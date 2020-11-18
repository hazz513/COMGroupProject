/*
* COM2008 project
* 
* last modified: 18/11/20
* 
* @author Abdullah Attique Ahmed
*/
package businesslogic;

import dataaccess.Approval;
import dataaccess.Module;

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
	
	
	public static void main(String[] args) {
		
	}
}
