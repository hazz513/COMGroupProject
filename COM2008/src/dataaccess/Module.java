/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
* @author Aryan
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
	 * get all the module name within a degree
	 * 
	 * @return an array 
	 */
	public static ArrayList<String> allModInDegree(Degree degree) {	
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			
			//get all the module in a given degree
			ArrayList<String> result = new ArrayList<String>()  ;
			String degName = degree.getCode();
			degName =  degName.substring(0,degName.length()-3);
						
			ResultSet rs =  stmt.executeQuery("SELECT modName FROM Module WHERE modCode LIKE '" 
			                                    + degName+ "%'");
			
			//storing all the result in an arrayList
			while(rs.next()) {
				result.add(rs.getString("modName"));
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
	
	public static void main(String[] args) {
		// test to check if the allModInAdegree method is working (will delete this later)
		Degree abc = new Degree("COMU00","BSc Computer Science","Computer");
		ArrayList<String> rest = new ArrayList<String>()  ;
		rest = allModInDegree(abc);
		for (String x:rest) {
			System.out.println(x);
		}
	}
}
