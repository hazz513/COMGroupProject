package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Admin;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminPartnerPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Department> departments = new JComboBox<Department>();
	JComboBox<Degree> degrees = new JComboBox<Degree>();
	JComboBox<Partner> removeOption = new JComboBox<Partner>();
	
	private JLabel depLabel = new JLabel("Department to Link: ");
	private JLabel degLabel = new JLabel("Degree to Link ");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	
	/*
	 * Constructor to create the needed Panel
	 */
	public AdminPartnerPanel(Frame frame) {
		this.frame = frame;
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add a Link");
		optionSelection.addItem("Remove a Link");
	}
	
	/*
	 * Construct UI for adding the Links
	 */
	public void addAccount() {
		removeAll();
		setLayout(new FlowLayout());
		
		ArrayList<Department> dep = Department.getAllFromDB();
		for (Department current: dep) {
			departments.addItem(current);
		}
		
		ArrayList<Degree> deg = Degree.getAllFromDB();
		for (Degree current: deg) {
			degrees.addItem(current);
		}
		
		add(depLabel);
		add(departments);
		add(degLabel);
		add(degrees);
		
		
		confirm.addActionListener(this);
		add(confirm);
		
		
		cancel.addActionListener(this);
		add(cancel);
		
		frame.revalidate();
		frame.repaint();
	}
	/*
	 * Constructs UI to remove account
	 */
	public void removeAccount() {
		removeAll();
		setLayout(new FlowLayout());
		//Gets list of all partners for a drop down menu
		ArrayList<Partner> links = Partner.getAllFromDB();
		for (Partner current: links) {
			removeOption.addItem(current);
		}
		// limit height
		removeOption.setMaximumSize(new Dimension(100000, (int)remove.getMaximumSize().getHeight()));
		
		// alignment
		removeOption.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(removeOption);
		
		remove.addActionListener(this);
		add(remove);
		cancel.addActionListener(this);
		add(cancel);
		
		frame.revalidate();
		frame.repaint();
		
	}
	
	/*
	 * Initialise UI. Makes constructor cleaner
	 */
	public void initializePanel() {
		removeAll();
		setLayout(new FlowLayout());
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Add or Remove a link between Degree & Department"));
		
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
	
	/*
	 * Reads inputs from buttons and drop down menu
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// on button click
		if (command.equals("Confirm Option")) {
			// assign student from dropdown
			if (optionSelection.getSelectedItem() == "Add a Link") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove a Link") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected user from the database
		else if (command.equals("Remove")) {
			Partner toRemove = (Partner)removeOption.getSelectedItem();
			Admin.removePartner(toRemove);
			JOptionPane.showMessageDialog(null, "The two are no longer Linked");
			removeOption.removeAllItems();
			initializePanel();
		}
		//Adds a user into the database
		else if (command.equals("Add")) {
			Department depToLink = (Department)departments.getSelectedItem();
			Degree degToLink = (Degree)degrees.getSelectedItem();
			Partner toAdd = new Partner(degToLink,depToLink);
			Admin.addPartner(toAdd);
			JOptionPane.showMessageDialog(null, "The two are now Linked");
			removeOption.removeAllItems();
			initializePanel();
		}
	}
	
}