package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TeacherPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7560348159386808134L;
	
	Frame frame;
	
	public TeacherPanel(Frame frame) {
		this.frame = frame;
		
		setLayout(new GridLayout(0, 1));
		
		add(new JButton("this is the teacher panel"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
