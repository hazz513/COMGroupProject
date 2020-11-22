	package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Authentication {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private String userID;
	private String password;
	private int authLevel;
	//Placeholder Variable to be linked to Student Object
	private Student student;
	private int regNum;
	
	/*
	 * Constructor for an Account
	 * @param userID = User login
	 * @param password = User's Password
	 * @param authLevel = Authorisation level
	 * @param regNum = Registration Number 
	 */
	public Authentication(String userID, String password, int authLevel, Student student) {
		this.userID = userID;
		this.password = password;
		this.authLevel = authLevel;
		this.student = student;
	}
	
	/*
	 * New Student Constructor
	 * @param student = Instance of a student
	 * return = returns a boolean 
	 */
	public Authentication(Student student) {
		String name = student.getForename();
		String lastName = student.getSurname();
		String registration = Integer.toString(student.getRegistration());
		
		String userID = name.substring(0,1)+lastName.substring(0,1)+registration.substring(registration.length() -4,registration.length());
		System.out.printf("The generated username for the student is: ", userID);
		this.userID = userID;
		
		String passEnd = Integer.toString((int)Math.random()*9001 + 1000);
		String password = name.substring(0,3)+lastName.substring(0,3)+passEnd;
		System.out.printf("The generated password for the student is: ", password);
		this.password = password;
		
		this.authLevel = 1;
		this.student = student;
	}
	
	
	//Get Functions
	public String getUserID() {
		return userID;
	}
	public String getPassword() {
		return password;
	}
	public int getAuthLevel() {
		return authLevel;
	}
	public int getRegNum() {
		return student.getRegistration();
	}
	
	//Set Function
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAuthLevel(int authLevel) {
		this.authLevel = authLevel;
	}
	public void setRegNum(int regNum) {
		this.regNum = regNum;
	}
	
	//Database ----------------------------------------------------------------------------
	
	/*
	 * Inserts a login detail for Authorisation Levels
	 * 
	 * @Return a Boolean function 
	 */
	public boolean addAuthentication () {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("INSERT INTO Authentication VALUE ('" + 
											this.getUserID() + "','" +
											this.getPassword() + "','" +
											this.getAuthLevel() + "','" +
											this.getRegNum() + "');"
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
	 * Removes a login detail for Authorisation Levels
	 * 
	 * @Return a Boolean function 
	 */
	public boolean removeAuthentication() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("DELETE FROM Authentication WHERE " + 
											"userID = '" + this.getUserID() + "';"
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
		//test
		//Student George = new Student(321241, "Mr", "Ashcroft", "George","george@fake.com");
		//System.out.println(George.addStudent());
		
		//Authentication initialStudent = new Authentication(George);
		//Authentication test = new Authentication(123311, "Hello?", 3, George);
		
		//System.out.println(George.removeStudent());
		//System.out.println(test.addAuthentication());
		//System.out.println(test.removeAuthentication());
		
		//System.out.println(initialStudent.addAuthentication());
		//System.out.println(initialStudent.removeAuthentication());
	}
}
