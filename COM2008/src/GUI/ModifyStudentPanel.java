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
	private JLabel tutorLabel = new JLabel("Perosnal Tutor: ");
	//error message
	private JLabel errorNum = new JLabel("Date Format YYYY-MM-DD");
	private JLabel empty = new JLabel(" ");
	
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
	private JTextField personalTutor = new JTextField(10);
	
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
		personalTutor.setDocument(new JTextFieldLimit(45));
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
		add(tutorLabel);
		add(personalTutor);
		//add(empty);
		
		
		
		add(registerButton);
		//add(empty);
		add(clearAllButton);
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
		personalTutor.setText(null);
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
					endDate.getText()==null||endDate.getText().trim().isEmpty()||
					personalTutor.getText()==null||personalTutor.getText().trim().isEmpty()))
			{
				/*
				String sYear = startDate.getText().substring(0,4);
				String mYear = startDate.getText().substring(5,7);
				String dYear = startDate.getText().substring(8,10);
				
				String sYear1 = endDate.getText().substring(0,4);
				String mYear1 = endDate.getText().substring(5,7);
				String dYear1 = endDate.getText().substring(8,10);
				
				String tDate = sYear +""+mYear+""+dYear;
				String tDate1 = sYear1 +""+mYear1+""+dYear1;
				
				char dash1 = startDate.getText().charAt(4);
				char dash2 = startDate.getText().charAt(7);
				
				
				System.out.println(sYear);
				System.out.println(dash1);
				System.out.println(mYear);
				System.out.println(dash2);
				System.out.println(dYear);
				System.out.println(tDate);
				*/
				
				if( 
					startDate.getText().length()==10 && endDate.getText().length()==10 && checkInt(startDate.getText().substring(0,1))&&
					checkInt(startDate.getText().substring(0,4)) && checkInt(startDate.getText().substring(5,7))&&
					checkInt(startDate.getText().substring(8,10))&& checkInt(endDate.getText().substring(0,4))&&
					checkInt(endDate.getText().substring(5,7))&& checkInt(endDate.getText().substring(8,10))&& 
					startDate.getText().charAt(4)=='-'&& startDate.getText().charAt(7)=='-' && 
					endDate.getText().charAt(4)=='-'&& endDate.getText().charAt(7)=='-' && Integer.parseInt(endDate.getText().substring(0,4))-
							Integer.parseInt(startDate.getText().substring(0,4))== 1) 
				{
				
					int reg = Student.regNumGenerator();
					String title = studentTitle.getText();
					String sur = studentSurname.getText();
					String fore = studentForename.getText();
					String email = Student.emailGenerator(sur,fore);
					String sDate =startDate.getText();
					String eDate = endDate.getText();
					String tutor = personalTutor.getText();
					Student student = new Student (reg,title,sur,fore,email,tutor);
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
						Authentication newUser = new Authentication(student);
						newUser.addAuthentication();
					
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
					JOptionPane.showMessageDialog(null, "Incorrect Date Entry");
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
	        // System.out.println("It's an int");
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	    	// System.out.println("It's not an int");
	      }
	      
	      return isValidInteger;
   }
			
}
