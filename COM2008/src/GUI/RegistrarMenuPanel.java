package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarMenuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5251234810140590507L;
	
	Frame frame;
	RegistrarPanel registrarPanel;
	
	public RegistrarMenuPanel (Frame frame,RegistrarPanel registrarPanel) {
		this.frame = frame;
		this.registrarPanel = registrarPanel;
		
		initialize();
	}
	
	public void initialize() {
		setLayout(new GridLayout(0, 1));
		//Initialise buttons
		JButton logout = new JButton("logout");
		logout.addActionListener(this);
		
		JButton modifyStudent = new JButton("Add New Student");
		modifyStudent.addActionListener(this);
		
		JButton deleteStudent = new JButton("Remove Student");
		deleteStudent.addActionListener(this);
		
		
		//add buttons to panel
		add(logout);
		add(modifyStudent);
		add(deleteStudent);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// open relevant panel for each selection
		// first re-initialize frame too erase previous panel
		if (command.equals("logout")) {
			frame.loadLogin();
		}
		else if(command.equals("Add New Student")) {
			resetRegistrarPanel();
			//add task panel
			registrarPanel.add(new ModifyStudentPanel(frame),BorderLayout.CENTER);
			//re display
			frame.revalidate();
			frame.repaint();
			
		}
		else if (command.equals("Delete Student")) {
			resetRegistrarPanel();
			
		}
	}

	public void resetRegistrarPanel() {
		// clearPanel
		registrarPanel.removeAll();
		// re-initialise registrar panel
		registrarPanel.initialize(); 
		// re-add side menu
		registrarPanel.add(this, BorderLayout.WEST);
	}
}
