package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegistrarPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 4725777563857119450L;
	
	Frame frame;
	
	public RegistrarPanel (Frame frame) {
		this.frame = frame;
		initialize();
		
	}

	private void initialize() {
		setLayout(new BorderLayout());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
