package GUI;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public Frame (String title) throws HeadlessException {
		super(title);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// make it a half size window centered by default
		setSize(screenSize.width/2, screenSize.height/2);
		setLocation(screenSize.width/4, screenSize.height/4);
		// open the login panel by default
		Container contentPane = getContentPane();
		LoginPanel loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel, BorderLayout.CENTER);
		// exit program on closing window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
		
	}
	
	/*
	 * changes the interface to a teacher
	 */
	public void loadTeacher() {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		
		TeacherPanel teacherPanel = new TeacherPanel(this);
		contentPane.add(teacherPanel);
		
		revalidate();
		repaint();
	}
	
	public void loadLogin() {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		
		LoginPanel loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel);
		
		revalidate();
		repaint();
	}
	
	public void loadStudent(int registration) {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		
		StudentPanel studentPanel = new StudentPanel(this,registration);
		contentPane.add(studentPanel);
		revalidate();
		repaint();
	}
	
	public static void main(String[] args) {
		new Frame("the system");
	}
}
