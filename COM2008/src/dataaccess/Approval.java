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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
	private String modCode;
	private String degCode;
	
	
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
		this.modCode = module.getCode();
		this.degCode = degree.getCode();
	}
	
	public Approval(String degree, String module, int core, int credits, char level) {
		this.modCode = module;
		this.degCode = degree;
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
	public String getModCode() {
		return modCode;
	}
	public String getDegCode() {
		return degCode;
	}
	
	public String toString() {
		return (this.degCode + ", " + this.modCode + ", " + this.core + ", " + this.level);
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
											this.getDegCode() + "', '" +
											this.getModCode() + "', '" +
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
										   "degCode = '" + this.getDegCode() + "' AND " + 
										   "modCode = '" + this.getModCode() + "';"
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
	 * get an approval from database
	 * 
	 * @param degCode - degree code of degree
	 * @param modCode - module code of degree
	 * 
	 * @return approval matching codes
	 */
	public static Approval retrieveFromDB(String degCode, String modCode) {
		ArrayList<Approval> approvals = new ArrayList<Approval>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the approvals mathcing codes
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Approval WHERE " +
					 						  "degCode LIKE '" + degCode + "' AND " +
					 						  "modCode LIKE '" + modCode +"' ;");
			
			// build list of approvals
			while(rs.next()) {
				Module module = Module.retrieveFromDB(rs.getString("modCode"));
				Degree degree = Degree.retrieveFromDB(rs.getString("degCode"));
				
				Approval coreApproval = new Approval(degree, module, rs.getInt("core"), rs.getInt("credits"), rs.getString("level").charAt(0));
				approvals.add(coreApproval);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return first(and only) approval
		return approvals.get(0);
	}
	
	public static ArrayList<Approval> getAllFromDB() {
		ArrayList<Approval> approvals = new ArrayList<Approval>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Approval;");
			
			// build list of students
			while(rs.next()) {
				Approval approval = new Approval(rs.getString("degCode"), rs.getString("modCode"), rs.getInt("core"), rs.getInt("credits"), rs.getString("level").charAt(0));
				approvals.add(approval);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of departments
		return approvals;
	}
	
	
	public static void main(String[] args) {
		//test
		//Module fp = new Module("COM2108", "Functional Programming");
		//Degree se = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");
		//Approval fpse = new Approval(se, fp, 1, 10, '2');
		//System.out.println(fpse.addToDB());
		//System.out.println(fpse.removeFromDB());
		System.out.println(retrieveFromDB("COMU00", "COM0000").getLevel());
	}

}
