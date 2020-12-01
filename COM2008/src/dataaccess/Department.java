package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class Department {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private String depCode;
	private String depName;
	
	/*
	 * Constructor 
	 * @param depCode = Department Code
	 * @param depName = Department Name
	 */
	public Department(String depCode, String depName) {
		this.depCode = depCode;
		this.depName = depName;
	}
	
	//Get functions
	public String getDepName() {
		return depName;
	}
	public String getDepCode() {
		return depCode;
	}
	
	//Set functions
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	//To String Function
	public String toString() {
		return (this.depCode + ". " + this.depName);
	}
	
	//Database ----------------------------------------------------------------------------
	
	/* 
	 * Inserts a Department into the Database
	 * 
	 * @return a boolean based on success
	 */
	public boolean addDepartment () {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("INSERT INTO Department VALUE ('" + 
											this.getDepCode() + "','" +
											this.getDepName() + "');"
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
	 * Removes a Department from the Database
	 * 
	 * @return a boolean based on success
	 */
	public boolean removeDepartment() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("DELETE FROM Department WHERE " +
										   "depCode = '" + this.getDepCode() + "';"
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
	
	public static ArrayList<Department> getAllFromDB() {
		ArrayList<Department> departments = new ArrayList<Department>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Department;");
			
			// build list of students
			while(rs.next()) {
				Department department = new Department(rs.getString("depCode"), rs.getString("depName"));
				departments.add(department);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of students
		return departments;
	}
	
	public static void main(String[] args) {
		//test Method
		Department ACSE = new Department("ACS", "Automated Computer Systems");
		System.out.println(ACSE.addDepartment());
		//System.out.println(ACSE.removeDepartment());
	}
}
