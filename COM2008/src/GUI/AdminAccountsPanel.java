package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminAccountsPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	
	private JTextField usernameID = new JTextField(6);
	private JTextField password = new JPasswordField(10);
	private JTextField authLevel = new JPasswordField(1);
	private JTextField registration = new JPasswordField(10);
	
	private JLabel userLabel = new JLabel("User login: ");
	private JLabel passLabel = new JLabel("Password: ");
	private JLabel authLabel = new JLabel("Authorization (1-4): ");
	private JLabel regLabel = new JLabel("Registration (Only needed for students): ");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	public AdminAccountsPanel(Frame frame) {
		this.frame = frame;
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add an Account");
		optionSelection.addItem("Remove an Account");
	}
	
	public void addAccount() {
		System.out.println("Anything");
		removeAll();
		add(userLabel);
		add(usernameID);
		add(passLabel);
		add(password);
		add(authLabel);
		add(authLevel);
		add(regLabel);
		add(registration);
		
		
		confirm.addActionListener(this);
		add(confirm);
		
		
		cancel.addActionListener(this);
		add(cancel);
		
		frame.revalidate();
		frame.repaint();
	}
	
	
	public void initializePanel() {
		removeAll();
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Add or Remove an Account"));
		
		// set vertical layout
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		// add dropdown to panel
		add(optionSelection);
		
		// add button to initiate process
		JButton getInput = new JButton("Confirm Option");
		getInput.addActionListener(this);
		
		add(getInput);
		frame.revalidate();
		frame.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		System.out.println(command);
		// on button click
		if (command.equals("Confirm Option")) {
			// assign student from dropdown
			if (optionSelection.getSelectedItem() == "Add an Account") {
				addAccount();
			}
		}
		else if (command.equals("Remove an Account")) {
		}
		else if (command.equals("Cancel")) {
			initializePanel();
		}
		else if (command.equals("Add")) {
			initializePanel();
		}
	}
	
}