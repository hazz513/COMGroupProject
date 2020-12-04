package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dataaccess.*;

public class StudentPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7560348159386808134L;
	
	Frame frame;
	
	public StudentPanel(Frame frame, int registration) {
		// initialize panel
		this.frame = frame;
		initialize(registration);
		frame.revalidate();
		frame.repaint();

	}
	
	
	public void initialize(int registration) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));;
		setBorder(BorderFactory.createTitledBorder("Your current Years Progress"));
		
		Student user = Student.retrieveFromDB(registration);
		ArrayList<StudyPeriod> periods = user.getPeriods();
		if (periods.size() > 0) {
			Degree degree = periods.get(0).getPerformances().get(0).getApproval().getDegree();
			add(new JLabel("Degree: " + degree.getName()));
		}
		else {
			add(new JLabel("No study periods found"));
		}
		
		// format to 1 dp
		DecimalFormat df = new DecimalFormat("###.#");
		// boolean to make sure degree info is only printed the first time
		boolean firstPeriod = true;
		// display info for each period
		for (StudyPeriod period: periods) {
			ArrayList<Performance> performances = period.getPerformances();
			if (performances.size() > 0) {
				// if this is the first time then print degree info
				if (firstPeriod) {
					Degree degree = performances.get(0).getApproval().getDegree();
					add(new JLabel("Degree: " + degree.getName()));
					// mark degree info as printed
					firstPeriod = false;	
					// display personal tutor
					add(new JLabel("Personal Tutor: " + user.getPersonalTutor()));
				}
				
				// if student's degree class has been calculated display it
				if (user.getDegreeClass() != null) {
					// format to 1 dp
					add(new JLabel("Overall Grade: " + df.format(user.getOverallGrade())));
					add(new JLabel("Degree Class: " + user.getDegreeClass()));
				}
				
				add(new JLabel("---| Period: " + period.getLabel() + ", Level: " + performances.get(0).getLevel()));
				// if period has progression calculated, display it and mean grade
				if (period.getProgression() != null) {
					add(new JLabel("-------| Grade Average: " + df.format(period.getMeanGrade())));
					add(new JLabel("-------| Progression: " + period.getProgression()));
				}
				for (Performance performance: performances) {
					add(new JLabel("-------| Module: " + performance.getApproval().getModule().getName()));
					add(new JLabel("-----------| Grade: " + performance.getGrade()));
					if (performance.getResitGrade() != 0) {
						add(new JLabel("-----------| Resit Grade: " + performance.getResitGrade()));
					}
				}
			}
			else {
				add(new JLabel("ERROR: No modules found for the study period: " + period.getLabel() + "."));
			}
		}
		JButton logout = new JButton("logout");
		logout.addActionListener(this);
		add(logout);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// open relevant panel for each selection
		// first re-initialize frame too erase previous panel
		if (command.equals("logout")) {
			frame.loadLogin();
		}
		else {
			JOptionPane.showMessageDialog(null, "Invalid Input");
		}
		
	}
}