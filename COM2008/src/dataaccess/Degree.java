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
		ArrayList<Integer> currentCodes = new ArrayList<Integer>()  ;
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			//get all the module with same department code
			ResultSet rs =  stmt.executeQuery("SELECT degCode FROM Degree WHERE degCode LIKE '" 
			                                    + depCode + degreeLevel + "%'");
			
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
	
	public static void main(String[] args) {
		// tests for code generator
		/*System.out.println(generateDegreeCode("APS", "P"));
		System.out.println(generateDegreeCode("APS", "U"));
		System.out.println(generateDegreeCode("COM", "U"));
		System.out.println(generateDegreeCode("COM", "P"));
		System.out.println(generateDegreeCode("NEW", "U"));*/
	}
}

