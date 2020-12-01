package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TeacherGradePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = -3563278903500335251L;

	Frame frame;
	
	// dropdowns for selection
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	Student currentStudent;
	JComboBox<StudyPeriod> periodSelection = new JComboBox<StudyPeriod>();
	StudyPeriod currentPeriod;
	JComboBox<Performance> performanceSelection = new JComboBox<Performance>();
	// buttons
	JButton showPeriods = new JButton("Show study periods");
	JButton showPerformances = new JButton("Show modules");
	JButton updateGrade = new JButton("Add/Update grade");
	JButton updateResitGrade = new JButton("Add/Update resit grade");
	// grade entry fields
	JTextField gradeEntry = new JTextField("Enter grade", 3);
	JTextField resitGradeEntry = new JTextField("Enter resit grade", 3);
	
	public TeacherGradePanel(Frame frame) {
		this.frame = frame;
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Add/Update Student Grades"));
		
		// set vertical layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initialize();
	}
	
	
	/*
	 * set sizes of elements, build student list,
	 * and add student selection combobox and button to proceed
	 */
	public void initialize() {
		// limit sizes of elements to certain height
		studentSelection.setMaximumSize(new Dimension(100000, (int)showPeriods.getMaximumSize().getHeight()));
		gradeEntry.setMaximumSize(new Dimension(100000, (int)showPeriods.getMaximumSize().getHeight()));
		resitGradeEntry.setMaximumSize(new Dimension(100000, (int)showPeriods.getMaximumSize().getHeight()));
		
		// limit length of text entry for validation and security
		gradeEntry.setDocument(new JTextFieldLimit(3));
		resitGradeEntry.setDocument(new JTextFieldLimit(3));
	
		// create dropdown menu to select student
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student student: students) {
			System.out.println(student);
			studentSelection.addItem(student);
		}
		
		// create button to initiate process
		showPeriods.addActionListener(this);
				
		// add dropdown to panel
		add(studentSelection);
		studentSelection.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		// add button to panel
		add(showPeriods);
		showPeriods.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	}
	
	/*
	 * add all the study periods of a student to the selection dropdown
	 * 
	 * @param student - the student for which the periods will be used
	 */
	public void buildPeriodComboBox(Student student) {
		// reset
		periodSelection = new JComboBox<StudyPeriod>();
		// add periods
		ArrayList<StudyPeriod> periods = student.getPeriods();
		for (StudyPeriod period: periods) {
			System.out.println(period);
			periodSelection.addItem(period);
		}
		// limit height
		periodSelection.setMaximumSize(new Dimension(100000, (int)showPeriods.getMaximumSize().getHeight()));
	}
	
	/*
	 * add all the performances of a period to the selection dropdown
	 * 
	 * @param period - the period for which the performances will be used
	 */
	public void buildPerformanceComboBox(StudyPeriod period) {
		// reset
		performanceSelection = new JComboBox<Performance>();
		// add periods
		ArrayList<Performance> performances = period.getPerformances();
		System.out.println(performances.size());
		for (Performance performance: performances) {
			System.out.println(performance);
			performanceSelection.addItem(performance);
		}
		// limit height
		performanceSelection.setMaximumSize(new Dimension(100000, (int)showPeriods.getMaximumSize().getHeight()));
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Show study periods")) {
			Student selectedStudent = (Student)studentSelection.getSelectedItem();
			// if the same option is being selected, do nothing
			if (selectedStudent != null && currentStudent != null && selectedStudent == currentStudent) {
				// do nothing
			}
			else {
				// remove old elements from previous selection
				remove(periodSelection);
				remove(performanceSelection);
				remove(showPerformances);
				remove(updateGrade);
				remove(updateResitGrade);
				remove(gradeEntry);
				remove(resitGradeEntry);
				// mark selection as the one being displayed
				currentStudent = selectedStudent;
				// add relevant periods to dropdown
				buildPeriodComboBox(selectedStudent);
				// check if there is content to be displayed
				if (periodSelection.getItemCount() == 0) {
					JOptionPane.showMessageDialog(null, "ERROR: No study periods found found.");
				}
				else {
					// add study period dropdown and align left
					add(periodSelection);
					periodSelection.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					// add button to show performances and align left
					showPerformances.addActionListener(this);
					add(showPerformances);
					showPerformances.setAlignmentX(JLabel.LEFT_ALIGNMENT);
				}
				// refresh
				frame.revalidate();
				frame.repaint();
			}
		}
		else if (command.equals("Show modules")) {
			StudyPeriod selectedPeriod = (StudyPeriod)periodSelection.getSelectedItem();
			// if the same option is being selected, do nothing
			if (selectedPeriod != null && currentPeriod != null && selectedPeriod == currentPeriod) {
				// do nothing
			}
			else {
				// remove old elements from previous selection
				remove(performanceSelection);
				remove(updateGrade);
				remove(updateResitGrade);
				remove(gradeEntry);
				remove(resitGradeEntry);
				// mark selection as the one being displayed
				currentPeriod = selectedPeriod;
				// add relevant performances to dropdown
				buildPerformanceComboBox(selectedPeriod);
				// check if there is content to be displayed
				if (performanceSelection.getItemCount() == 0) {
					JOptionPane.showMessageDialog(null, "ERROR: No modules found.");
				}
				else {
					// add dropdown and align left
					add(performanceSelection);
					performanceSelection.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					// add buttons and text entries, and align left
					updateGrade.addActionListener(this);
					updateResitGrade.addActionListener(this);
					add(gradeEntry);
					gradeEntry.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					add(updateGrade);
					updateGrade.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					add(resitGradeEntry);
					resitGradeEntry.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					add(updateResitGrade);
					updateResitGrade.setAlignmentX(JLabel.LEFT_ALIGNMENT);
				}
				// refresh
				frame.revalidate();
				frame.repaint();
			}
		}
		else if (command.equals("Add/Update grade")) {
			// retrieve performance
			Performance performance = (Performance)performanceSelection.getSelectedItem();
			// check if valid int
			String entry = gradeEntry.getText();
			if (entry.matches("[0-9]+")) {
				// update/add grade
				performance.setGrade(Integer.parseInt(entry));
				performance.updateGrades();
			}
			else {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid entry.");
			}
		}
		else if (command.equals("Add/Update resit grade")) {
			// retrieve performance
			Performance performance = (Performance)performanceSelection.getSelectedItem();
			// check if valid int
			String entry = resitGradeEntry.getText();
			if (entry.matches("[0-9]+")) {
				// update/add grade
				performance.setGrade(Integer.parseInt(entry));
				performance.updateResitGrades();
			}
			else {
				JOptionPane.showMessageDialog(null, "ERROR: Invalid entry.");
			}
		}
	}

}
