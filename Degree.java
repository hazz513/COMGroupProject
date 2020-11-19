/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package mycomgpp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Degree {
	
	private String code;
	private String name;
	
	// database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	/*
	 * Constructor
	 * 
	 * @param code - degree code
	 * @param name - degree name
	 * @param leadDep - lead department code
	 */
	public Degree(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	// get methods
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	
	//set methods
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// database ------------------------------------------------------------------
	
	public boolean addToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert module
			int count = stmt.executeUpdate("INSERT INTO Degree VALUES ('" + 
											this.getCode() + "', '" +
											this.getName() + "');"
											);
			// check that changes were made
			//System.out.println("changes made: " + count);
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
}

