package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckProgressionPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	Frame frame;
	
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	
	public CheckProgressionPanel(Frame frame) {
		// set vertical layout
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create dropdown menu to select student
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student student: students) {
			studentSelection.addItem(student);
		}
		
		// add dropdown to panel
		add(studentSelection);
		
		// add button to initiate process
		JButton getProgression = new JButton("check progression");
		getProgression.addActionListener(this);
		
		add(getProgression);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		int progress = JOptionPane.NO_OPTION;
		
		// on button click
		if (command.equals("check progression")) {
			// assign student from dropdown
			Student student = (Student)studentSelection.getSelectedItem();
			// default popup message
			String message = "No study period found.";
			
			// get highest period
			ArrayList<StudyPeriod> periods = student.getPeriods();
			
			// if there are study periods to check then check the latest one
			if (periods.size() > 0) {
				periods.sort(new Teacher.StudyPeriodComparator());
				StudyPeriod period = periods.get(periods.size()-1);
				// get the progression for it
				Teacher.Progression progression = Teacher.progression(period);
				message = ("the progression status is: " + progression + ", would you like to apply this?");
				// output to use and ask for further action
				progress = JOptionPane.showConfirmDialog(null, message, "Progress Student", JOptionPane.YES_NO_OPTION);
			}
			else {
				JOptionPane.showMessageDialog(null, message);
			}
			
			// if user chooses to proceed with progression
			if (progress == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "not implemented yet");
			}
			
		}
	}

}
