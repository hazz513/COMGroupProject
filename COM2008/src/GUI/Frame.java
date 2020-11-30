package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	public Frame (String title) throws HeadlessException {
		super(title);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// make it a half size window centered by default
		setSize(screenSize.width/3, screenSize.height/2);
		setLocation(screenSize.width/4, screenSize.height/4);
		// open the login panel by default
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
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
		contentPane.setLayout(new GridLayout(0,1));
		TeacherPanel teacherPanel = new TeacherPanel(this);
		contentPane.add(teacherPanel);
		
		revalidate();
		repaint();
	}
	/*
	 * changes the interface to the Login
	 */
	public void loadLogin() {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		
		contentPane.setLayout(new FlowLayout());
		LoginPanel loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel);
		
		revalidate();
		repaint();
	}
	/*
	 * changes the interface to the student view
	 */
	public void loadStudent(int registration) {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		
		StudentPanel studentPanel = new StudentPanel(this,registration);
		contentPane.add(studentPanel);
		revalidate();
		repaint();
	}
	/*
	 * changes the interface to a registrar
	 */
	public void loadRegistrar() {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new GridLayout(0,1));
		RegistrarPanel registrarPanel = new RegistrarPanel(this); 
		contentPane.add(registrarPanel);
		
		revalidate();
		repaint();
	}
	
	public void loadAdmin() {
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new GridLayout(0,1));
		AdminPanel adminPanel = new AdminPanel(this);
		contentPane.add(adminPanel);
		
		revalidate();
		repaint();
	}
	
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		new Frame("the system");
	}
}
