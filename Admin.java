/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package mycomgpp;

import mycomgpp.Approval;
import mycomgpp.Module;
import mycomgpp.Degree;
import mycomgpp.Authentication;

public class Admin {
	
	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	private static boolean addModule (Module[] modules) {
		for (Module module: modules) {
			if (module.addToDB()) {				
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * inserts approvals into database
	 * 
	 * @param approvals - the array of approval objects to be inserted
	 * @return boolean based on success
	 */
	private static boolean addApprovals (Approval[] approvals) {
		// insert each approval
		for (Approval approval: approvals) {
			if (approval.addToDB()) {
			}
			else {
				return false;
			}
		}
		return true;
	}
	private static boolean addDegreeCourses (Degree[] degrees) {
		
		for (Degree degree:degrees) {
			if (degree.addToDB()) {
	
			}
			else {
			return false;
		}
		}
		return true;
	}
	private static String addPassword (Authentication user, String newPass) {
		String currentpass = user.getPassword();
		if (newPass != currentpass) {
			user.setPassword(newPass);
			user.updatePassToDB();
			return "Succesful password change";
		}
		return "New password is the same, therefore no change has been made";
		
	}
	private static boolean removeDegreeCourses (Degree[] degrees) {
		for (Degree degree:degrees) {
			if (degree.removeFromDB()) {
	
			}
			else {
			return false;
		}
		}
		return true;
	}
	public static void main(String[] args) {
		
	}
}
