/*
* COM2008 project
* 
* last modified: 17/11/20
* 
* @author Abdullah Attique Ahmed
*/
package main;

public class Module {
	
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

}
