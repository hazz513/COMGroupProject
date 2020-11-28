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
	
	public AllStudentStatPanel(Frame frame) {
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
		JButton viewStatus = new JButton("view status");
		viewStatus.addActionListener(this);
		
		add(viewStatus);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("view status")) {
			// get selected student
			Student student = (Student)studentSelection.getSelectedItem();
			// get all study periods
			ArrayList<StudyPeriod> periods = student.getPeriods();
			// display info for each period
			for (StudyPeriod period: periods) {
				
			}
		}
		
	}

}
