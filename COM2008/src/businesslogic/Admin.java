/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package businesslogic;

import dataaccess.Approval;
import dataaccess.Authentication;
import dataaccess.Degree;
import dataaccess.Module;
import dataaccess.Department;
import dataaccess.Partner;

public class Admin {
	
	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	public static boolean addModule (Module[] modules) {
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
	public static boolean addApprovals (Approval[] approvals) {
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
	
	/* inserts degree courses into database
	 * 
	 * @param degree - the degrees to be inserted
	 * @return boolean based on success
	 */
	
	public static boolean addDegree (Degree degree) {
		
		return degree.addToDB();
	}
	

	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	public static boolean addDepartment(Department department) {
		return department.addDepartment();
	}
	
	/*
	 * Inserts and creates a new login account
	 * 
	 * @param authentiaction - Instance of an account to be inserted
	 * @return boolean based on success
	 */
	public static boolean addAccounts(Authentication authentication) {
		return authentication.addAuthentication();
	}
	
	/*
	 * Inserts and creates a new login account with a Registration number
	 * 
	 * @param authentiaction - Instance of an account to be inserted
	 * @return boolean based on success
	 */
	public static boolean addAccountsNoReg(Authentication authentication) {
		return authentication.addAuthenticationNoReg();
	}
	
	/*
	 * Inserts a partner into the Database
	 * 
	 * @param partner- the partner to form
	 * @return boolean based on success
	 */
	public static boolean addPartner (Partner partner) {
		return partner.addPartner();
	}
	
	/*
	 * deletes module from database
	 * 
	 * @param module - the module object to be deleted
	 * @return boolean based on success
	 */
	public static boolean removeModule(Module module) {
		return module.removeFromDB();
	}
	
	/*
	 * deletes approval from database
	 * 
	 * @param approval - the approval object to be deleted
	 * @return boolean based on success
	 */
	public static boolean removeApproval(Approval approval) {
		return approval.removeFromDB();
	}
	
	/*
	 * Removes a department from the database
	 * 
	 * @param department - the department to remove
	 * @return boolean based on success
	 */
	public static boolean removeDepartment(Department department) {
		return department.removeDepartment();
	}
	
	/*
	 * Removes an account from the database
	 * 
	 *  @param authentication - the user account to remove
	 *  @return boolean based on success
	 */
	public static boolean removeAccounts(Authentication authentication) {
		return authentication.removeAuthentication();
	}
	
	/*
	 * Removes a degree from the database
	 * 
	 *  @param authentication - the degree to remove
	 *  @return boolean based on success
	 */
	
	public static boolean removeDegree (Degree degree) {
		return degree.removeFromDB();
	}
	
	/*
	 * Removes a link from the database
	 * 
	 * @param Partner - the partner to remove
	 * @return boolean based on success
	 */
	public static boolean removePartner (Partner partner) {
		return partner.removePartner();
	}
	
	
	/*
	 * Changes a password from the database
	 * 
	 *  @param user - the person who's password is to be changed
	 *  @param newPass - the new password
	 *  @param currentpass - the current password
	 *  @return string based on success or failure
	 */
	
	
	
	public static String changePassword (Authentication user, String newPass) {
		String currentpass = user.getPassword();
		if (newPass != currentpass) {
			user.setPassword(newPass);
			user.updatePassToDB();
			return "Succesful password change";
		}
		return "New password is the same, therefore no change has been made";
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		//Authentication test = new Authentication(1, "anthony", 3, 1234567);
		//System.out.println(changePassword(test,"anthony"));
	}
}
