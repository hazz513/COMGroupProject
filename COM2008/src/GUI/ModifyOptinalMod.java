package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dataaccess.*;
import businesslogic.*;

public class ModifyOptinalMod extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1516476441914107348L;
	
	Frame frame;
	
	private JLabel studentLabel = new JLabel("Students: ");
	private JLabel moduleLabel = new JLabel("Modules: ");
	
	JComboBox<String> optionSelection = new JComboBox<String>();
	JComboBox<Student> studentSelection = new JComboBox<Student>();
	JComboBox<Approval> moduleSelection = new JComboBox<Approval>();
	JComboBox<Approval> optModSelection = new JComboBox<Approval>();
	
	private JButton getInput = new JButton("Confirm Option");
	private JButton addInput = new JButton("Add");
	private JButton removeInput = new JButton("Remove");
	private JButton cancelInput = new JButton("Cancel");
	
	char labelS;
	char labelSD;
	
	public ModifyOptinalMod(Frame frame) {
		this.frame = frame;
		setBorder(BorderFactory.createTitledBorder("Add/Remove Optional Module of a Student"));
		
		optionSelection.addItem("Add a Module");
		optionSelection.addItem("Remove a Module");
		
		init();
	}
	
	public void init() {
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		removeAll();
		setLayout(new FlowLayout());
		
		
		//controlling the size of dropdown panel
		optionSelection.setMaximumSize(new Dimension(100000, (int)getInput.getMaximumSize().getHeight()));
		optionSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(optionSelection);
		add(getInput);
		
		getInput.addActionListener(this);
		
		revalidate();
		repaint();
		
	}
	
	//add module function
	public void addModule() {
		removeAll();
		setLayout(new FlowLayout());
		
		ArrayList<Student> students = Student.getAllFromDB();
		for (Student i : students) {
			studentSelection.addItem(i);
		}
		
		studentSelection.setMaximumSize(new Dimension(100000, (int)addInput.getMaximumSize().getHeight()));
		studentSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(studentLabel);
		add(studentSelection);
		add(moduleLabel);
		add(moduleSelection);
		add(addInput);
		add(cancelInput);
		addInput.addActionListener(this);
		cancelInput.addActionListener(this);
		
		studentSelection.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent event) {
		    	 JComboBox studentSelection = (JComboBox) event.getSource();
	             
		    	 Student selected = (Student)studentSelection.getSelectedItem();
		    	 ArrayList<StudyPeriod> sPeriods = selected.getPeriods();
	             StudyPeriod currentSPeriod = sPeriods.get(sPeriods.size() - 1);
	             labelS = currentSPeriod.getLabel();
	             ArrayList<Performance> performances =  currentSPeriod.getPerformances();
	             ArrayList<Approval> approvals = new  ArrayList<Approval>();
	             approvals = Registrar.gatherOptModules(performances.get(performances.size()-1));
	             for (Approval i:approvals) {
	            	 moduleSelection.addItem(i);
	             }   
		    }
		});
		
		frame.revalidate();
		frame.repaint();
	}
	
	//remove module function
	public void removeModule() {
		removeAll();
		setLayout(new FlowLayout());
		
		ArrayList<Student> students1 = Student.getAllFromDB();
		for (Student i : students1) {
			studentSelection.addItem(i);
		}
		
		studentSelection.setMaximumSize(new Dimension(100000, (int)removeInput.getMaximumSize().getHeight()));
		studentSelection.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(studentLabel);
		add(studentSelection);
		add(moduleLabel);
		add(moduleSelection);
		add(removeInput);
		add(cancelInput);
		removeInput.addActionListener(this);
		cancelInput.addActionListener(this);
		
		studentSelection.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent event) {
		    	 JComboBox studentSelection = (JComboBox) event.getSource();
	             
		    	 Student selected = (Student)studentSelection.getSelectedItem();
		    	 ArrayList<StudyPeriod> sPeriods = selected.getPeriods(); 
	             StudyPeriod currentSPeriod = sPeriods.get(sPeriods.size() - 1);
	             labelSD = currentSPeriod.getLabel();
	             ArrayList<Performance> performances =  currentSPeriod.getPerformances();
	             //ArrayList<Approval> approvals1 = new  ArrayList<Approval>();;
	             //ArrayList<Approval> approvals = new  ArrayList<Approval>();
	             ArrayList<Approval> approvals3 = new  ArrayList<Approval>();
	             
	             for(Performance i: performances) {
	            	 if(i.getApproval().getCore()==0) {
	            		 approvals3.add(i.getApproval());
	            	 }
	            	 else {
	            		 System.out.println("CoreMod removed");
	            	 }
	             }
	             
	             for (Approval i:approvals3) {
	            	 moduleSelection.addItem(i);
	             }    
		    }
		});
		
		frame.revalidate();
		frame.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if (command.equals("Confirm Option")) {
			if (optionSelection.getSelectedItem()== "Add a Module") {
				addModule();
			}
			else if(optionSelection.getSelectedItem()== "Remove a Module") {
				removeModule();
			}
		}
		else if(command.equals("Cancel")) {
			init();	
		}
		else if(command.equals("Add")) {
			Student selectedS = (Student)studentSelection.getSelectedItem();
			Approval selectedA = (Approval)moduleSelection.getSelectedItem();
			StudyPeriod studyperiod = new StudyPeriod(labelS,"2020","2021",selectedS);
			Performance performance = new Performance(studyperiod,selectedA,0);
			if(performance.addPerformance()) {
				JOptionPane.showMessageDialog(null, "New Optinal Module Added");
			}
			else {
				JOptionPane.showMessageDialog(null, "Error, Try again");
			}
			
		}
		else if(command.equals("Remove")) {
			Student selectedT = (Student)studentSelection.getSelectedItem();
			Approval selectedB = (Approval)moduleSelection.getSelectedItem();
			StudyPeriod studyperiod = new StudyPeriod(labelSD,"2020","2021",selectedT);
			Performance performance = new Performance(studyperiod,selectedB,0);
			if(performance.removePerformance()) {
				JOptionPane.showMessageDialog(null, "Optinal Module Removed");
			}
			else {
				JOptionPane.showMessageDialog(null, "Error, Try again");
			}
		}
		
	}
	

}
