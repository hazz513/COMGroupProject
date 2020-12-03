package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMenuPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 8257326810140520507L;
	
	Frame frame;
	TeacherPanel teacherPanel;
	
	public TeacherMenuPanel(Frame frame, TeacherPanel teacherPanel) {
		this.frame = frame;
		this.teacherPanel = teacherPanel;
		
		initialize();
	}
	
	/*
	 * sets layout, sets up buttons
	 */
	public void initialize() {
		setLayout(new GridLayout(0, 1));
		// initialise buttons
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
				
		JButton modifyGrades = new JButton("Add/update grades");
		modifyGrades.addActionListener(this);
				
		JButton checkProgression = new JButton("Calculate progression");
		checkProgression.addActionListener(this);
				
		JButton calculateDegree = new JButton("Calculate degree class");
		calculateDegree.addActionListener(this);
				
		JButton studentStat = new JButton("View student status");
		studentStat.addActionListener(this);
		
		// add buttons to panel
		add(logout);
		add(modifyGrades);
		add(checkProgression);
		add(calculateDegree);
		add(studentStat);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// open relevant panel for each selection
		// first re-initialize frame too erase previous panel
		if (command.equals("Logout")) {
			frame.loadLogin();
		}
		else if (command.equals("Add/update grades")) {
			resetTeacherPanel();
			// add task panel
			TeacherGradePanel panel = new TeacherGradePanel(frame);
			// create scrollable pane with panel
			JScrollPane scrollPane = new JScrollPane(panel);
			// make pane vertically scrollable
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			// add pane to frame
			teacherPanel.add(scrollPane, BorderLayout.CENTER);
			//teacherPanel.add(panel, BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("Calculate progression")) {
			resetTeacherPanel();
			// add task panel
			teacherPanel.add(new TeacherProgressionPanel(frame), BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("Calculate degree class")) {
			resetTeacherPanel();
			// add task panel
			teacherPanel.add(new TeacherDegreePanel(frame), BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("View student status")) {
			resetTeacherPanel();
			// add task panel
			TeacherStatPanel panel = new TeacherStatPanel(frame);
			// create scrollable pane with panel
			JScrollPane scrollPane = new JScrollPane(panel);
			// make pane vertically scrollable
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			// add pane to frame
			teacherPanel.add(scrollPane, BorderLayout.CENTER);
			//teacherPanel.add(panel, BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
	}
	
	/*
	 * removes everything from teacher panel and 
	 * re-adds the left menu panel, to make room for a new panel
	 * 
	 */
	public void resetTeacherPanel() {
		// clearPanel
		teacherPanel.removeAll();
		// re-initialize teacher panel
		teacherPanel.initialize(); 
		// re-add side menu
		teacherPanel.add(this, BorderLayout.WEST);
	}

}
