package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
	 * Testing method 
	 */
	public static void main(String[] args) {
		//test Method
		Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com");
		System.out.println(George.addStudent());
		System.out.println(George.removeStudent());
	}
	
}
