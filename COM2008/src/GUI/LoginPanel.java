package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import businesslogic.Teacher;
import dataaccess.*;

public class LoginPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7586199126872813371L;
	
	Frame frame;
	
	private JTextField usernameField = new JTextField(6);
	private JTextField passwordField = new JPasswordField(10);
	
	private JLabel userLabel = new JLabel("User login: ");
	private JLabel passLabel = new JLabel("Password: ");
	
	private JButton loginButton = new JButton("login");
	private JButton teacher = new JButton("launch Teacher");
	
	public LoginPanel(Frame frame) {
		this.frame = frame;
		setLayout(new GridLayout(0, 1));
		

		init();
	}
	
	/*
	 * handles the actions for this frame
	 * 
	 * @param event - the event object
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("login")) {
			String username = usernameField.getText();
			 
			String password = passwordField.getText();
			
			Authentication login = new Authentication(username,password);
			int authLevel = login.checkPassword(username,password);
			System.out.println(authLevel);
			
			if (authLevel == 0) {
				JOptionPane.showMessageDialog(null, "Incorrect Login Details");
			}
			else if (authLevel == 1) {
				JOptionPane.showMessageDialog(null, "Correct Student Login");
			}
			else if (authLevel == 2) {
				JOptionPane.showMessageDialog(null, "Correct Teacher Login");
				frame.loadTeacher();
			}
			else if (authLevel == 3) {
				JOptionPane.showMessageDialog(null, "Correct Registrar Login");
			}
			else if (authLevel == 4) {
				JOptionPane.showMessageDialog(null, "Correct Administrator Login");
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid Authroization Level");
			}
		}
		else if (command.equals("Launch Teacher")) {
			frame.loadTeacher();
		}
	}
	
	public void init() {
		add(userLabel);
		add(usernameField);
		add(passLabel);
		add(passwordField);
	
		
		add(loginButton);
		loginButton.addActionListener(this);
		add(teacher);
		teacher.addActionListener(this);
	}
}
