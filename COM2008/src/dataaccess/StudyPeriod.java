package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StudyPeriod {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private char label;
	private String startDate;
	private String endDate;
	private Student student;
	private int storedRegistration;
	
	public StudyPeriod(char label, String startDate, String endDate, Student student) {
		this.label = label;
		this.startDate = startDate;
		this.endDate = endDate;
		this.student = student;
		storedRegistration = this.student.getRegistration();
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
				int count = stmt.executeUpdate("INSERT INTO StudyPeriod VALUE ('" + 
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
		 * Testing functions. 
		 * Invalid and won't work until the Table Student is populated
		 * FK is needed
		 */
		public static void main(String[] args) {
			//Test Method
			//Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com");
			//System.out.println(George.addStudent());
			//StudyPeriod Test = new StudyPeriod('a', "2020-11-19","2021-11-19", George);
			//System.out.println(Test.addStudyPeriod());
			//System.out.println(George.removeStudent());
			//System.out.println(Test.removeStudyPeriod());
			}
}
