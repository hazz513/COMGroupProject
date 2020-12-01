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
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
				
		JButton modules = new JButton("Add/Remove Modules");
		modules.addActionListener(this);
				
		JButton approvals = new JButton("Add/Remove Approvals");
		approvals.addActionListener(this);
				
		JButton degrees = new JButton("Add/Remove Degree Courses");
		degrees.addActionListener(this);
				
		JButton departments = new JButton("Add/Remove Departments");
		departments.addActionListener(this);
		
		JButton links = new JButton("Add/Remove Degree & Deptartment Links");
		links.addActionListener(this);
		
		JButton accounts = new JButton("Add/Remove Accounts");
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
		if (command.equals("Logout")) {
			frame.loadLogin();
		}
		else if (command.equals("Add/Remove Approvals")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminApprovalsPanel(frame));
			// re display
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("Add/Remove Modules")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminModulesPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("Add/Remove Degree & Deptartment Links")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminPartnerPanel(frame));
			frame.revalidate();
			frame.repaint();
		}

		else if (command.equals("Add/Remove Departments")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminDepartmentsPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
		
		else if (command.equals("Add/Remove Accounts")) {
			resetAdminPanel();
			// add task panel
			adminPanel.add(new AdminAccountsPanel(frame));
			frame.revalidate();
			frame.repaint();
		}
		else if (command.equals("Add/Remove Degree Courses")) {
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
