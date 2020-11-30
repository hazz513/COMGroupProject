package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import businesslogic.Teacher;

public class Student {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private int registration;
	private String title;
	private String surname;
	private String forename;
	private String email;
	
	/*
	 * Constructor
	 * 
	 * @param registration = Registration Number
	 * @param title = Title of 
	 * @param surname = Surname
	 * @param forename = forename
	 * @param email = Email
	 */
	public Student(int registration, String title, String surname, String forename, String email) {
		this.registration = registration;
		this.title = title;
		this.surname = surname;
		this.forename = forename;
		this.email = email;
	}
	
	// get methods
	public int getRegistration() {
		return registration;
	}
	public String getTitle() {
		return title;
	}
	public String getSurname() {
		return surname;
	}
	public String getForename() {
		return forename;
	}
	public String getEmail( ) {
		return email;
	}
	//set methods
	public void setRegistration(int registration) {
		this.registration = registration;
	}
	public void  setTitle(String title) {
		this.title = title;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// database ---------------------------------------------------------------------------
	
	/*
	 * Adds a student to the database
	 * @return returns a boolean output
	 */
	public boolean addStudent() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("INSERT INTO Student VALUE ('" + 
											this.getRegistration() + "','" +
											this.getTitle() + "','" +
											this.getSurname() + "','" +
											this.getForename() + "','" +
											this.getEmail() + "');"
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
	 * Removes a student from the database
	 * @return returns a boolean output
	 */
	public boolean removeStudent() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("DELETE FROM Student WHERE " +
										   "registration = '" + this.getRegistration() + "';"
										   );
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
	 * get a student from database
	 * 
	 * @param registration - registration number of student
	 * 
	 * @return student matching registration
	 */
	public static Student retrieveFromDB(int registration) {
		ArrayList<Student> students = new ArrayList<Student>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Student WHERE " + 
					 						  "registration = '" + registration +"' ;");
			
			// build list of degrees
			while(rs.next()) {
				Student student = new Student(rs.getInt("registration"), rs.getString("title"), rs.getString("surname"), rs.getString("forename"), rs.getString("email"));
				students.add(student);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return first(and only) degree
		return students.get(0);
	}
	
	/*
	 * get periods associated with the student
	 * 
	 * @return list of periods
	 */
	public ArrayList<StudyPeriod> getPeriods() {
		ArrayList<StudyPeriod> periods = new ArrayList<StudyPeriod>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the periods associated with student
			ResultSet rs =  stmt.executeQuery("SELECT * FROM StudyPeriod WHERE " +
					 						  "registration = '" + this.registration + "' ;");
			
			// build list of periods
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
		
		return periods;
	}
	
	public boolean canRepeat(char level) {
		ArrayList<StudyPeriod> periods = this.getPeriods();
		
		boolean containsLevel = false;
		for (StudyPeriod period: periods) {
			char periodLevel = period.getLevel();
			
			if (containsLevel && periodLevel == level) {
				return false;
			}
			else if (periodLevel == level) {
				containsLevel = true;
			}
		}
		return true;
	}
	
	public static ArrayList<Student> getAllFromDB() {
		ArrayList<Student> students = new ArrayList<Student>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Student;");
			
			// build list of students
			while(rs.next()) {
				Student student = new Student(rs.getInt("registration"), rs.getString("title"), rs.getString("surname"), rs.getString("forename"), rs.getString("email"));
				students.add(student);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of students
		return students;
	}
	
	public String toString() {
		return (this.title + ". " + this.forename + " " +
				this.surname + ", " + this.registration);
	}
	
	/*
	 * Testing method 
	 */
	public static void main(String[] args) {
		//test Method
		//Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com");
		//System.out.println(George.addStudent());
		//System.out.println(George.removeStudent());
	}
	
}
