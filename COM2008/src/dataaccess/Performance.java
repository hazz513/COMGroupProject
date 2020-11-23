package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class Performance {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private StudyPeriod studyPeriod;
	private Approval approval;
	private int grade;
	private Integer resitGrade;
	
	public Performance(StudyPeriod studyPeriod, Approval approval, int grade) {
		this.studyPeriod = studyPeriod;
		this.approval = approval;
		this.grade = grade;
	}
	
	public Performance(StudyPeriod studyPeriod, Approval approval, int grade, Integer resitGrade) {
		this(studyPeriod, approval, grade);
		this.resitGrade = resitGrade;
	}
	
	//get methods (May need to create a set for the student object)
	public StudyPeriod getStudyPeriod() {
		return studyPeriod;
	}
	public Approval getApproval() {
		return approval;
	}
	public int getGrade() {
		return grade;
	}
	public Integer getResitGrade() {
		return resitGrade;
	}
	
	//set methods
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public void setResitGrade(Integer resitGrade) {
		this.resitGrade = resitGrade;
	}
	
	//Database ----------------------------------------------------------------------------
	
		/*
		 * Inserts a performance to be associated with a student
		 * 
		 * @Return a Boolean function 
		 */
		public boolean addPerformance () {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("INSERT INTO Performance VALUE ('" + 
												this.getStudyPeriod().getStoredRegistration() + "','" +
												this.getStudyPeriod().getLabel() + "','" +
												this.getApproval().getModule().getCode() + "','" +
												this.getApproval().getDegree().getCode() + "','" +
												this.getGrade() + "','" +
												this.getResitGrade() + "');"
												);
				System.out.println("Changes made: " + count);
				switch (count) {
					case 0:
						return false;
					default:
						return true;
					
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * Removes a login detail for Authorisation Levels
		 * 
		 * @Return a Boolean function 
		 */
		public boolean removePerformance() {
			try (Connection con = DriverManager.getConnection(DB, DB_USER_NAME, DB_PASSWORD)){
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate("DELETE FROM Performance WHERE " + 
											   "registration = '" + this.studyPeriod.getStoredRegistration() + "' AND " + 
											   "label = '" + this.studyPeriod.getLabel() + "' AND " + 
											   "modCode = '" + this.getApproval().getModule().getCode() + "' AND " + 
											   "degCode = '" + this.getApproval().getDegree().getCode() + "';"
												);
				System.out.println("Changes made: " + count);
				switch (count) {
					case 0:
						return false;
					default:
						return true;
					
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * get the study level associated with the performance
		 * 
		 * @return char representing level
		 */
		public char getLevel() {
			return (this.getApproval().getLevel());
		}
		
		/*
		 * Testing functions. 
		 * Invalid and won't work until the Table Student is populated
		 * FK is needed
		 */
		public static void main(String[] args) {
			//test
			Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com");
			System.out.println(George.addStudent());
			
			StudyPeriod Test = new StudyPeriod('a', "2020-11-19","2021-11-19", George);
			System.out.println(Test.addStudyPeriod());
			
			Module fp = new Module("COM2108", "Functional Programming");
			System.out.println(fp.addToDB());
			
			Degree se = new Degree("COMP00", "MEng Software Engineering with a Year in Industry", "COM");
			System.out.println(se.addToDB());
			
			Approval fpse = new Approval(se, fp, 1, 10, '2');
			System.out.println(fpse.addToDB());

			
			
			Performance please = new Performance(Test, fpse, 45, 90);
			System.out.println(please.addPerformance());
			//System.out.println(please.removePerformance());
			
		}
}
