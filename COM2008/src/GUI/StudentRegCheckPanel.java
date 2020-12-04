package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import businesslogic.Registrar;
import dataaccess.Performance;
import dataaccess.Student;
import dataaccess.StudyPeriod;

public class StudentRegCheckPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 5842733073298936194L;
	
	Frame frame;
	
	//labels
	private JLabel studentLabel = new JLabel("Students: ");
	private JLabel labelLabel = new JLabel("Labels: ");
	
	//buttons
	private JButton checkButton = new JButton("Check");
	//combobox
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	JComboBox<Character>labelSelection = new JComboBox<Character>();
	
	public StudentRegCheckPanel(Frame frame) {
		this.frame = frame;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Check Credit"));
		init();
	}

	public void init() {
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student i : students) {
			studentSelection.addItem(i);
		}
		
		//controlling the size of dropdown panel
		studentSelection.setMaximumSize(new Dimension(100000, (int)checkButton.getMaximumSize().getHeight()));
		studentSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelSelection.setMaximumSize(new Dimension(100000, (int)checkButton.getMaximumSize().getHeight()));
		labelSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				
		//dropdown panel
		add(studentLabel);
		add(studentSelection);
		add(labelLabel);
		add(labelSelection);
				
		//button
		add(checkButton);
		
		checkButton.addActionListener(this);
		
		studentSelection.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent event) {
		    	 JComboBox studentSelection = (JComboBox) event.getSource();

	                // Print the selected items and the action command.
	                Student selected = (Student)studentSelection.getSelectedItem();
	                //System.out.println("Selected Item  = " + selected);
	                ArrayList<StudyPeriod> periods = selected.getPeriods();
	                ArrayList<Character> label= new ArrayList<Character>();
	                label.clear();
	                for(StudyPeriod i: periods) {
	                	label.add(i.getLabel());
	                }
	                for (char i:label) {
	                	labelSelection.addItem(i);
	                }
	                
		    }
		});
		
				
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if(command.equals("Check")) {
			Student student = (Student)studentSelection.getSelectedItem();
			Character label = (Character)labelSelection.getSelectedItem();
			
			if (label !=null) {
				StudyPeriod studyPeriod = new StudyPeriod(label,"2020","2021",student);
				boolean checkReg = Registrar.checkStudentReg(studyPeriod);
				
				if(checkReg) {
					JOptionPane.showMessageDialog(null, "This student is fully registered for this study period");
				}
				else {
					int totalCredits = Registrar.creditChecker(studyPeriod);
					ArrayList<Performance> list = studyPeriod.getPerformances();
					String degreeType = list.get(0).getApproval().getDegree().getCode();
					int tCredit = 100000;
					
					if (degreeType.charAt(3)=='U') {
						tCredit = 120;
					}
					else {
						tCredit = 160;
					}

					if(totalCredits >0) {
						JOptionPane.showMessageDialog(null, "Student needs "+totalCredits +" more credits to register ");
						labelSelection.removeAllItems();
					}
					else if(totalCredits >0) {
						JOptionPane.showMessageDialog(null, "Student has "+ Math.abs(totalCredits) +" more credits than required to register ");
						labelSelection.removeAllItems();
					}
					else {
						JOptionPane.showMessageDialog(null, "An error has occured");
						labelSelection.removeAllItems();
					}
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "No label entered!");
				labelSelection.removeAllItems();
			}
			
		}
		
		
	}
	

}
