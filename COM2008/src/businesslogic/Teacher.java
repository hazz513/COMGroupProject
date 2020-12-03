package businesslogic;

import java.util.*;

import javax.swing.JOptionPane;

import dataaccess.*;

public class Teacher {
	
	private static final int DEFAULT_PASS = 40;
	private static final int LEVEL_4_PASS = 50;
	
	public enum Progression {
		INVALID,
		FAIL,
		RESIT,
		REPEAT,
		PROGRESS,
		GRADUATE_BACHELOR,
		GRADUATE_MASTER,
		GRADUATE_PGDIP,
		GRADUATE_PGCERT	
	}
	
	public enum DegreeClass {
		INVALID,
		FIRST_CLASS,
		UPPER_SECOND,
		LOWER_SECOND,
		THIRD_CLASS,
		PASS,
		DISTINCTION,
		MERIT,
		FAIL	
	}
	
	/*
	 * get the degree class for a graduating student, returns invalid otherwise
	 * 
	 * @param student - the subjected student
	 * @param progression - the progression type, expects a bachelors or masters
	 * 
	 * @return the degree class obtained
	 */
	public static DegreeClass getDegreeClass(Student student, Progression progression) {
		ArrayList<Double> grades = compileGrades(student);
		if (grades.size() == 4 && progression == Progression.GRADUATE_BACHELOR) {
			grades.remove(3);
		}
		double meanGrade = degreeMeanGrade(grades);
		
		if (grades.size() == 2 || grades.size() > 4 || grades.size() < 1) {
			return DegreeClass.INVALID;
		}
		
		if (progression == Progression.GRADUATE_BACHELOR) {
			if (meanGrade < 39.5) {
				return DegreeClass.FAIL;
			}
			else if (meanGrade < 44.5) {
				return DegreeClass.PASS;
			}
			else if (meanGrade < 49.5) {
				return DegreeClass.THIRD_CLASS;
			}
			else if (meanGrade < 59.5) {
				return DegreeClass.LOWER_SECOND;
			}
			else if (meanGrade < 69.5) {
				return DegreeClass.UPPER_SECOND;
			}
			else {
				return DegreeClass.FIRST_CLASS;
			}
		}
		else if (progression == Progression.GRADUATE_MASTER) {
			if (meanGrade < 49.5) {
				return DegreeClass.FAIL;
			}
			else if (meanGrade < 59.5) {
				if (grades.size() == 1)  {
					return DegreeClass.PASS;
				}
				return DegreeClass.LOWER_SECOND;
			}
			else if (meanGrade < 69.5) {
				if (grades.size() == 1)  {
					return DegreeClass.MERIT;
				}
				return DegreeClass.UPPER_SECOND;
			}
			else {
				if (grades.size() == 1)  {
					return DegreeClass.DISTINCTION;
				}
				return DegreeClass.FIRST_CLASS;
			}
		}
		
		else {
			return DegreeClass.INVALID;
		}
	}
	
	/*
	 * get the weighted mean grade for the entire course for a student
	 * return standard mean unless there are 3 or 4 levels 
	 * 
	 * @param grades - list of grades, 1 per level, ordered by level
	 * 
	 * @return the the mean grade
	 */
	public static double degreeMeanGrade(ArrayList<Double> grades) {
		// get mean grades for each
		double sum = 0;
		switch(grades.size()) {
			case 3:
				// mean for bachelors
				// third year is given double weighting
				sum = grades.get(1) + 2*grades.get(2);
				return sum/3;
			case 4:
				// mean for masters
				// third and fourth year are given double weighting
				sum = grades.get(1) + 2*grades.get(2) +2*grades.get(3);
				return sum/5;
			default:
				// standard mean
				// to be used for 1 year postgrad
				for (double grade: grades) {
					sum += grade;
				}
				return sum/grades.size();	
		}
	}
	
	/*
	 * compile mean grades for each level into ordered list
	 * 
	 * @param student - the student for which grades are needed
	 * 
	 * @return list of grades for each level in order
	 */
	public static ArrayList<Double> compileGrades(Student student) {
		// get all levels excluding year in industry
		ArrayList<StudyPeriod> periods = student.getPeriods();
		periods.sort(new StudyPeriodComparator());
		ArrayList<Double> grades = new ArrayList<Double>();
		ArrayList<Character> levels = new ArrayList<Character>();
		// for each period
		for (int i = 0; i < periods.size(); i++) {
			ArrayList<Performance> performances = periods.get(i).getPerformances();
			char level = performances.get(0).getLevel();
			// remove placement
			if (level == 'P') {
				periods.remove(i);
				i--;
			}
			// add grade(one per level) to list, replace previous entry if needed
			else { 
				double grade = meanGrade(performances);
				if (!levels.contains(level)){
					//System.out.println("adding: " + level);
					levels.add(level);
					grades.add(grade);
				}	
				else {
					//System.out.println("repeating: " + level);
					grades.set(levels.indexOf(level), grade);
				}		
			}
					
		}
		return grades;
	}
	
	// a comparator for studyperiod allowing it to be easily sorted based on label
	public static class StudyPeriodComparator implements Comparator<StudyPeriod> { 
		// compares the label of the period
        @Override
        public int compare(StudyPeriod a, StudyPeriod b) { 
            // for comparison 
            return a.getLabel() - b.getLabel();
        }    
    } 
	
	/*
	 * check if student progresses, fails, graduate etc 
	 * 
	 * @param period - the study period
	 * 
	 * @return the kind of progression for the period
	 */
	public static Progression progression(StudyPeriod period){
		ArrayList<Performance> performances = period.getPerformances();
		Degree degree = performances.get(0).getApproval().getDegree();
		Student student = Student.retrieveFromDB(performances.get(0).getStudyPeriod().getStoredRegistration());
		char level = performances.get(0).getLevel();
		// check for special conditions for postgrad
		if (degree.getCode().charAt(3) == 'P') {
			System.out.println("special rules");
			return postGradRules(performances);
		}
		
		
		// check if average met
		// check if all modules passed and if conceded pass is valid
		if (passedEachModule(performances) && passedMean(performances)) {
			// graduate if final year
			if (isFinalYear(period)) {
				if (level == '4') {
					return Progression.GRADUATE_MASTER;
				}
				else if (level == '3'){
					return Progression.GRADUATE_BACHELOR;
				}
			}
			
			// progress otherwise
			return Progression.PROGRESS;
			
		}
		// if didnt pass
		else {
			boolean canResit = true;
			for (Performance performance: performances) {
				// if there is any failed performance which cannot be resit
				if (passMargin(performance) < 0 && !(performance.canResit())) {
					canResit = false;
					break;
				}
			}
			// if masters can get bachelor then get bachelor
			if (level == '4' && degree.getLevels().size() > 1) {
				return Progression.GRADUATE_BACHELOR;
			}
			// if not masters, allow resit or repeat
			else {
				// if can resit then resit
				if (canResit) {
					return Progression.RESIT;
				}
				// if can repeat then repeat
				if (student.canRepeat(level)) {
					return Progression.REPEAT;
				}
			}
			
		}
		
		return Progression.FAIL;
		
		// give result based on case
	}
	
	/*
	 * check if student progresses, fails, graduate etc for the special case of 1 year postgrad
	 * 
	 * @param performances - the performances for the study period
	 * 
	 * @return the kind of progression for the performances
	 */
	public static Progression postGradRules(ArrayList<Performance> performances) {
		boolean dissertationPassed = true;
		int acquiredCredits = 0;
		boolean conceded = false;
		// sum credits for all modules apart from dissertation
		for (Performance performance: performances) {
			int margin = passMargin(performance);
			// if it is a dissertation only check if it was passed
			if (performance.getApproval().getCredits() == 60) {
				if (margin < 0) {
					dissertationPassed = false;
				}
				performances.remove(performance);
			}
			// allow one failure otherwise withing 10%
			else if (margin >= -10 && margin < 0 && !conceded) {
				acquiredCredits += performance.getApproval().getCredits();
				conceded = true;
			}
			else if (margin < 0) {
			}
			else {
				acquiredCredits += performance.getApproval().getCredits();
			}
		}
		
		if (acquiredCredits >= 120 && passedMean(performances)) {
			if (dissertationPassed) {
				return Progression.GRADUATE_MASTER;
			}
			else {
				return Progression.GRADUATE_PGCERT;
			}
		}
		else if (acquiredCredits >= 60) {
			return Progression.GRADUATE_PGCERT;
		}
		return Progression.FAIL;
		
	}
	
	/*
	 * check if a study period is for the final year of a degree
	 * 
	 * @param period - the study period to be checked
	 * 
	 * @return true if it is the final year, false otherwise
	 */
	public static boolean isFinalYear(StudyPeriod period) {
		// get degree of study period
		Degree degree = period.getPerformances().get(0).getApproval().getDegree();
		// compare level with final level of degree
		return degree.finalLevel() == period.getLevel();
	}
	
	/*
	 * check if a study period has a passing weighted mean grade
	 * 
	 * @param period - the study period
	 * 
	 * @return true passing, false otherwise
	 */
	public static boolean passedMean(ArrayList<Performance> performances) {
		double mean = meanGrade(performances);
		// use appropriate level threshold
		if (performances.get(0).getLevel() == '4') {
			if (mean < LEVEL_4_PASS) {
				return false;
			}
		}
		else if (mean < DEFAULT_PASS) {
			return false;
		}
		return true;
	}
	
	/*
	 * checks if each module was passed, taking conceded pass into account
	 * 
	 * @param performances - the performances for the study period
	 * 
	 * @return true if pass or conceded pass, false otherwise
	 */
	public static boolean passedEachModule(ArrayList<Performance> performances) {
		//int[] margins = modulesPassMargin(performances);
		boolean conceded = false;
		for (Performance performance: performances) {
			// if a single module has a failed margin greater than 10 then fail
			// if only one module was failed within a margin of ten and
			// there were no other failures then pass (conceded will be checked for this)
			// otherwise pass
			int margin = passMargin(performance);
			if (margin >= -10 && margin < 0 && !conceded) {
				conceded = true;
			}
			else if (margin < 0) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * returns an integer describing the amount by which passed or failed
	 * 
	 * @param performance - the performance to check
	 * 
	 * @return integer for the margin
	 */
	public static int passMargin(Performance performance) {
		if (performance.getLevel() == '4') {
			
			return getCountedGrade(performance) - LEVEL_4_PASS;
		}
		// otherwise
		else {
			return getCountedGrade(performance) - DEFAULT_PASS;
		}
	}
	
	/*
	 * get the weighted mean grade for a study period
	 * 
	 * @param period - the study period
	 * 
	 * @return the mean value
	 */
	public static double meanGrade(ArrayList<Performance> performances) {
		// get grades for all performances
		int weightedSum = 0;
		int weightedDivisor = 0;
		// for each performance use highest grade
		for (int i = 0; i < performances.size(); i++) {
			int credits = performances.get(i).getApproval().getCredits();
			weightedSum += (float)getCountedGrade(performances.get(i)) * credits;
			weightedDivisor += credits;
		}
		
		// return mean
		return weightedSum/weightedDivisor;
	}
	
	/*
	 * get the best grade for a performance which will be capped for resits
	 * 
	 * @param period - the performance
	 * 
	 * @return the grade
	 */
	public static int getCountedGrade(Performance performance) {
		int cappedResit = 0;
		// if resit grade exists, consider it when getting final grade
		if (performance.getResitGrade() != 0) {
			// cap grade depending on study level
			if (performance.getLevel() == '4') {
				cappedResit = Math.min(LEVEL_4_PASS, performance.getResitGrade());
			}
			else {
				cappedResit = Math.min(DEFAULT_PASS, performance.getResitGrade());
			}
		}
		// set grade
		return Math.max(performance.getGrade(), cappedResit);
	}
	
	/*
	 * Checks if the student is eligible to be advanced and advances them if necessary
	 * @param student - A student to be checked
	 * 
	 * @return a boolean defendant on result
	 */
	public static boolean progressStudent(StudyPeriod studyPeriod,String startDate, String endDate) {
		Progression progressMethod = progression(studyPeriod);
		char newLabel = studyPeriod.getLabel();
		newLabel+=1;
		ArrayList<Performance> pastPerformances = studyPeriod.getPerformances();
		Degree degree = pastPerformances.get(0).getApproval().getDegree();
		// the levels that the degree offers
		ArrayList<Character> degreeLevels = degree.getLevels();
		// derive the final level of the degree
		char highestLevel = '0';
		for (char level: degreeLevels) {
			if (level < 'A' && level > highestLevel) {
				highestLevel = level;
			}
		}
		
		char level = studyPeriod.getLevel();
		
		
		if (progressMethod == Progression.PROGRESS) {
			// deal with placement years
			if (degree.getName().contains("with a Year in Industry")) {
				System.out.println("dealing with placement student");
				// if the student is progressing to the second last year then create placement year
				if (level == (degreeLevels.size() - 2)) {
					level = 'P';
				}
				// if the student is progressing from the placement year then create last year
				else if (level == 'P') {
					level = highestLevel;
				}
				// increment level
				else {
					level+=1;
				}
			}
			// simply increment otherwise
			else {
				level+=1;
			}
		}
		else if (progressMethod == Progression.REPEAT) {}
		else {
			//Invalid input (I.E Resit is an option)
			System.out.println("Student can do a resit");
			return false;
		}
		
		StudyPeriod newStudyPeriod = new StudyPeriod(newLabel, startDate, endDate, studyPeriod.getStudent());
		System.out.println(newStudyPeriod.addStudyPeriod());
		
		ArrayList<Approval> coreModules = degree.getCores(level);
		
		for (Approval approvals:coreModules) {
			Performance newPerformances = new Performance(newStudyPeriod, approvals, 0,0);
			if (!newPerformances.addPerformance()) {
				return false;
				
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		//System.out.println(meanGrade(StudyPeriod.retrieveFromDB('A', 1234567)));
		// test grading
		
		
		//StudyPeriod period = StudyPeriod.retrieveFromDB('A', 1234567);
		//System.out.println(progression(period));
		
		StudyPeriod periodProgressTest = StudyPeriod.retrieveFromDB('A', 1234567);
		
		System.out.println(progressStudent(periodProgressTest,"0000-00-00","0000-00-01"));
		
		//Degree degree = Degree.retrieveFromDB("COMU00");
		//System.out.println(degree.finalLevel());
		//Student student = Student.retrieveFromDB(1241214);
		//double test = degreeMeanGrade(compileGrades(student));
		//System.out.println(getDegreeClass(student, Progression.GRADUATE_MASTER));
	}

}
