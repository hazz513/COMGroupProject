package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import dataaccess.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckProgressionPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	Frame frame;
	
	public CheckProgressionPanel(Frame frame) {
		// set vertical layout
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create dropdown menu to select student
		ArrayList<Student> students = Student.getAllFromDB();
		JComboBox<Student> studentSelection = new JComboBox<Student>();
		for (Student student: students) {
			studentSelection.addItem(student);
		}
		// add dropdown
		add(studentSelection);
		add(new JButton("wont do anything yet"));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
