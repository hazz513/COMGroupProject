/*
* COM2008 project
* 
* last modified: 20/11/20
* 
* @author Abdullah Attique Ahmed
*/
package businesslogic;

import java.util.ArrayList;

import dataaccess.*;
import dataaccess.Module;

public class Registrar {
	
	/*
	 * register existing student with core modules for a certain degree
	 * 
	 * @param student - student to be registered
	 * @param startDate - start date for initial period
	 * @param endDate - end date for initial period
	 * @param degree - the degree for which the student is to be registered
	 * 
	 * @return boolean b
	 */
	public static boolean registerStudent(Student student, String startDate, String endDate, Degree degree) {
		// add initial period
		StudyPeriod initial = new StudyPeriod('A', startDate, endDate, student);
		if (!initial.addStudyPeriod()) {
			return false;
		}
		// get all core modules and add them
		ArrayList<Approval> cores = degree.getCores('1');
		for (Approval approval: cores) {
			Performance performance = new Performance(initial, approval, 0, 0);
			if (!performance.addPerformance()) {
				return false;
			}
		}
		
		return true;
	}
	
	// useless atm
	// add optional modules 
	public static boolean addOptModules() {
		return true;
	}
	
	// useless atm
	// return number of credits yet to be filled for a study period
	public static int creditsLeft() {
		return 0;
	}
	
	// useless atm
	// check that student has correct number of credits left (0), and has all core modules
	// and all modules are approved
	public static boolean registrationIsValid() {
		return true;
	}
	
	public static void main(String[] args) {
		//test
		Student test = new Student(1234567, "dr", "Robert", "Bob", "B.Rob@whatever.com");
		Degree testDeg = new Degree("COMU00", "placeholder", "COM");
		System.out.println(registerStudent(test, "2020-11-20", "2021-11-21", testDeg));
		
	}

}
