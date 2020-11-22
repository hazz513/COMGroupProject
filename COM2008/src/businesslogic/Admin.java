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
import dataaccess.Module;
import dataaccess.Department;

public class Admin {
	
	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	private static boolean addModule (Module module) {
		return module.addToDB();
	}
	
	
	
	/*
	 * inserts approvals into database
	 * 
	 * @param approvals - the array of approval objects to be inserted
	 * @return boolean based on success
	 */
	private static boolean addApprovals (Approval[] approvals) {
		int count = 0;
		// insert each approval
		for (Approval approval: approvals) {
			if (approval.addToDB()) {
				count++;
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * inserts module into database
	 * 
	 * @param module - the module object to be inserted
	 * @return boolean based on success
	 */
	private static boolean addDepartment(Department department) {
		return department.addDepartment();
	}
	
	/*
	 * Inserts and creates a new login account
	 * 
	 * @param authentiaction - Instance of an account to be inserted
	 * @return boolean based on success
	 */
	private static boolean addAccounts(Authentication authentication) {
		return authentication.addAuthentication();
	}
	
	/*
	 * deletes module from database
	 * 
	 * @param module - the module object to be deleted
	 * @return boolean based on success
	 */
	private static boolean removeModule(Module module) {
		return module.removeFromDB();
	}
	
	/*
	 * deletes approval from database
	 * 
	 * @param approval - the approval object to be deleted
	 * @return boolean based on success
	 */
	private static boolean removeApproval(Approval approval) {
		return approval.removeFromDB();
	}
	
	/*
	 * Removes a department from the database
	 * 
	 * @param department - the department to remove
	 * @return boolean based on success
	 */
	private static boolean removeDepartment(Department department) {
		return department.removeDepartment();
	}
	
	/*
	 * Removes an account from the databse
	 * 
	 *  @param authentication - the user account to remove
	 *  @return boolean based on success
	 */
	private static boolean removeAccounts(Authentication authentication) {
		return authentication.removeAuthentication();
	}
	
	
	
	
	public static void main(String[] args) {
		
	}
}
