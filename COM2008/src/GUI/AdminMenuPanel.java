package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenuPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 8257326810140520507L;
	
	Frame frame;
	AdminPanel adminPanel;
	
	public AdminMenuPanel(Frame frame, AdminPanel adminPanel) {
		this.frame = frame;
		this.adminPanel = adminPanel;
		
		initialize();
	}
	
	public void initialize() {
		setLayout(new GridLayout(0, 1));
		// initialise buttons
		JButton logout = new JButton("logout");
		logout.addActionListener(this);
				
		JButton modules = new JButton("add/remove modules");
		modules.addActionListener(this);
				
		JButton approvals = new JButton("add/remove approvals");
		approvals.addActionListener(this);
				
		JButton degrees = new JButton("add/remove degree courses");
		degrees.addActionListener(this);
				
		JButton departments = new JButton("add/remove departments");
		departments.addActionListener(this);
		
		JButton links = new JButton("add/remove Degree & Deptartment Links");
		links.addActionListener(this);
		
		JButton accounts = new JButton("add/remove accounts");
		accounts.addActionListener(this);
		
		
		
		// add buttons to panel
		add(logout);
		add(modules);
		add(approvals);
		add(degrees);
		add(departments);
		add(links);
		add(accounts);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// open relevant panel for each selection
		// first re-initialize frame too erase previous panel
		if (command.equals("logout")) {
			frame.loadLogin();
		}
		/*
		else if (command.equals("add/remove modules")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminModulesPanel(frame), BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("add/remove approvals")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminApprovalsPanel(frame), BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("add/remove degree courses")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminDegreesPanel(frame));
			// create scrollable pane with panel
			JScrollPane scrollPane = new JScrollPane(panel);
			// make pane vertically scrollable
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			// add pane to frame
			adminPanel.add(scrollPane, BorderLayout.CENTER);
			//teacherPanel.add(panel, BorderLayout.CENTER);
			// re display
			frame.revalidate();
			frame.repaint();
		}
				*/
		else if (command.equals("add/remove Degree & Deptartment Links")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminPartnerPanel(frame));
			frame.revalidate();
			frame.repaint();
		}

		else if (command.equals("add/remove departments")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminDepartmentsPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
		
		else if (command.equals("add/remove accounts")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminAccountsPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("add/remove degree courses")) {
			resetAdminPanel();
			//add task panel
			adminPanel.add(new AdminDegreesPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
	}
	
	public void resetAdminPanel() {
		// clearPanel
		adminPanel.removeAll();
		// re-initialize teacher panel
		adminPanel.initialize(); 
		// re-add side menu
		adminPanel.add(this, BorderLayout.WEST);
	}

}
