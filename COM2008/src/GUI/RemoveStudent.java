package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import dataaccess.Student;

public class RemoveStudent extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5306184016676448751L;
	
	Frame frame; 
	
	//Labels to identify the fields
	private JLabel regLabel = new JLabel("Registration Number: ");
	private JLabel stdLabel = new JLabel("Students: ");
	//error message
	private JLabel errorNum = new JLabel();
	
	//Fields for data entry
	//private JTextField registrationNum = new JTextField(10);
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	
	//button
	private JButton deleteButton = new JButton("Delete Student");
	
	public RemoveStudent(Frame frame){
		this.frame = frame;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Remove a Student"));
		init();
	}
	
	public void init() {
		removeAll();
		//limiting the input length 
		//registrationNum.setDocument(new JTextFieldLimit(11));
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student i : students) {
			studentSelection.addItem(i);
		}
		
		
		studentSelection.setMaximumSize(new Dimension(100000, (int)deleteButton.getMaximumSize().getHeight()));
		studentSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(stdLabel);
		add(studentSelection);
		//add(errorNum);
		
		add(deleteButton);
		deleteButton.addActionListener(this);
		
		//to let registrationNum only take integer inputs
		/*
		registrationNum.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = registrationNum.getText();
			    int l = value.length();
			    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
			    	registrationNum.setEditable(true);
			        errorNum.setText("");
			    }
			    else {
			        registrationNum.setEditable(false);
			        registrationNum.setText(null);
			        errorNum.setText("* Enter only numeric digits(0-9)");
			            	
			    }
			}
		});*/
		
	}
	
	
	/*
	public void clearFields() {
		registrationNum.setText(null);
	}*/

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Delete Student")) {
			Student student = (Student)studentSelection.getSelectedItem();
			
			//int reg = Integer.parseInt(registrationNum.getText());
			//need to work on error when the regNo is wrong
			//if (Student.regNumChecker(reg)) {
				//Student student = new Student(reg,"xx","axv","xrt","fake@fakes.com","tutor man");
				if(student.removeStudent()) {
					JOptionPane.showMessageDialog(null, "Sucessfully removed this student");
					studentSelection.removeAllItems();
					//clearFields();
					init();
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Error ocurred, try again");
					//clearFields();
				}
				
			//}
			/*
			else {
				JOptionPane.showMessageDialog(null, "No Student with this registration");
				clearFields();
			}*/
			
		}
		
		
	}
	
	
	

}
