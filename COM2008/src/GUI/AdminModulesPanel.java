package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Admin;
import dataaccess.*;
import dataaccess.Module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminModulesPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Module> removeOption = new JComboBox<Module>();
	JComboBox<Department> allDepartments = new JComboBox<Department>();
	

	private JTextField modName = new JTextField(25);

	private JLabel modNLabel = new JLabel("Module Name: ");
	private JLabel associatedDep = new JLabel("Associated Department: ");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	private JButton getInput = new JButton("Confirm Option");
	
	/*
	 * Constructor to create the needed Panel
	 */
	public AdminModulesPanel(Frame frame) {
		this.frame = frame;
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		remove.addActionListener(this);
		getInput.addActionListener(this);
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add a Module");
		optionSelection.addItem("Remove a Module");
	}
	
	/*
	 * Construct UI for adding an account
	 */
	public void addAccount() {
		removeAll();
		setLayout(new FlowLayout());
		
		ArrayList<Department> departments = Department.getAllFromDB();
		for (Department current : departments) {
			allDepartments.addItem(current);
		}
		
		add(modNLabel);
		add(modName);
		add(associatedDep);
		add(allDepartments);
		add(confirm);
		add(cancel);
		
		frame.revalidate();
		frame.repaint();
	}
	/*
	 * Constructs UI to remove Module
	 */
	public void removeAccount() {
		removeAll();
		setLayout(new FlowLayout());
		//Gets list of all modules and adds to dropdown
		ArrayList<Module> modules = Module.getAllFromDB();
		for (Module current: modules) {
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
		setBorder(BorderFactory.createTitledBorder("Add or Remove a Module"));
		
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
			if (optionSelection.getSelectedItem() == "Add a Module") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove a Module") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected module from the database
		else if (command.equals("Remove")) {
			Module toRemove = (Module)removeOption.getSelectedItem();
			Admin.removeModule(toRemove);
			JOptionPane.showMessageDialog(null, "The Module has been Removed");
			removeOption.removeAllItems();
			removeAccount();
		}
		//Adds a module into the database
		else if (command.equals("Add")) {
			Department department = (Department)allDepartments.getSelectedItem();
			String dLeadDep = (department.getDepCode()).toUpperCase();
			String code = (Module.generateModuleCode(dLeadDep)).toUpperCase();
			String name = modName.getText();
			String halfCode1 = code.substring(0,3);
			String halfCode2 = code.substring(3);
			
			if (((checkSize(7, code.length())) && checkOnlyLetters(halfCode1) && checkInt(halfCode2)) && (checkSize(25,name.length()))){
				Module Module = new Module(code,name);
				if (Admin.addSingleModule(Module)){
					JOptionPane.showMessageDialog(null, "The new module has been added");
				}
				else {
					JOptionPane.showMessageDialog(null, "An error has occurred");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid character length or Character Types");
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
	
	/*
	 * Checks if the string is an integer of varying length
	 * @return = boolean
	 */
	public boolean checkInt(String toCheck) {
		boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(toCheck);
	 
	         // s is a valid integer
	         System.out.println("It's an int");
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	      System.out.println("It's not an int");
	      return isValidInteger;
   }
}