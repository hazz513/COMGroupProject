package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dataaccess.*;
import businesslogic.*;



public class ModifyStudentPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -8844619246235661365L;

	Frame frame; 
		
	//Labels to identify the fields
	private JLabel titleLabel = new JLabel("Title: ");
	private JLabel surnameLabel = new JLabel("Surname: ");
	private JLabel forenameLabel = new JLabel("Forename: ");
	private JLabel degreeCodeLabel = new JLabel("Degree: ");
	private JLabel startDateLabel = new JLabel("Start Date: ");
	private JLabel endDateLabel = new JLabel("End Date: ");
	
	//error message
	private JLabel errorNum = new JLabel("Date Format YYYY-MM-DD");
	
	//Fields for data entry
	//private JTextField registrationNum = new JTextField(10);
	private JTextField studentTitle = new JTextField(10);
	private JTextField studentSurname = new JTextField(10);
	private JTextField studentForename = new JTextField(10);
	//private JTextField degreeCode = new JTextField(10);
	JComboBox<Degree> degreeSelection = new JComboBox<Degree>();
	//private JTextField leadDep = new JTextField(10);
	private JTextField startDate = new JTextField(10);
	private JTextField endDate = new JTextField(10);
	
	//button
	private JButton registerButton = new JButton("Register");
	private JButton clearAllButton = new JButton("ClearAll");
	
	public ModifyStudentPanel(Frame frame) {
		this.frame = frame;
		setLayout(new GridLayout(0,1));
		setBorder(BorderFactory.createTitledBorder("Register new Student"));
		init();		
		
	}
	
	public void init() {
		//limiting the input length 
		studentTitle.setDocument(new JTextFieldLimit(45));
		studentSurname.setDocument(new JTextFieldLimit(45));
		studentForename.setDocument(new JTextFieldLimit(45));
		startDate.setDocument(new JTextFieldLimit(10));
		endDate.setDocument(new JTextFieldLimit(10) );
		//degreeCode.setDocument(new JTextFieldLimit(6));
		//leadDep.setDocument(new JTextFieldLimit(3));
		
		ArrayList<Degree> degrees = Degree.getAllFromDB();
		for (Degree degree : degrees) {
			degreeSelection.addItem(degree);
		}
		
		
		//add(errorNum);
		add(titleLabel);
		add(studentTitle);
		add(surnameLabel);
		add(studentSurname);
		add(forenameLabel);
		add(studentForename);
		add(degreeCodeLabel);
		add(degreeSelection);
		//add(leadDepLabel);
		//add(leadDep);
		add(errorNum);
		add(startDateLabel);
		add(startDate);
		add(endDateLabel);
		add(endDate);
		
		
		
		add(clearAllButton);
		add(registerButton);
		clearAllButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		/*
		//to let registrationNum only take integer inputs
		registrationNum.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent ke) {
	            String value = registrationNum.getText();
	            int l = value.length();
	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
	            	registrationNum.setEditable(true);
	            	errorNum.setText("");
	            } else {
	            	registrationNum.setEditable(false);
	            	registrationNum.setText(null);
	            	errorNum.setText("* Enter only numeric digits(0-9)");
	            	
	            }
	         }
		});*/
		
		/*studentTitle.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent ke) {
	            String value = studentTitle.getText();
	            int l = value.length();
	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
	            	studentTitle.setEditable(false);
	            	studentTitle.setText(null);
	            	errorNum.setText("No number in titles");
	            } else {
	            	studentTitle.setEditable(true);
	            	errorNum.setText("");
	            	
	            }
	         }
		});*/
		
		
	}
	
	public void clearFields() {
		studentTitle.setText(null);
		studentSurname.setText(null);
		studentForename.setText(null);
		startDate.setText(null);
		endDate.setText(null);
		//degreeCode.setText(null);
		//leadDep.setText(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Register")) {
			
			if (!(	studentTitle.getText()==null||studentTitle.getText().trim().isEmpty()||
					studentSurname.getText()==null||studentSurname.getText().trim().isEmpty()||	
					studentForename.getText()==null||studentForename.getText().trim().isEmpty()||
					startDate.getText()==null||startDate.getText().trim().isEmpty()||
					endDate.getText()==null||endDate.getText().trim().isEmpty()))
			{
				String sDateCheck = startDate.getText().substring(0,4);
				
				//String eDateCheck = endDate.getText().substring(0,3)+endDate.getText().substring(5,6)+endDate.getText().substring(7,8);
				System.out.println(sDateCheck);
				
				int reg = Student.regNumGenerator();
				String title = studentTitle.getText();
				String sur = studentSurname.getText();
				String fore = studentForename.getText();
				String email = Student.emailGenerator(sur,fore);
				String sDate =startDate.getText();
				String eDate = endDate.getText();
				Student student = new Student (reg,title,sur,fore,email,"Tutor men");
				Degree degree = (Degree)degreeSelection.getSelectedItem();
								
				if(student.addStudent()) {
					/*
					Degree degree = (Degree)degreeSelection.getSelectedItem();
					ArrayList<Approval> cores = degree.getCores('1');
					StudyPeriod studyPeriod = new StudyPeriod('A',sDate,eDate,student);
					ArrayList<Performance> p = new ArrayList<Performance>();
					
					for(Approval i:cores) {
						 p.add(new Performance(studyPeriod,i,0));
					}	

					studyPeriod.addStudyPeriod();
					
					for(Performance i: p) {
						i.addPerformance();
					}
					*/
					System.out.println(student.getRegistration());
					System.out.println(degree.getCode());
					System.out.println(eDate);
					System.out.println(sDate);
					System.out.println(Registrar.registerStudent(student,sDate,eDate,degree));
					
					clearFields();
					JOptionPane.showMessageDialog(null, "New Student Registered"+"\n" + "Registration no: "+reg
							 +"\n"+"Email Id: "+ email+"\n");
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Student Entry");
					clearFields();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Error in New Student Registration");
				clearFields();
			}
				
			
		}
		else if (command.equals("ClearAll")) {
			clearFields();
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
