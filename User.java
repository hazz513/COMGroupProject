package mycomgpp;

import java.sql.*;


public class User {
	
	private String pass;
	private String username;
	
	//database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	/*
	 * constructor
	 * @param password - password
	 */
	public User(String pass, String username) {
		this.pass = pass;
		this.username = username;
	}
	
	//get methods
	public String getPass() {
		return pass;
	}
	public String getUser() {
		return username;
	}
	
	//set methods
	public void setPass(String pass) {
		this.pass = pass;
	}
	// database-----------------------------------------------------------------------------------
	
	public boolean addtoDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("INSERT INTO password VALUES ('" +
					this.getPass() + "');"
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
}
