package businesslogic;

import java.util.ArrayList;

import dataaccess.*;

public class Teacher {
	
	private static final int DEFAULT_PASS = 40;
	private static final int LEVEL_4_PASS = 50;
	
	enum Progression {
		FAIL,
		RESIT,
		REPEAT,
		PROGRESS,
		GRADUATE_BACHELOR,
		GRADUATE_MASTER,
		GRADUATE_PGDIP,
		GRADUATE_PGCERT,	
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
		if (passedEachModule(performances) && passedMean(period)) {
			// graduate if final year
			if (isFinalYear(period)) {
				if (level == '4') {
					return Progression.GRADUATE_MASTER;
				}
				else {
					return Progression.GRADUATE_BACHELOR;
				}
			}
			// progress otherwise
			else {
				return Progression.PROGRESS;
			}
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
		
		if (acquiredCredits >= 120 && passedMean(performances.get(0).getStudyPeriod())) {
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
	public static boolean passedMean(StudyPeriod period) {
		int mean = meanGrade(period);
		// use appropriate level threshold
		if (period.getLevel() == '4') {
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
	public static int meanGrade(StudyPeriod period) {
		// get all performances
		ArrayList<Performance> performances = period.getPerformances();
		// get grades for all performances
		int weightedSum = 0;
		int weightedDivisor = 0;
		// for each performance use highest grade
		for (int i = 0; i < performances.size(); i++) {
			int credits = performances.get(i).getApproval().getCredits();
			weightedSum += getCountedGrade(performances.get(i)) * credits;
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
	
	
	public static void main(String[] args) {
		//System.out.println(meanGrade(StudyPeriod.retrieveFromDB('A', 1234567)));
		// test grading
		StudyPeriod period = StudyPeriod.retrieveFromDB('C', 1234567);
		System.out.println(progression(period));
		//Degree degree = Degree.retrieveFromDB("COMU00");
		//System.out.println(degree.finalLevel());
	}

}
