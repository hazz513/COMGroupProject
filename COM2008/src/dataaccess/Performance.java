package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Performance {
	//Database Information
	private static final String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team012";
	private static final String DB_USER_NAME = "team012";
	private static final String DB_PASSWORD =  "0232ab87";
	
	private Student student;
	private StudyPeriod studyPeriod;
	private Module module;
	private int grade;
	private int resitGrade;
	
	public Performance(Student student, StudyPeriod studyPeriod, Module module, int grade, int resitGrade) {
		this.student = student;
		this.studyPeriod = studyPeriod;
		this.module = module;
		this.grade = grade;
		this.resitGrade = resitGrade;
	}
	
	//get methods (May need to create a set for the student object)
	public Student getStudent() {
		return student;
	}
	public StudyPeriod getStudyPeriod() {
		return studyPeriod;
	}
	public Module getModule() {
		return module;
	}
	public int getGrade() {
		return grade;
	}
	public int getResitGrade() {
		return resitGrade;
	}
	
	//set methods
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public void setResitGrade(int resitGrade) {
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
												this.getStudent().getRegistration() + "','" +
												this.getStudyPeriod().getLabel() + "','" +
												this.getModule().getCode() + "','" +
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
											   "registration = '" + this.student.getRegistration() + "' AND " + 
											   "label = '" + this.studyPeriod.getLabel() + "' AND " + 
											   "modCode = '" + this.module.getCode() + "';"
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
		 * Testing functions. 
		 * Invalid and won't work until the Table Student is populated
		 * FK is needed
		 */
		public static void main(String[] args) {
			//test
			Student George = new Student(1241214, "Mr", "Ashcroft", "George","george@fake.com");
			//System.out.println(George.addStudent());
			
			StudyPeriod Test = new StudyPeriod('a', "2020-11-19","2021-11-19", George);
			//System.out.println(Test.addStudyPeriod());
			
			Module fp = new Module("COM2108", "Functional Programming");
			//System.out.println(fp.addToDB());
			
			Performance please = new Performance(George, Test, fp, 45, 90);
			//System.out.println(please.addPerformance());
			System.out.println(please.removePerformance());
			
		}
}
