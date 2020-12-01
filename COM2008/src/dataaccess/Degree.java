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

public class Degree {
	
	// database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private String code;
	private String name;
	private String leadDep;
	
	
	/*
	 * Constructor
	 * 
	 * @param code - degree code
	 * @param name - degree name
	 * @param leadDep - lead department code
	 */
	public Degree(String code, String name, String leadDep) {
		this.code = code;
		this.name = name;
		this.leadDep = leadDep;
	}
	
	// get methods
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getLeadDep() {
		return leadDep;
	}
	//set methods
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLeadDep(String leadDep) {
		this.leadDep = leadDep;
	}
	
	public String toString() {
		return (this.code + ". " + this.name + ". " + this.leadDep);
	}
	
	//-----------------------------------------------------------------------------------------------------
	//Database Begin
	/*
	 * generate unique degree code
	 * 
	 * @param depCode - three letter department code
	 * @param degreeLevel - single letter, U for undergrad, P for postgrad
	 * 
	 * @return module code string
	 */
	public static String generateDegreeCode(String depCode, String degreeLevel) {
		// get list of modules with same department code
		ArrayList<Integer> currentCodes = new ArrayList<Integer>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			//get all the module with same department code
			ResultSet rs =  stmt.executeQuery("SELECT degCode FROM Degree WHERE degCode LIKE '" 
			                                    + depCode + degreeLevel + "%';");
			
			//storing all the unique digits from the result in an arrayList
			while(rs.next()) {
				currentCodes.add(Integer.parseInt(rs.getString("degCode").substring(4)));
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
			//ArrayList<String> str = new ArrayList<String>();
			//str.add ("Nothing to show,error occured");
			//return ("COM11111");
		}
	
		// generate list of numbers new one should be unique against
		System.out.print(currentCodes);
		
		// generate unique number
		int newNum = 0;
		while (currentCodes.contains(newNum)) {
			newNum ++;
		}
		
		//convert to correct number of digits
		String newNumFormatted = Integer.toString(newNum);
		for(int i = 2-newNumFormatted.length(); i > 0; i--) {
			newNumFormatted = "0" + newNumFormatted;
		}
		// return complete module code
		return (depCode + degreeLevel + newNumFormatted);
		
	}
	
	/*
	 * get approvals for all core modules to the degree at specified level
	 * 
	 * @param level - the level the cores will be returned
	 * 
	 * @return ArrayList of approval which are core
	 */
	public ArrayList<Approval> getCores(char level) {
		ArrayList<Approval> cores = new ArrayList<Approval>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the approvals which are core for the degree and level
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Approval WHERE " +
					 						  "degCode LIKE '" + code + "' AND " +
											  "core = 1 AND " + 
					 						  "level = '" + level +"' ;");
			
			// build list of approvals
			while(rs.next()) {
				Approval coreApproval = Approval.retrieveFromDB(rs.getString("degCode"), rs.getString("modCode"));
				cores.add(coreApproval);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return cores;
		
	}
	
	/*
	 * get approvals for all non-core modules to the degree at specified level
	 * 
	 * @param level - the level the cores will be returned
	 * 
	 * @return ArrayList of approval which are non-core
	 */

	public ArrayList<Approval> getNonCores(char level) {
		ArrayList<Approval> nonCores = new ArrayList<Approval>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the approvals which are core for the degree and level
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Approval WHERE " +
					 						  "degCode LIKE '" + code + "' AND " +
											  "core = 0 AND " + 
					 						  "level = '" + level +"' ;");
			// build list of approvals
			while(rs.next()) {
				Approval nonCoreApproval = Approval.retrieveFromDB(rs.getString("degCode"), rs.getString("modCode"));
				nonCores.add(nonCoreApproval);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return nonCores;
	}
	
	/*
	 * check if a degree exists in the database
	 * 
	 * @return true if degree exists, false otherwise
	 */
	public boolean exists() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// select degree
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Degree WHERE " +
					 						  "degCode LIKE '" + code + "';");
			
			// if something was selected return true
			while(rs.next()) {
				return true;
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean addToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert module
			int count = stmt.executeUpdate("INSERT INTO Degree VALUES ('" + 
											this.getCode() + "', '" +
											this.getName() + "', '" +
											this.getLeadDep() + "');"
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
	
	public boolean removeFromDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert module
			int count = stmt.executeUpdate("DELETE FROM Degree WHERE degCode = ('" + 
											this.getCode() + "');"
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
	
	public static ArrayList<Degree> getAllFromDB() {
		ArrayList<Degree> degrees = new ArrayList<Degree>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Degree;");
			
			// build list of Degrees
			while(rs.next()) {
				Degree degree = new Degree(rs.getString("degCode"), rs.getString("degName"),rs.getString("leadDep"));
				degrees.add(degree);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of degrees
		return degrees;
	}
	
	
	/*
	 * get a degree from database
	 * 
	 * @param degCode - degree code of degree
	 * 
	 * @return degree matching code
	 */
	public static Degree retrieveFromDB(String degCode) {
		ArrayList<Degree> degrees = new ArrayList<Degree>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Degree WHERE " + 
					 						  "degCode = '" + degCode +"' ;");
			
			// build list of degrees
			while(rs.next()) {
				Degree degree = new Degree(rs.getString("degCode"), rs.getString("degName"), rs.getString("leadDep"));
				degrees.add(degree);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return first(and only) degree
		return degrees.get(0);
	}
	
	/*
	 * get all approvals for degree
	 * 
	 * @return ArrayList of approval
	 */
	public ArrayList<Approval> getApprovals() {
		ArrayList<Approval> approvals = new ArrayList<Approval>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the approvals
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Approval WHERE " +
					 						  "degCode = '" + this.code + "';");
			
			// build list of approvals
			while(rs.next()) {
				Approval approval = Approval.retrieveFromDB(rs.getString("degCode"), rs.getString("modCode"));
				approvals.add(approval);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return approvals;
	}
	
	/*
	 * get the levels for degree
	 * 
	 * @return ArrayList of levels
	 */
	public ArrayList<Character> getLevels() {
		ArrayList<Character> levels = new ArrayList<Character>();
		ArrayList<Approval> approvals = this.getApprovals();
		for (Approval approval: approvals) {
			// if level has not already been added then add it
			if (!levels.contains(approval.getLevel())) {
				levels.add(approval.getLevel());
			}
		}
		return levels;
	}
	
	/*
	 * get the highest level for degree
	 * 
	 * @return char representing level
	 */
	public char finalLevel() {
		ArrayList<Character> levels = this.getLevels();
		char highest = '0';
		for (char level: levels) {
			if (level < 'A' && level > highest) {
				highest = level;
			}
		}
		return highest;
	}
	
	public static void main(String[] args) {
		// tests for code generator
		/*System.out.println(generateDegreeCode("APS", "P"));
		System.out.println(generateDegreeCode("APS", "U"));
		System.out.println(generateDegreeCode("COM", "U"));
		System.out.println(generateDegreeCode("COM", "P"));
		System.out.println(generateDegreeCode("NEW", "U"));*/
		// test for getCores
		Degree test = new Degree("COMU00", "placeholder", "COM");
		ArrayList<Approval> cores = test.getCores('1');
		for (Approval approval: cores) {
			System.out.println(approval.getModule().getCode());
		}
		/*Degree test = new Degree("COMU20", "placeholder", "COM");
		Degree test2 = new Degree("COMU00", "placeholder", "COM");
		System.out.println(test.exists());
		System.out.println(test2.exists());*/
		//Degree degree = retrieveFromDB("COMP00");
		//System.out.println(degree.getName());
	}
}

