/*
* COM2008 project
* 
* last modified: 20/11/20
* 
* @author Abdullah Attique Ahmed
*/
package businesslogic;

import java.util.*;

import dataaccess.*;

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
	
	/*
	 * check if the sum of all module credit of a student sums to correct total
	 * 
	 * @param StudyPeriod
	 * 
	 * returns a integer, positive if there are less than allowed limit of credit taken,negative if more than and zero when it's correct
	 */
	public static int creditChecker(StudyPeriod studyPeriod) {
		ArrayList<Performance> list = studyPeriod.getPerformances();
		int totalCredit = 100000;
		String degreeType = list.get(0).getApproval().getDegree().getCode();
		
		if (degreeType.charAt(3)=='U')
			totalCredit = 120;
		else
			totalCredit = 160;
		
		
		for(Performance i: list) {
			totalCredit -= i.getApproval().getCredits();	
		}
		
		return totalCredit ;
		
	}
	/*
	 * checks that student registrations are complete and correct
	 * 
	 * @param StudyPeriod
	 * 
	 * returns a boolean, true when everything is correct and false otherwise
	 */
	public static boolean checkStudentReg(StudyPeriod studyPeriod) {
		Degree degree = new Degree(studyPeriod.getPerformances().get(0).getApproval().getDegree().getCode(),"xyz","YYY");
		ArrayList<Approval> actualCoreMod = degree.getCores(studyPeriod.getLevel());
		ArrayList<Approval>  chosenCoreMod = new ArrayList<Approval>();
		ArrayList<Performance> performance = studyPeriod.getPerformances();
		
		for(Performance i: performance) {
			chosenCoreMod.add(i.getApproval());
		}
		
		ArrayList<String> actualModCode = new ArrayList<String>();
		ArrayList<String> chosenModCode = new ArrayList<String>();
		
		//adding the module codes to the both the arrayList
		for (Approval i: actualCoreMod) {
			actualModCode.add(i.getModule().getCode());
		}
		
		for (Approval i: chosenCoreMod) {
			chosenModCode.add(i.getModule().getCode());
		}
		
		//sorting the array right now helps in comparing them
		//Collections.sort(actualModCode);
		//Collections.sort(chosenModCode);
		
		
		if (chosenCoreMod.size()==actualCoreMod.size() && creditChecker(studyPeriod)==0 && 
			actualModCode.containsAll(chosenModCode) && chosenModCode.containsAll(actualModCode) ) {
			return true;
		}	
		else
			return false;
		

		//int c= 0;
		//chosenCoreMod.containsAll(actualCoreMod) && actualCoreMod.containsAll(chosenCoreMod)
		//return c;
	}
	
	public static void main(String[] args) {
		/*//test
		Student test = new Student(1234567, "dr", "Robert", "Bob", "B.Rob@whatever.com");
		Degree testDeg = new Degree("COMU00", "placeholder", "COM");
		System.out.println(registerStudent(test, "2020-11-20", "2021-11-21", testDeg));
		*/
		
		//test for creditChecker or checkStudentReg method
		StudyPeriod s = StudyPeriod.retrieveFromDB('A',9876543);
		System.out.println(checkStudentReg(s));
	}

}
