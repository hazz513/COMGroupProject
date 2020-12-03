package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Admin;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDepartmentsPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Department> removeOption = new JComboBox<Department>();
	
	private JTextField depCode = new JTextField(3);
	private JTextField depName = new JTextField(30);
	
	private JLabel depCLabel = new JLabel("Department Code: ");
	private JLabel depCName = new JLabel("Department Name: ");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	private JButton getInput = new JButton("Confirm Option");
	
	/*
	 * Constructor to create the needed Panel
	 */
	public AdminDepartmentsPanel(Frame frame) {
		this.frame = frame;
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		remove.addActionListener(this);
		getInput.addActionListener(this);
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add a Department");
		optionSelection.addItem("Remove a Department");
	}
	
	/*
	 * Construct UI for adding an account
	 */
	public void addAccount() {
		removeAll();
		setLayout(new FlowLayout());
		add(depCLabel);
		add(depCode);
		add(depCName);
		add(depName);
		add(confirm);
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
		ArrayList<Department> departments = Department.getAllFromDB();
		for (Department current: departments) {
			removeOption.addItem(current);
		}
		// limit height
		removeOption.setMaximumSize(new Dimension(100000, (int)remove.getMaximumSize().getHeight()));
		
		// alignment
		removeOption.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(removeOption);
		add(remove);
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
		setBorder(BorderFactory.createTitledBorder("Add or Remove a Department"));
		
		// set vertical layout
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		// add dropdown to panel
		add(optionSelection);
		
		// add button to initiate process
		
		
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
			if (optionSelection.getSelectedItem() == "Add a Department") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove a Department") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected department from the database
		else if (command.equals("Remove")) {
			Department toRemove = (Department)removeOption.getSelectedItem();
			Admin.removeDepartment(toRemove);
			JOptionPane.showMessageDialog(null, "The Department has been Removed");
			removeOption.removeAllItems();
			removeAccount();
		}
		//Adds a department into the database
		else if (command.equals("Add")) {
			String code = depCode.getText();
			String name = depName.getText();
			if (((code.length()==3) && checkOnlyLetters(code)) && (checkSize(50,name.length()))){
				Department Dep = new Department(code,name);
				if (Admin.addDepartment(Dep)){
					JOptionPane.showMessageDialog(null, "The new Department has been added");
				}
				else {
					JOptionPane.showMessageDialog(null, "An error has occurred");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid character length or Character Type");
			}
		}
	}
	
	/*
	 * Checks inputs and responds if they are valid
	 * @return = boolean
	 */
	public boolean checkSize(int max, int length) {
		if ((length > max ) || (length == 0)) {
			return false;
		}
		else {
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
	        	return false;
	        }
	    }
	    return true;
	}
	
}