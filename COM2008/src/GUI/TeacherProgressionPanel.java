package GUI;

import java.awt.*;
import javax.swing.*;

import GUI.Frame;
import businesslogic.Teacher;
import dataaccess.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TeacherProgressionPanel  extends JPanel implements ActionListener{
	private static final long serialVersionUID = -4615865294577288857L;
	
	Frame frame;
	
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	
	public TeacherProgressionPanel(Frame frame) {
		this.frame = frame;
		// styling and title
		setBorder(BorderFactory.createTitledBorder("Calculate Student's Progression Status"));
		// set vertical layout
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// create dropdown menu to select student
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student student: students) {
			studentSelection.addItem(student);
		}
		
		// add dropdown to panel
		add(studentSelection);
		
		// add button to initiate process
		JButton getProgression = new JButton("check progression");
		getProgression.addActionListener(this);
		
		add(getProgression);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		int progress = JOptionPane.NO_OPTION;
		
		// on button click
		if (command.equals("check progression")) {
			// assign student from dropdown
			Student student = (Student)studentSelection.getSelectedItem();
			// default popup message
			String message = "No study period found.";
			
			// get highest period
			ArrayList<StudyPeriod> periods = student.getPeriods();
			
			// if there are study periods to check then check the latest one
			if (periods.size() > 0) {
				periods.sort(new Teacher.StudyPeriodComparator());
				StudyPeriod period = periods.get(periods.size()-1);
				// get the progression for it
				Teacher.Progression progression = Teacher.progression(period);
				message = ("the progression status is: " + progression);
				
				// add results to database
				period.addMeanGrade(Teacher.meanGrade(period.getPerformances()));
				period.addProgression(progression);
				// if a new study period is to be made then ask to proceed
				if (progression == Teacher.Progression.PROGRESS || progression == Teacher.Progression.REPEAT) {
					progress = JOptionPane.showConfirmDialog(null, message + ", would you like to apply this?", "Progress Student", JOptionPane.YES_NO_OPTION);
			
					// if user chooses to proceed with progression
					if (progress == JOptionPane.YES_OPTION) {
						// generate new start and end dates for nextperiod
						String newStartDate = period.getStartDate();
						String newEndDate = period.getEndDate();
						if (progression == Teacher.Progression.PROGRESS) {
							String newStartYear = Integer.toString(Integer.parseInt(period.getStartDate().substring(0,4))+1);
							String newEndYear = Integer.toString(Integer.parseInt(period.getEndDate().substring(0,4))+1);
							newStartDate = newStartYear + period.getStartDate().substring(4);
							newEndDate = newEndYear + period.getEndDate().substring(4);
						}
						// create new study period
						boolean success = Teacher.progressStudent(period, newStartDate, newEndDate);
						// message based on successful completion
						if (success) {
							JOptionPane.showMessageDialog(null, "Next study period generated");
						}
						else {
							JOptionPane.showMessageDialog(null, "A problem occurred in generating the next study period, The student may not have permission to progress");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, message);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, message);
			}
		}
	}

}
