package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AllStudentStatPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 4725247563857811450L;
	
	Frame frame;
	
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	JButton viewStatus = new JButton("view status");
	
	public AllStudentStatPanel(Frame frame) {
		this.frame = frame;
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Check Student Status"));

		// set vertical layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create dropdown menu to select student
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student student: students) {
			studentSelection.addItem(student);
		}
		
		studentSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// create button to initiate process
		viewStatus.addActionListener(this);
		// create scroll bar
		
		
		// add dropdown to panel
		add(studentSelection);
		// add button to panel
		add(viewStatus);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("view status")) {
			removeAll();
			// add dropdown to panel
			add(studentSelection);
			// add button to panel
			add(viewStatus);
			
			// get selected student
			Student student = (Student)studentSelection.getSelectedItem();
			// get all study periods
			ArrayList<StudyPeriod> periods = student.getPeriods();
			
			add(new JLabel("Degree: " + periods.get(0).getPerformances().get(0).getApproval().getDegree().getName()));
			// display info for each period
			for (StudyPeriod period: periods) {
				ArrayList<Performance> performances = period.getPerformances();
				add(new JLabel("---| Period: " + period.getLabel() + ", Level: " + performances.get(0).getLevel()));
				add(new JLabel("-------| Grade Average: " + period.getMeanGrade()));
				for (Performance performance: performances) {
					add(new JLabel("-------| Module: " + performance.getApproval().getModule().getName()));
					add(new JLabel("-----------| Grade: " + performance.getGrade()));
					if (performance.getResitGrade() != 0) {
						add(new JLabel("-----------| Resit Grade: " + performance.getResitGrade()));
					}
				}
			}
			
			frame.revalidate();
			frame.repaint();
		}
		
	}

}
