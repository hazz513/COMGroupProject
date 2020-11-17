/*
* COM2008 project
* 
* last modified: 17/11/20
* 
* @author Abdullah Attique Ahmed
*/
package main;
import java.sql.*;

public class Admin {
	
	// database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	
	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	private static boolean addModule (Module module) {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert module
			int count = stmt.executeUpdate("INSERT INTO Module VALUES ('" + 
											module.getCode() + "', '" +
											module.getName() + "');"
											);
			// check that changes were made
			System.out.println("changes made: " + count);
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
	 * inserts approvals into database
	 * 
	 * @param approvals - the array of approval objects to be inserted
	 * @return boolean based on success
	 */
	private static boolean addApprovals (Approval[] approvals) {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert approvals
			int count = 0;
			for (Approval approval: approvals) {
				count += stmt.executeUpdate("INSERT INTO Approval VALUES ('" + 
						approval.getDegree().getCode() + "', '" +
						approval.getModule().getCode() + "', '" +
						approval.getCore() + "', '" +
						approval.getCredits() + "', '" +
						approval.getLevel() + "');"
						);
			}
			// check that changes were made
			System.out.println("changes made: " + count);
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
	
	public static void main(String[] args) {
		
	}
}
