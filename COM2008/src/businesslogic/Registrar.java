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
	
	/* This gathers all optional modules and returns an ArrayList of Approvals 
	*for input into the addNonCoreModules Function
	*
	*@param Performance this is used to work out what level of degree the need
	*modules for
	*/
	public static ArrayList<Approval> gatherOptModules(Performance performance) {
		Approval approval = performance.getApproval();
		char lvl = approval.getLevel();
		Degree degree = approval.getDegree();
		ArrayList<Approval> nonCoreList = new ArrayList<Approval>(degree.getNonCores(lvl));
		return nonCoreList;
	}
		
	/* Adds modules to a student's performance table
	 * 
	*@param ArrayList<Approval> nonCoreFinaList - a nudge towards the inputted list being the final list
	*-possibly edited from the nonCoreList that is outputted by ArrayList as the student chooses his/her modules
	*
	*@param Performance used to fill parameters for the new performance created.
	*
	*Returns number of completed edits
	*/
	public static int addNonCoreModules(ArrayList<Approval> nonCoreFinalList, Performance performance) {
		int numOfEdit = 0;
		for (int i=0;i<nonCoreFinalList.size();i++){
		StudyPeriod tempStudyPeriod = performance.getStudyPeriod();
		Approval tempApproval = nonCoreFinalList.get(i);
		Performance tempPerf = new Performance(tempStudyPeriod,tempApproval,0,0);
		tempPerf.addPerformance();
		numOfEdit++;
		}
		
		return numOfEdit;
	}
		
	/* This function is used to return an ArrayList of the modules which can then be used to write out
	 * an ArrayList<String> later down the line if desired (to show the user what modules can be selected)
	 * 
	*@param ArrayList<Approval> nonCoreFinaList - a nudge towards the inputted list being the final list
	*-possibly edited from the nonCoreList that is outputted by ArrayList as the student chooses his/her modules
	*
	*@param Performance used to fill parameters for the new performance created.
	*
	*Returns ArrayList of all Non-Core modules available
	*/
	
	
	public static ArrayList<Module> OptModulesDetails(ArrayList<Approval> nonCoreList, Performance performance) {
		ArrayList<Module> modList = new ArrayList<Module>();
		Approval approval = performance.getApproval();
		Degree degree = approval.getDegree();
		for (int i=0;i<nonCoreList.size();i++){
			modList.add(nonCoreList.get(i).getModule());
		}
		System.out.println("The Non-Core Modules for the Degree " + degree.getCode() + " are:");
		return modList;
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
	
	/*Creates a ArrayList of String to show user
	 * 
	 */
	
	public static ArrayList<String> modStringListing (ArrayList<Module> modList){
		ArrayList<String> modStringList = new ArrayList<String>();
		for (int i=0; i<modList.size();i++){
			String modCode = modList.get(i).getCode();
			System.out.println(modCode);
			modStringList.add(modCode);
		}
		
		return modStringList;
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
			totalCredit = 180;
		
		
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
			/*
			//all opt module in the given degree
			ArrayList<Approval> optApproval = degree.getNonCores(actualCoreMod.get(0).getLevel());
			ArrayList<String> optModules = new ArrayList<String>();
			for (Approval i: optApproval) {
				optModules.add(i.getModule().getCode());
			}
			
			
			//opt module chosen by student
			ArrayList<Performance> performances =  studyPeriod.getPerformances();
			ArrayList<String> optModulesChosen = new ArrayList<String>();
			 ArrayList<String> optModulesChosenCopy = optModulesChosen;
			
			 for(Performance i: performances) {
            	 if(i.getApproval().getCore()==0) {
            		 optModulesChosen.add(i.getApproval().getModule().getCode());
            		 optModulesChosenCopy.add(i.getApproval().getModule().getCode());
            	 }
            	 else {
            		 System.out.println("CoreMod removed");
            	 }
             }
			 System.out.println(optModulesChosen);
			 System.out.println(optModules);
			
			 if(optModulesChosen.retainAll(optModules)) {
				 return true;
			 }
			 else {
				 return false;
			 }
			 */
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
		//StudyPeriod s = StudyPeriod.retrieveFromDB('A',9876543);
		//System.out.println(checkStudentReg(s));
		
		Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com", "Cool Tutor Person");
		StudyPeriod Test = new StudyPeriod('A', "2020-11-19","2021-11-19", George);
		Module fp = new Module("COM2108", "Functional Programming");
		Degree se = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");			
		Approval fpse = new Approval(se, fp, 1, 10, '2');
		Performance please = new Performance(Test, fpse, 45, 90);
		ArrayList<String> listOfNonCoreModules = modStringListing(OptModulesDetails(gatherOptModules(please),please));
		int changes = addNonCoreModules(gatherOptModules(please),please);
		System.out.println(changes + " edits have been made.");
		
	}

}
