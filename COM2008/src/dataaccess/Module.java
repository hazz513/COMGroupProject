/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package dataaccess;
import java.sql.*;
import java.util.*;

public class Module {
	
	// database information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private String code;
	private String name;
	
	/*
	 * Constructor
	 * 
	 * @param code - module code
	 * @param name - module name
	 */
	public Module(String code, String name) {
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
	
	// set methods
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//To String Function
	public String toString() {
		return (this.code + ", " + this.name);
	}
	
	// database ---------------------------------------------------------------------------
	
	/*
	 * inserts module into database
	 * 
	 * @return boolean based on success
	 */
	public boolean addToDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// insert module
			int count = stmt.executeUpdate("INSERT INTO Module VALUES ('" + 
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
	
	/*
	 * remove module from database
	 * 
	 * @return boolean based on success
	 */
	public boolean removeFromDB() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			// delete module
			int count = stmt.executeUpdate("DELETE FROM Module WHERE " + 
										   "modCode = '" + this.getCode() + "';"
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
	 * get all module in a degree according to the level
	 * 
	 * @param approval
	 * 
	 * @return an arrayList of all the module in a degree level wise
	 */
	public static ArrayList<String> allModInDegree(Approval approval) {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();


			//get all the module in a given degree
			ArrayList<String> result = new ArrayList<String>()  ;
			String degCode = approval.getDegree().getCode();
			//String modCode = approval.getModule().getCode();

			//String degCode1 =  degCode.substring(0,degCode.length()-3);

			ResultSet rs =  stmt.executeQuery("SELECT Module.modCode, Module.modName FROM Module INNER JOIN "
											  +" Approval WHERE Module.modCode = Approval.modCode AND "
											  + "Approval.degCode ='"+ degCode + "'");

			//storing all the result in an arrayList
			while(rs.next()) {
				result.add(rs.getString("modCode")+" "+rs.getString("modName"));
			}

			return result ;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			ArrayList<String> str = new ArrayList<String>();
			str.add ("Nothing to show,error occured");
			return str ;
		}
	}
	/*
	 * Retrieve all Modules from the DB
	 * @return a list of all the Modules
	 */
	public static ArrayList<Module> getAllFromDB() {
		ArrayList<Module> modules = new ArrayList<Module>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Module;");
			
			// build list of students
			while(rs.next()) {
				Module module = new Module(rs.getString("modCode"), rs.getString("modName"));
				modules.add(module);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of departments
		return modules;
	}
	
	/*
	 * generate unique module code
	 * 
	 * @param depCode - three letter department code
	 * 
	 * @return module code string
	 */
	public static String generateModuleCode(String depCode) {
		// get list of modules with same department code
		ArrayList<Integer> currentCodes = new ArrayList<Integer>()  ;
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			//get all the module with same department code
			ResultSet rs =  stmt.executeQuery("SELECT modCode FROM Module WHERE modCode LIKE '" 
			                                    + depCode + "%';");
			
			//storing all the unique digits from the result in an arrayList
			while(rs.next()) {
				currentCodes.add(Integer.parseInt(rs.getString("modCode").substring(3)));
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
		for(int i = 4-newNumFormatted.length(); i > 0; i--) {
			newNumFormatted = "0" + newNumFormatted;
		}
		// return complete module code
		return (depCode + newNumFormatted);
		
	}
	
	/*
	 * get a module from database
	 * 
	 * @param modCode - module code of a module
	 * 
	 * @return module matching code
	 */
	public static Module retrieveFromDB(String modCode) {
		ArrayList<Module> modules = new ArrayList<Module>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the modules matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Module WHERE " + 
					 						  "modCode = '" + modCode +"' ;");
			
			// build list of modules
			while(rs.next()) {
				Module module = new Module(rs.getString("modCode"), rs.getString("modName"));
				modules.add(module);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return first(and only) from list
		return modules.get(0);
	}
	
	public static void main(String[] args) {
		//test
		//Module fp = new Module("COM2108", "Functional Programming");
		//System.out.println(fp.addToDB());
		//System.out.println(fp.removeFromDB());
		
		// test to check if the allModInAdegree method is working (will delete this later)
		/*Degree abc = new Degree("COMP00","BSc Computer Science","Computer");
		Module pqr = new Module("COM1008","Functional");
		Approval xyz = new Approval (abc,pqr,2,120,'1');
		ArrayList<String> rest = new ArrayList<String>()  ;
		rest = allModInDegree(xyz);
		for (String x:rest) {
			System.out.println(x);
		}*/
		
		//test
		//System.out.println(generateModuleCode("COM"));
		System.out.println(retrieveFromDB("COM0000").getName());
	}

}
