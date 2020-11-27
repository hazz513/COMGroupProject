package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7586199126872813371L;
	
	Frame frame;
	
	public LoginPanel(Frame frame) {
		this.frame = frame;
		setLayout(new GridLayout(0, 1));
		
		JButton teacherButton = new JButton("load teacher");
		teacherButton.addActionListener(this);
		
		add(teacherButton);
		add(new JButton("do nothing"));
	}
	
	/*
	 * handles the actions for this frame
	 * 
	 * @param event - the event objectt
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("load teacher")) {
			frame.loadTeacher();
		}
	}
}
