package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
	private JButton teacher = new JButton("Launch Teacher");
	
	public LoginPanel(Frame frame) {
		this.frame = frame;
		setLayout(new GridLayout(0, 2));
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
			List<Integer> storedInfo = new ArrayList<Integer>();
			storedInfo = login.checkPassword(username,password);
			System.out.println(storedInfo.get(0));
			System.out.println(storedInfo.get(1));
			
			if (storedInfo.get(0) == 0) {
				JOptionPane.showMessageDialog(null, "Incorrect Login Details");
			}
			else if (storedInfo.get(0) == 1) {
				JOptionPane.showMessageDialog(null, "Correct Student Login");
				frame.loadStudent(storedInfo.get(1));
			}
			else if (storedInfo.get(0) == 2) {
				JOptionPane.showMessageDialog(null, "Correct Teacher Login");
				frame.loadTeacher();
			}
			else if (storedInfo.get(0) == 3) {
				JOptionPane.showMessageDialog(null, "Correct Registrar Login");
			}
			else if (storedInfo.get(0) == 4) {
				JOptionPane.showMessageDialog(null, "Correct Administrator Login");
				frame.loadAdmin();
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
