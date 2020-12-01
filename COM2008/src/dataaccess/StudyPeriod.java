package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import businesslogic.Teacher;

public class StudyPeriod {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private char label;
	private String startDate;
	private String endDate;
	private Student student;
	private double meanGrade;
	private Teacher.Progression progression;
	private int storedRegistration;
	
	public StudyPeriod(char label, String startDate, String endDate, Student student) {
		this.label = label;
		this.startDate = startDate;
		this.endDate = endDate;
		this.student = student;
		storedRegistration = this.student.getRegistration();
		this.meanGrade = 0;
	}
	public StudyPeriod(char label, String startDate, String endDate, Student student, double meanGrade, Teacher.Progression progression) {
		this(label, startDate, endDate, student);
		this.meanGrade = meanGrade;
		this.progression = progression;
	}
	
	//get methods (May need to create a set for the student object)
	public char getLabel() {
		return label;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public Student getStudent() {
		return student;
	}
	public int getStoredRegistration() {
		return storedRegistration;
	}
	public double getMeanGrade() {
		return meanGrade;
	}
	public Teacher.Progression getProgression() {
		return progression;
	}
	
	//set methods
	public void setLabel(char label) {
		this.label = label;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndState(String endDate) {
		this.endDate = endDate;
	}
	
	//Database ----------------------------------------------------------------------------
	
		/*
		 * Inserts aStudy Period to be associated with a Student
		 * 
		 * @Return a Boolean function 
		 */
		public boolean addStudyPeriod () {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("INSERT INTO StudyPeriod (label,startDate,endDate,registration)VALUE ('" + 
												this.getLabel() + "','" +
												this.getStartDate() + "','" +
												this.getEndDate() + "','" +
												this.getStoredRegistration() + "');"
												);
				System.out.println("Changes made: " + count);
				switch (count) {
					case 0:
						return false;
					default:
						return true;
					
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * Removes a Study Period associated with a Student
		 * 
		 * @Return a Boolean function 
		 */
		public boolean removeStudyPeriod() {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("DELETE FROM StudyPeriod WHERE " + 
						 						"label = '" + this.getLabel() + "' AND " + 
						 						"registration = '" + this.getStoredRegistration() + "';"
												);
				System.out.println("Changes made: " + count);
				switch (count) {
					case 0:
						return false;
					default:
						return true;
					
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * get a degree from database
		 * 
		 * @param degCode - degree code of degree
		 * 
		 * @return degree matching code
		 */
		public static StudyPeriod retrieveFromDB(char label, int registration) {
			ArrayList<StudyPeriod> periods = new ArrayList<StudyPeriod>();
	
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
				Statement stmt = con.createStatement();
				
				// get all the degrees matching code
				ResultSet rs =  stmt.executeQuery("SELECT * FROM StudyPeriod WHERE " + 
						 						  "label = '" + label + "' AND " +
												  "registration = '" + registration + "';");
				// build list of degrees
				while(rs.next()) {
					StudyPeriod period;
					// if a valid progression is present, include it and mean grade
					if (rs.getInt("progression") > 0 && rs.getInt("progression") <= 8) {
						//System.out.println("found progression" + rs.getInt("progression"));
						period = new StudyPeriod(rs.getString("label").charAt(0), rs.getString("startDate"),
												 rs.getString("endDate"), Student.retrieveFromDB(rs.getInt("registration")),
												 rs.getFloat("meanGrade"), Teacher.Progression.values()[(rs.getInt("progression"))]);
					}
					else {
						period = new StudyPeriod(rs.getString("label").charAt(0), rs.getString("startDate"),
							 					 rs.getString("endDate"), Student.retrieveFromDB(rs.getInt("registration")));
					}
					periods.add(period);
					
				}
			}
			
			
			catch (Exception ex) {
				ex.printStackTrace();
			}
			// return first(and only) degree
			return periods.get(0);
		}
		
		/*
		 * get performances associated with the study period
		 * 
		 * @return list of performances
		 */
		public ArrayList<Performance> getPerformances() {
			ArrayList<Performance> performances = new ArrayList<Performance>();
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
				Statement stmt = con.createStatement();
				// get all the performances associated with period
				ResultSet rs =  stmt.executeQuery("SELECT * FROM Performance WHERE " +
						 						  "registration = '" + this.storedRegistration + "' AND " +
												  "label = '" + this.label + "' ;");
				
				while(rs.next()) {
					Approval approval = Approval.retrieveFromDB(rs.getString("degCode"), rs.getString("modCode"));
					Performance performance = new Performance(this, approval, rs.getInt("grade"), rs.getInt("resitGrade"));
					performances.add(performance);
				}
			}
			
			catch (Exception ex) {
				ex.printStackTrace();
			}
			return performances;
		}
		
		/*
		 * get the study level associated with the study period
		 * 
		 * @return char representing level
		 */
		public char getLevel() {
			ArrayList<Performance> performances = this.getPerformances();
			return (performances.get(0).getApproval().getLevel());
		}
		
		/*
		 * set mean grade
		 * 
		 * @param grade -  the mean grade
		 * 
		 * @return boolean based one success
		 */
		public boolean addMeanGrade(double grade) {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("UPDATE StudyPeriod SET meanGrade = '" + grade + 
						"'WHERE registration = '" + this.storedRegistration + "' AND " +
						" label = '" + this.label + "';"
						);
				switch(count) {
				case 0:
					return false;
				default:
					return true;
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * set progression
		 * 
		 * @param progression - the progression
		 * 
		 * @return boolean based one success
		 */
		public boolean addProgression(Teacher.Progression progression) {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("UPDATE StudyPeriod SET progression = '" + progression.ordinal() + 
						"'WHERE registration = '" + this.storedRegistration + "' AND " +
						" label = '" + this.label + "';"
						);
				switch(count) {
				case 0:
					return false;
				default:
					return true;
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * Testing functions. 
		 * Invalid and won't work until the Table Student is populated
		 * FK is needed
		 */
		public static void main(String[] args) {
			//Test Method
			//Student George = new Student(1255214, "Mr", "Ashcro", "George","george@fake.com");
			//System.out.println(George.addStudent());
			//StudyPeriod Test = new StudyPeriod('a', "2020-11-19","2021-11-19", George);
			//System.out.println(Test.addStudyPeriod());
			//System.out.println(George.removeStudent());
			//System.out.println(Test.removeStudyPeriod());
			}
}
