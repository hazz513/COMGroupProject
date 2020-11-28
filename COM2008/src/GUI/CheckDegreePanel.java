package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckDegreePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 2198856093172424855L;
	
	Frame frame;
	
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	
	public CheckDegreePanel(Frame frame) {
		this.frame = frame;
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Calculate Student's Degree Class"));
		
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
		JButton getDegreeClass = new JButton("calculate degree class");
		getDegreeClass.addActionListener(this);
		
		add(getDegreeClass);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command == "calculate degree class") {
			// assign student from dropdown
			Student student = (Student)studentSelection.getSelectedItem();
			// default error message
			String message = "No study period found.";
						
			// get highest period
			ArrayList<StudyPeriod> periods = student.getPeriods();
						
			// if there are study periods to check then check the latest one
			if (periods.size() > 0) {
				periods.sort(new Teacher.StudyPeriodComparator());
				StudyPeriod period = periods.get(periods.size()-1);
				// get the progression for it
				Teacher.Progression progression = Teacher.progression(period);
				// get degree class for it
				Teacher.DegreeClass degreeClass = Teacher.getDegreeClass(student, progression);
				// output to user
				if (degreeClass == Teacher.DegreeClass.INVALID) {
					message = ("the student is not ready to end course");
				}
				else {
					message = ("the degree class is: " + degreeClass);
				}
				
			}
			// default to error message
			else {
				// do nothing
			}
			JOptionPane.showMessageDialog(null, message);
		}
	}

}
