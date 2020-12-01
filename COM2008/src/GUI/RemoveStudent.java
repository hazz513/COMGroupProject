package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dataaccess.Student;

public class RemoveStudent extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5306184016676448751L;
	
	Frame frame; 
	
	//Labels to identify the fields
	private JLabel regLabel = new JLabel("Registration Number: ");
	
	//error message
	private JLabel errorNum = new JLabel();
	
	//Fields for data entry
	private JTextField registrationNum = new JTextField(10);
	
	//button
	private JButton deleteButton = new JButton("Delete Student");
	
	public RemoveStudent(Frame frame){
		this.frame = frame;
		setLayout(new GridLayout(0,1));
		setBorder(BorderFactory.createTitledBorder("Remove a Student"));
		init();
	}
	
	public void init() {
		//limiting the input length 
		registrationNum.setDocument(new JTextFieldLimit(11));
		
		add(regLabel);
		add(registrationNum);
		add(errorNum);
		
		add(deleteButton);
		deleteButton.addActionListener(this);
		
		//to let registrationNum only take integer inputs
		registrationNum.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = registrationNum.getText();
			    int l = value.length();
			    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
			    	registrationNum.setEditable(true);
			        errorNum.setText("");
			    }
			    else {
			        registrationNum.setEditable(false);
			        registrationNum.setText(null);
			        errorNum.setText("* Enter only numeric digits(0-9)");
			            	
			    }
			}
		});
		
	}
	
	public void clearFields() {
		registrationNum.setText(null);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Delete Student")) {
			int reg = Integer.parseInt(registrationNum.getText());
			//need to work on error when the regNo is wrong
			if (Student.retrieveFromDB(reg) != null) {
				Student student = new Student(reg,"xx","axv","xrt","fake@fakes.com");
				if(student.removeStudent()) {
					JOptionPane.showMessageDialog(null, "Sucessfully removed this student");
					clearFields();
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Error ocurred, try again");
					clearFields();
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "No Student with this registration");
				clearFields();
			}
			
		}
		
		
	}
	
	
	

}
