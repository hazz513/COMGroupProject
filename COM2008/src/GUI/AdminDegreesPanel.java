package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Admin;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDegreesPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Degree> removeOption = new JComboBox<Degree>();
	
	private JTextField code = new JTextField(6);
	private JTextField name = new JTextField(35);
	private JTextField leadDep = new JTextField(3);
	
	private JLabel degCode = new JLabel("Degree Code: ");
	private JLabel degName = new JLabel("Degree Name: ");
	private JLabel degLeadDep = new JLabel("Degree lead department");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	
	/*
	 * Constructor to create the needed Panel
	 */
	public AdminDegreesPanel(Frame frame) {
		this.frame = frame;
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add a Degree");
		optionSelection.addItem("Remove a Degree");
	}
	
	/*
	 * Construct UI for adding an account
	 */
	public void addAccount() {
		removeAll();
		setLayout(new FlowLayout());
		add(degCode);
		add(code);
		add(degName);
		add(name);
		add(degLeadDep);
		add(leadDep);
		
		
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
		//Gets list of all accounts and adds to dropdown
		ArrayList<Degree> users = Degree.getAllFromDB();
		for (Degree current: users) {
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
		setBorder(BorderFactory.createTitledBorder("Add or Remove a Degree"));
		
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
			if (optionSelection.getSelectedItem() == "Add a Degree") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove a Degreet") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected user from the database
		else if (command.equals("Remove")) {
			Degree toRemove = (Degree)removeOption.getSelectedItem();
			Admin.removeDegree(toRemove);
			JOptionPane.showMessageDialog(null, "The Degree has been removed");
			removeOption.removeAllItems();
			initializePanel();
		}
		//Adds a user into the database
		else if (command.equals("Add")) {
			String dCode = code.getText();
			String dName = name.getText();
			String dLeadDep = leadDep.getText();
			if (((dCode.length()==6)) && ((checkSize(50,dName.length()) &&
					((dLeadDep.length()==3) && checkOnlyLetters(dLeadDep))))){
				Degree Deg = new Degree(dCode,dName,dLeadDep);
				if (Admin.addDegree(Deg)){
					JOptionPane.showMessageDialog(null, "The new Degree has been added");
					initializePanel();
				}
				else {
					JOptionPane.showMessageDialog(null, "An error has occurred");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid character length or character Type");
			}
		}
	}
	
	/*
	 * Checks inputs and responds if they are valid
	 * @return = boolean
	 */
	public boolean checkSize(int max, int length) {
		if ((length > max ) || (length == 0)) {
			System.out.println("Failed size ");
			return false;
		}
		else {
			System.out.println("True size ");
			return true;
		}
	}
	/*
	 * Checks if the string is an integer of varying length
	 * @return = boolean
	 */
	public boolean checkOnlyLetters(String toCheck) {
	    int length = toCheck.length();
	    for (int i = 0; i<length; i++) {
	        if ((Character.isLetter(toCheck.charAt(i)) == false)) {
	        	System.out.println("Failed" + toCheck);
	        	return false;
	        }
	    }
	    System.out.println("True" + toCheck);
	    return true;
	}
	
}