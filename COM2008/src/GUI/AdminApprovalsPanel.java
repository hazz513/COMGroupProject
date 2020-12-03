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

public class AdminApprovalsPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Approval> removeOption = new JComboBox<Approval>();
	JComboBox<Degree> degrees = new JComboBox<Degree>();
	JComboBox<Module> modules = new JComboBox<Module>();
	
	
	private JTextField core = new JTextField(1);
	private JTextField credits = new JTextField(11);
	private JTextField level = new JTextField(1);
	
	private JLabel coreLabel = new JLabel("Is the module Core (0 or 1): ");
	private JLabel creditLabel = new JLabel("Credit Value: ");
	private JLabel levelLabel = new JLabel("What level of study is the module at: ");
	private JLabel degreeLabel = new JLabel("Select a Degree to Link: ");
	private JLabel moduleLabel = new JLabel("Select a Module to Link: ");
	private JButton getInput = new JButton("Confirm Option");
	
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	
	/*
	 * Constructor
	 */
	public AdminApprovalsPanel(Frame frame) {
		this.frame = frame;
		getInput.addActionListener(this);
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		remove.addActionListener(this);
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add an Approval");
		optionSelection.addItem("Remove an Approval");
	}
	
	/*
	 * Construct UI for adding an account
	 */
	public void addAccount() {
		removeAll();
		setLayout(new FlowLayout());
		
		ArrayList<Degree> deg = Degree.getAllFromDB();
		for (Degree current: deg) {
			degrees.addItem(current);
		}
		ArrayList<Module> mod = Module.getAllFromDB();
		for (Module current: mod) {
			modules.addItem(current);
		}
		
		
		add(degreeLabel);
		add(degrees);
		add(moduleLabel);
		add(modules);
		add(levelLabel);
		add(level);
		add(coreLabel);
		add(core);
		add(creditLabel);
		add(credits);
		
		
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
		ArrayList<Approval> links = Approval.getAllFromDB();
		for (Approval current: links) {
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
		setBorder(BorderFactory.createTitledBorder("Add or Remove an Approval between a Module and Degree"));
		
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
			if (optionSelection.getSelectedItem() == "Add an Approval") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove an Approval") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected approval from the database
		else if (command.equals("Remove")) {
			Approval toRemove = (Approval)removeOption.getSelectedItem();
			Admin.removeApproval(toRemove);
			JOptionPane.showMessageDialog(null, "The Approval has been Removed");
			removeOption.removeAllItems();
			removeAccount();
		}
		//Adds an approval into the database
		else if (command.equals("Add")) {
			String cor = core.getText();
			String lev = level.getText();
			String cred = credits.getText();
			Module modToLink = (Module)modules.getSelectedItem();
			Degree degToLink = (Degree)degrees.getSelectedItem();
			
			if ( (cor.length()==1 && checkInt(cor)) && (lev.length()==1) && (checkSize(11,cred.length()) && checkInt(cred)) ){
				int corInt = Integer.parseInt(cor);
				if (corInt == 1 || corInt == 0) {
					int credInt = Integer.parseInt(cred);
					Approval appToAdd = new Approval(degToLink,modToLink,corInt,credInt,lev.charAt(0));
					Admin.addSingleApproval(appToAdd);
					JOptionPane.showMessageDialog(null, "The Degree and Module are now Approved");
				}
				else {
					JOptionPane.showMessageDialog(null, "Core value isn't a 1 or a 0");
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