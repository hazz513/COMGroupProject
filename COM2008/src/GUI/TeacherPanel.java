package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TeacherPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -7560348159386808134L;
	
	Frame frame;
	
	public TeacherPanel(Frame frame) {
		// initialize panel
		this.frame = frame;
		initialize();
		// add side menu
		TeacherMenuPanel sideMenu = new TeacherMenuPanel(frame, this);
		add(sideMenu, BorderLayout.WEST);
	}
	
	public void initialize() {
		setLayout(new BorderLayout());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
