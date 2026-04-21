package guiGradingSystem;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import entityClasses.*;

/**
 * Provides the in-memory model logic for the forum feature.
 *
 * <p>This class manages forum posts and replies, validates user actions,
 * supports soft deletion of posts, and provides filtering and retrieval
 * operations for the forum user interface.</p>
 */
public class ModelGradingSystem {

	private static GradingSystem gradeSystem = new GradingSystem();

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**
	 * The class constructor but it will not be used in this project
	 */
	public ModelGradingSystem() {
		
	}
	static {
	    
	    
	    newAssignment("Assignment 1", "this", 100, 25);
	    newAssignment("Assignment 2", "this", 100, 25);
	    newAssignment("Assignment 3", "this", 100, 50);
	}
	
	/**
	 * Adds a new post to the forum after validating its title, content, and author.
	 *
	 * @param title the title of the post
	 * @param content the body content of the post
	 * @param author the username of the author creating the post
	 * @return an empty string if the post is added successfully; otherwise, an error message
	 */
	// Post Action
	public static String addPost(String title, String content, String author) {
	    return newAssignment(title, content, 0, 0);
	}
	
	/**
	 * Create new assignment with title, content, maxScore and weight
	 * @param title an String contains the title of the assignment
	 * @param content an String contains the content of the assignment
	 * @param maxScore an integer of the maximum score possible
	 * @param weight an integer for the percentage weight contribute to the total weight
	 * @return a String if empty, there are no errors. Otherwise it returns error messages
	 */
	public static String newAssignment(String title, String content, int maxScore, int weight) {		
		/** TODO: Validate the field **/
		gradeSystem.createAssignment(title, content, maxScore, weight);
		return "";
	}
	
	/**
	 * Get the student list of the entire system
	 * @return a List of string contains all the username
	 */
	public static List<String> getStudentList() {
		List<String> userList = theDatabase.getUserList();
		
		// the result student list
		List<String> studentList = new ArrayList<String>(); 
		
		for (String username: userList) {
			// get the User from the theDatabase
			User user = theDatabase.getUserDetails(username);
			
			// check if the user is student
			if (user != null && user.getStudent()) {
				studentList.add(username);
			}
		}
		
		return studentList;
	}
	
	/**
	 * Get a list of assignments
	 * @return List of Assignment 
	 */
	public static List<Assignment> getAssignmentList() {
		return gradeSystem.getAssnList();
	}
	
	/**
	 * Get the total score for the given student
	 * @param studentName a string contains the student's username
	 * @return a float which is the student's total score
	 */
	public static float getTotalScore(String studentName) {
		return gradeSystem.getTotalScore(studentName); 
	}
	
	/**
	 * Check the total weight if it is 100% or not 
	 * @return return an empty string if it is exactly 100 else return helpful message
	 */
	public static String checkTotalWeight() {
		if (gradeSystem.getTotalWeight() != 100) {
			if (gradeSystem.getTotalWeight() < 100) {
				return "The total weight is less than 100"; 
			}
			return "The total weight is greater than 100";
		}
		return "";
	}
	
	/**
	 * Set the feedback for the student's assignment
	 * @param index an integer which is the index of the assignment
	 * @param studentUsername a String contain the student's username
	 * @param score an integer which is the current score
	 * @param comment a String contains the feedback comment
	 * @return A err messages if it occur
	 */
	public static String setFeedback(int index, String studentUsername, int score, String comment) {
		return gradeSystem.setFeedback(index, studentUsername, score, comment);
	}
	
	/**
	 * Set the max score of the assignment. This will reset all the score to 0
	 * @param index an integer which is the index of the assignment
	 * @param newScore an integer which is the new max score
	 * @return An error message if it occur
	 */
	public static String setMaxScore(int index, int newScore) {
		return gradeSystem.setMaxScore(index, newScore);
	}
	
	/**
	 * Set new weight the to the assignment at the given index
	 * @param index an integer which is the index 
	 * @param newWeight an integer which is the weight
	 * @return An error message if it occur
	 */
	public static String setWeight(int index, int newWeight) {
		return gradeSystem.setWeight(index, newWeight);
	}
	
	/**
	 * Delete Assignment at index
	 * @param index an integer which is the index of the post
	 * @return an error message if any occur
	 */
	public static String deleteAssignment(int index) {
		return gradeSystem.delete(index);
	}
	
	/**
	 * Delete an Assignmnet given the object
	 * @param assn an assignment object 
	 * @return an error message if any occur
	 */
	public static String deleteAssignment(Assignment assn) {
		return gradeSystem.delete(assn);
	}
}
