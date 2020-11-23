package businesslogic;

import java.util.ArrayList;

import dataaccess.*;

public class Teacher {
	
	private static final int DEFAULT_PASS = 40;
	private static final int LEVEL_4_PASS = 50;
	
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
			weightedSum += getGrade(performances.get(i)) * credits;
			weightedDivisor += credits;
		}
		
		// return mean
		return weightedSum/weightedDivisor;
	}
	
	public static int getGrade(Performance performance) {
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
		System.out.println(meanGrade(StudyPeriod.retrieveFromDB('A', 1234567)));
	}

}
