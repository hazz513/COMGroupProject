/*
* COM2008 project
* 
* last modified: 17/11/20
* 
* @author Abdullah Attique Ahmed
*/
package main;

public class Approval {
	
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

}
