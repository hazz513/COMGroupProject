/*
* COM2008 project
* 
* last modified: 22/11/20
* 
* @author Harry Wang
*/
package businesslogic;

import dataaccess.Approval;
import dataaccess.Module;
import dataaccess.Degree;
import dataaccess.Authentication;
import dataaccess.Department;
import dataaccess.Partner;
import dataaccess.Performance;
import dataaccess.Student;
import dataaccess.StudyPeriod;

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
	
	private static boolean removeModule(Module module) {
		return module.removeFromDB();
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
	
	private static boolean removeApproval(Approval approval) {
		return approval.removeFromDB();
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
	
	private static String changePassword (Authentication user, String newPass) {
		String currentpass = user.getPassword();
		if (newPass != currentpass) {
			user.setPassword(newPass);
			user.updatePassToDB();
			return "Succesful password change";
		}
		return "New password is the same, therefore no change has been made";
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		Authentication test = new Authentication(1, "anthony", 3, 1234567);
		System.out.println(changePassword(test,"anthony"));
	}
}
