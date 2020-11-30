package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import dataaccess.Student;


public class ModifyStudentPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8844619246235661365L;

	Frame frame; 
		
	//Labels to identify the fields
	private JLabel regLabel = new JLabel("Registration Number: ");
	private JLabel titleLabel = new JLabel("Title: ");
	private JLabel surnameLabel = new JLabel("Surname: ");
	private JLabel forenameLabel = new JLabel("Forename: ");
	private JLabel emailLabel = new JLabel("emailId: ");
	
	//error message
	private JLabel errorNum = new JLabel();
	
	//Fields for data entry
	private JTextField registrationNum = new JTextField(10);
	private JTextField studentTitle = new JTextField(10);
	private JTextField studentSurname = new JTextField(10);
	private JTextField studentForename = new JTextField(10);
	private JTextField emailId = new JTextField(10);
	
	//button
	private JButton registerButton = new JButton("Register");
	private JButton clearAllButton = new JButton("ClearAll");
	
	public ModifyStudentPanel(Frame frame) {
		this.frame = frame;
		setLayout(new GridLayout(0,1));
		setBorder(BorderFactory.createTitledBorder("Register new Student"));
		init();		
		
	}
	
	public void init() {
		//limiting the input length 
		registrationNum.setDocument(new JTextFieldLimit(11) );
		studentTitle.setDocument(new JTextFieldLimit(45));
		studentSurname.setDocument(new JTextFieldLimit(45));
		studentForename.setDocument(new JTextFieldLimit(45));
		emailId.setDocument(new JTextFieldLimit(45));
		
		
		add(regLabel);
		add(registrationNum);
		add(errorNum);
		add(titleLabel);
		add(studentTitle);
		add(surnameLabel);
		add(studentSurname);
		add(forenameLabel);
		add(studentForename);
		add(emailLabel);
		add(emailId);
		
		
		add(clearAllButton);
		add(registerButton);
		clearAllButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		//to let registrationNum only take integer inputs
		registrationNum.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent ke) {
	            String value = registrationNum.getText();
	            int l = value.length();
	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
	            	registrationNum.setEditable(true);
	            	errorNum.setText("");
	            } else {
	            	registrationNum.setEditable(false);
	            	registrationNum.setText(null);
	            	errorNum.setText("* Enter only numeric digits(0-9)");
	            	
	            }
	         }
		});
		
		/*studentTitle.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent ke) {
	            String value = studentTitle.getText();
	            int l = value.length();
	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
	            	studentTitle.setEditable(false);
	            	studentTitle.setText(null);
	            	errorNum.setText("No number in titles");
	            } else {
	            	studentTitle.setEditable(true);
	            	errorNum.setText("");
	            	
	            }
	         }
		});*/
		
		
	}
	
	public void clearFields() {
		registrationNum.setText(null);
		studentTitle.setText(null);
		studentSurname.setText(null);
		studentForename.setText(null);
		emailId.setText(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Register")) {
			
			if (!(registrationNum.getText()==null||registrationNum.getText().trim().isEmpty()||
					studentTitle.getText()==null||studentTitle.getText().trim().isEmpty()||
					studentSurname.getText()==null||studentSurname.getText().trim().isEmpty()||	
					studentForename.getText()==null||studentForename.getText().trim().isEmpty()||
					emailId.getText()==null||emailId.getText().trim().isEmpty()))
			{
				int reg = Integer.parseInt(registrationNum.getText());
				String title = studentTitle.getText();
				String sur = studentSurname.getText();
				String fore = studentForename.getText();
				String email = emailId.getText();
				Student student = new Student (reg,title,sur,fore,email);
				student.addStudent();
				JOptionPane.showMessageDialog(null, "New Student Registered");
				clearFields();
			}
			else {
				JOptionPane.showMessageDialog(null, "Error in New Student Registration");
				clearFields();
			}
				
			
		}
		else if (command.equals("ClearAll")) {
			clearFields();
		}
		
	
	}

		
}
