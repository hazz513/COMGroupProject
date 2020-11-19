package mycomgpp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Authentication {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private int userID;
	private String password;
	private int authLevel;
	//Placeholder Variable to be linked to Student Object
	private int regNum;
	
	/*
	 * Constructor for an Account
	 * @param userID = User login
	 * @param password = User's Password
	 * @param authLevel = Authorisation level
	 * @param regNum = Registration Number 
	 */
	public Authentication(int userID, String password, int authLevel, int regNum) {
		this.userID = userID;
		this.password = password;
		this.authLevel = authLevel;
		this.regNum = regNum;
	}
	
	//Get Functions
	public int getUserID() {
		return userID;
	}
	public String getPassword() {
		return password;
	}
	public int getAuthLevel() {
		return authLevel;
	}
	public int getRegNum() {
		return regNum;
	}
	
	//Set Function
	public void setUserID(int userID) {
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
											"userID = '" + this.getUserID() + "');"
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
	public boolean updatePassToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("UPDATE password SET = ('" + this.getPassword() + 
					"WHERE userID = '" + this.getUserID() + "';"
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
	public static void main(String[] args) {
		//test
		//Authentication test = new Authentication(123311, "Hello?", 3, 321241);
		//System.out.println(test.addAuthentication());
		//System.out.println(test.removeAuthentication());
	}
}
