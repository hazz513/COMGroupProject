	package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
	public Authentication(String userID, String password, int authLevel, int regNum) {
		this.userID = userID;
		this.password = password;
		this.authLevel = authLevel;
		this.regNum = regNum;
	}
	public Authentication(String userID, String password) {
		this.userID = userID;
		this.password = password;
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
		System.out.println("The generated username for the student is: " + userID);
		this.userID = userID;
		int max = 9999;
		int min = 1000;
		
		int random_int = (int)(Math.random() * (max - min + 1) + min);
		String passEnd = Integer.toString(random_int);
		//Fix Randomizer
		String password = name.substring(0,3)+lastName.substring(0,3)+passEnd;
		System.out.println("The generated password for the student is: " + password);
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
	
	public boolean updatePassToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("UPDATE Authentication SET password= '" + this.getPassword() + 
					"'WHERE userID= '" + this.getUserID() + "';"
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
	
	public int checkPassword(String userID, String password) {
		ArrayList<Authentication> accounts = new ArrayList<Authentication>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Authentication WHERE " + 
					 						  "userID = '" + userID + "' AND " +
											  "password = '" + password + "';");
			// build list of degrees
			while(rs.next()) {
				if (rs.getInt("regNum") != 0) {
					Authentication account = new Authentication(rs.getString("userID"), rs.getString("password"), rs.getInt("authLevel"), Student.retrieveFromDB(rs.getInt("regNum")));
					accounts.add(account);
				}
				else {
					Authentication account = new Authentication(rs.getString("userID"), rs.getString("password"), rs.getInt("authLevel"), 0000000);
					accounts.add(account);
				}
				
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (accounts.size() == 0) {
			System.out.println("Incorrect UserID or Password");
			return 0;
		}
		else {
			System.out.println("Match found");
			return accounts.get(0).getAuthLevel();
		}
	}
	
	/*
	 * Testing functions. 
	 * Invalid and won't work until the Table Student is populated
	 * FK is needed
	 */
	public static void main(String[] args) {
		//test
		Student George = new Student(321242, "Mr", "pass", "rand","fake@email.com");
		//System.out.println(George.addStudent());
		//System.out.println(George.removeStudent());
		
		//Authentication initialStudent = new Authentication(George);
		//Authentication test = new Authentication(123311, "Hello?", 3, George);
		
		//int test = checkPassword("rp1241", "ranpas1000");
		//System.out.println(test);
		//System.out.println(test.addAuthentication());
		//System.out.println(test.removeAuthentication());
		
		//System.out.println(initialStudent.addAuthentication());
		//System.out.println(initialStudent.removeAuthentication());
	}
}
