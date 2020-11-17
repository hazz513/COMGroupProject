/*
* COM2008 project
* 
* last modified: 17/11/20
* 
* @author Abdullah Attique Ahmed
*/
package main;

public class Degree {
	
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
}

