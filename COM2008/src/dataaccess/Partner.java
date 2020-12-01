package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Partner {
	
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	
	private Degree degree;
	private Department department;
	private String deg;
	private String dep;
	/*
	 * Partner 
	 * @param degree = Degree Object
	 * @param department = Department Object
	 */
	public Partner(Degree degree, Department department) {
		this.degree = degree;
		this.department = department;
		this.deg = degree.getCode();
		this.dep = department.getDepCode();
	}
	public Partner(String degree, String department) {
		this.deg = degree;
		this.dep = department;
	}
	
	//Get Functions
	public Degree getDegree() {
		return degree;
	}
	public Department getDepartment() {
		return department;
	}
	
	public String toString() {
		return (this.deg + ". " + this.dep);
	}
	
	//Database ----------------------------------------------------------------------------
	
	/* 
	 * Inserts a Partner linker into the Database
	 * 
	 * @return a boolean based on success
	 */
	
	public boolean addPartner () {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			System.out.println(this.getDegree().getCode());
			System.out.println(this.getDepartment().getDepCode());
			int count = stmt.executeUpdate("INSERT INTO Partner VALUES ('" + 
											deg + "', '" +
											dep + "');"
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
	 * Removes a partner linker from the Database
	 * 
	 * @return a boolean based on success
	 */
	public boolean removePartner() {
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate("DELETE FROM Partner WHERE " +
										   "depCode = '" + dep + "' AND "+ 
										   "degCode = '" + deg + "';"
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
	
	public static ArrayList<Partner> getAllFromDB() {
		ArrayList<Partner> partners = new ArrayList<Partner>();
		
		try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)) {
			Statement stmt = con.createStatement();
			
			// get all the degrees matching code
			ResultSet rs =  stmt.executeQuery("SELECT * FROM Partner;");
			
			// build list of students
			while(rs.next()) {
				Partner partner = new Partner(rs.getString("degCode"), rs.getString("depCode"));
				partners.add(partner);
			}
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		// return List of departments
		return partners;
	}
	
	/*
	 * Testing function: Proven to work
	 */
	public static void main(String[] args) {
		//test
		Degree se = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");
		Department ACSE = new Department("ACS", "Automated Computer Systems");
		Partner fpse = new Partner(se,ACSE);
		System.out.println(fpse.addPartner());
		//System.out.println(fpse.removePartner());
	}
}
