package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Admin;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminAccountsPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	/*
	 * Initial functions to use
	 */
	Frame frame;
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Authentication> removeOption = new JComboBox<Authentication>();
	
	private JTextField usernameID = new JTextField(6);
	private JTextField password = new JPasswordField(10);
	private JTextField authLevel = new JPasswordField(1);
	private JTextField registration = new JPasswordField(10);
	
	private JLabel userLabel = new JLabel("User login: ");
	private JLabel passLabel = new JLabel("Password: ");
	private JLabel authLabel = new JLabel("Authorization (1-4): ");
	private JLabel regLabel = new JLabel("Registration (Only needed for students):");
	
	private JButton remove = new JButton("Remove");
	private JButton confirm = new JButton("Add");
	private JButton cancel = new JButton("Cancel");
	
	/*
	 * Constructor
	 */
	public AdminAccountsPanel(Frame frame) {
		this.frame = frame;
		initializePanel();
		// create dropdown menu to select an option
		optionSelection.addItem("Add an Account");
		optionSelection.addItem("Remove an Account");
	}
	
	/*
	 * Construct UI for adding an account
	 */
	public void addAccount() {
		System.out.println("Anything");
		removeAll();
		setLayout(new FlowLayout());
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
	/*
	 * Constructs UI to remove account
	 */
	public void removeAccount() {
		removeAll();
		setLayout(new FlowLayout());
		//Gets list of all accounts and adds to dropdown
		ArrayList<Authentication> users = Authentication.getAllFromDB();
		for (Authentication current: users) {
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
	
	/*
	 * Reads inputs from buttons and drop down menu
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		// on button click
		if (command.equals("Confirm Option")) {
			// assign student from dropdown
			if (optionSelection.getSelectedItem() == "Add an Account") {
				addAccount();
			}
			else if (optionSelection.getSelectedItem() == "Remove an Account") {
				removeAccount();
			}
		}
		else if (command.equals("Cancel")) {
			removeOption.removeAllItems();
			initializePanel();
		}
		//Removes a selected user from the database
		else if (command.equals("Remove")) {
			Authentication toRemove = (Authentication)removeOption.getSelectedItem();
			Admin.removeAccounts(toRemove);
			JOptionPane.showMessageDialog(null, "The user has been Removed");
			removeOption.removeAllItems();
			initializePanel();
		}
		//Adds a user into the database
		else if (command.equals("Add")) {
			String userID = usernameID.getText();
			String pass = password.getText();
			String authS = authLevel.getText();
			String registS = registration.getText();
			if ((checkSize(10,userID.length())) && (checkSize(11,pass.length())) && (checkSize(1,authS.length())) && ((checkSize(11,registS.length()) || (registS.isEmpty())))){
				if (!registS.isEmpty()) {
					int regist = Integer.parseInt(registration.getText());
					int auth = Integer.parseInt(authLevel.getText());
					Authentication newUser = new Authentication(userID,pass,auth,regist);
					if (Admin.addAccounts(newUser)){
						JOptionPane.showMessageDialog(null, "The new user has been added");
						initializePanel();
					}
					else {
						JOptionPane.showMessageDialog(null, "An error has occurred");
					}
				}
				else if ((registS.isEmpty()) && (!authS.isEmpty())){
					int auth = Integer.parseInt(authLevel.getText());
					Authentication newUser = new Authentication(userID,pass,auth);
					if (Admin.addAccountsNoReg(newUser)){
						JOptionPane.showMessageDialog(null, "The new user has been added");
						initializePanel();
					}
					else {
						JOptionPane.showMessageDialog(null, "An error has occurred");
					}
				}	
			}
			else {
				JOptionPane.showMessageDialog(null, "Invalid character length");
			}
		}
	}
	
	/*
	 * Checks inputs and responds if they are valid
	 */
	public boolean checkSize(int max, int length) {
		if ((length > max ) || (length == 0)) {
			return false;
		}
		else {
			return true;
		}
	}
	
}