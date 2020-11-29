package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dataaccess.*;

public class StudentPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7560348159386808134L;
	
	Frame frame;
	
	public StudentPanel(Frame frame, int registration) {
		// initialize panel
		this.frame = frame;
		initialize();
		Student user = Student.retrieveFromDB(registration);
	}
	
	public void initialize() {
		setLayout(new GridLayout(0, 1));
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