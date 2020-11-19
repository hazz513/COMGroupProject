package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Partner {
	
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	
	private Degree degree;
	private Department department;
	
	/*
	 * Partner 
	 * @param degree = Degree Object
	 * @param department = Department Object
	 */
	public Partner(Degree degree, Department department) {
		this.degree = degree;
		this.department = department;
	}
	
	//Get Functions
	public Degree getDegree() {
		return degree;
	}
	public Department getDepartment() {
		return department;
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
											this.getDegree().getCode() + "', '" +
											this.getDepartment().getDepCode() + "');"
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
										   "depCode = '" + this.department.getDepCode() + "' AND "+ 
										   "degCode = '" + this.degree.getCode() + "';"
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
