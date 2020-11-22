/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Approval {
	
	// database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private Module module;
	private Degree degree;
	private int core;
	private int credits;
	private char level;
	
	/*
	 * Constructor
	 * 
	 * @param degree - degree object
	 * @param module - module object
	 * @param core - integer representing boolean
	 * @param credits - credits for module for certain degree
	 * @param level - level of module for certain degreed
	 */
	public Approval(Degree degree, Module module, int core, int credits, char level) {
		this.module = module;
		this.degree = degree;
		this.core = core;
		this.credits = credits;
		this.level = level;
	}
	
	// get methods
	public Module getModule() {
		return module;
	}
	public Degree getDegree() {
		return degree;
	}
	public int getCore() {
		return core;
	}
	public int getCredits() {
		return credits;
	}
	public char getLevel() {
		return level;
	}
	
	// database ---------------------------------------------------------------------------
	
	/*
	 * inserts approval into database
	 * 
	 * @return boolean based on success
	 */
	public boolean addToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert approval
			int count = stmt.executeUpdate("INSERT INTO Approval VALUES ('" + 
											this.getDegree().getCode() + "', '" +
											this.getModule().getCode() + "', '" +
											this.getCore() + "', '" +
											this.getCredits() + "', '" +
											this.getLevel() + "');"
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
	
	/*
	 * remove module from database
	 * 
	 * @return boolean based on success
	 */
	public boolean removeFromDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// delete approval
			int count = stmt.executeUpdate("DELETE FROM Approval WHERE " + 
										   "degCode = '" + this.degree.getCode() + "' AND " + 
										   "modCode = '" + this.module.getCode() + "';"
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
	
	public static void main(String[] args) {
		//test
		/*Module fp = new Module("COM2118", "Functional Programming v2");
		Degree se = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");
		Approval fpse = new Approval(se, fp, 1, 10, '2');
		Module fp2 = new Module("COM2119", "N-F Programming");
		Degree se2 = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");
		Approval fpse2 = new Approval(se2,fp2,0, 10,'2');
		//System.out.println(fpse.addToDB());
		//System.out.println(fpse.removeFromDB());
		System.out.println(fpse2.addToDB());
		System.out.prrintln(fpse2.removeFromDB());
		*/
	}

}
