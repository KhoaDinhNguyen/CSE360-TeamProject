package guiGradingSystem;

import java.util.ArrayList;

/**
 * This is the prototype grading system 
 */
public class GradingSystem {
	/**
	 * A list for all the assignment 
	 */
	private ArrayList<Assignment> AssnList; 
	
	/**
	 * The default constructor
	 */
	public GradingSystem() {
		AssnList = new ArrayList<>();
	}
	
	/**
	 * A function to create new assignment with title, max score and weight
	 * @param title a string which is the title
	 * @param maxScore an integer which is the max score
	 * @param weight an integer which is the percentage weight
	 */
	public void createAssignment(String title, int maxScore, int weight) {
		Assignment newAssn = new Assignment(title, maxScore, weight); 
		AssnList.add(newAssn);
	}
	
	/**
	 * Get the assignment at certain index
	 * @param index an integer index of the target assignment 
	 * @return an Assignment object
	 */
	public Assignment getAssignment(int index) {
		return AssnList.get(index);
	}
	
	/**
	 * Return the number of assignment 
	 * @return an integer
	 */
	public int size() {
		return AssnList.size();
	}
	
	/**
	 * Get the title of the assignment 
	 * @param index an integer which is the index of the assignment 
	 * @return a string contains the title of the assignment
	 */
	public String getTitle(int index) { 
		return getAssignment(index).getTitle();
	}
	
	/**
	 * Return the max score for the given assignment index
	 * @param index an integer which is the index of the assignment 
	 * @return an integer which is the max score
	 */
	public int getMaxScore(int index) {
		return getAssignment(index).getMaxScore();
	}
	
	/**
	 * Get student's current score given the name 
	 * @param index an integer which is the index of the assignment
	 * @param studentName an string which is the student's name
	 * @return an integer, the current score of the student
	 */
	public int getScore(int index, String studentName) {
		return getAssignment(index).getScore(studentName).getScore();
	}
	
	/**
	 * Get the assignment's weight
	 * @param index an integer that is the assignmemnt's index
	 * @return an integer which is the percentage weight
	 */
	public int getWeight(int index) {
		return getAssignment(index).getWeight();
	}
	
	/**
	 * Get the total weight of the system 
	 * @return an integer which is the total weight
	 */
	public int getTotalWeight() {
		int totalWeight = 0;
		for (Assignment a: AssnList) {
			totalWeight += a.getWeight();
		}
		
		return totalWeight;
	}
	
	/**
	 * A function to update the assignment't title 
	 * @param index an integer which is the assignment's index
	 * @param title a string which is the new title
	 */
	public void setAssignTitle(int index, String title) {
		getAssignment(index).setTitle(title);
	}
	
	/**
	 * Set max score of a assignment 
	 * @param index an integer which is the index of the assignment
	 * @param maxScore an integer which is the new max score 
	 * @return an String that report error message, null mean execute successfully 
	 */
	public String setMaxScore(int index, int maxScore) {
		if (maxScore > 100)
			return "Max grade should not exceed 100";
		if (maxScore < 0)
			return "Negative max grade are not allowed";

		getAssignment(index).setMaxScore(maxScore);

		return null;
	}
	
	/**
	 * Assign score for the student given the assignment index
	 * @param index an integer which is the assignment's index
	 * @param studentName a string contains the student's name
	 * @param score an integer which is the assigned score
	 * @return an string contain error message, will be null if execute successfully
	 */
	public String setScore(int index, String studentName, int score) {
		if (score < 0)
			return "Negative grade are not allowed";
		if (score > getMaxScore(index))
			return "Assigned grade cannot be exceed the max grade";
		
		getAssignment(index).setScore(studentName, score);
		
		return null;
	}
	

	/**
	 * Set the weight of the assignment
	 * When the total weight is not 100, return a helpful message 
	 * 
	 * @param index an integer which is the assignment's index
	 * @param weight an integer which is the new weight
	 * @return a string contain error message, return null if there is not any
	 */
	public String setWeight(int index, int weight) {
		if (weight < 0)
			return "Weight percentage cannot be negative";
		if (weight > 100)
			return "Weight percentage cannot exceed 100";
		
		getAssignment(index).setWeight(weight);
		
		if (getTotalWeight() != 100)
			return "The total weight is not 100";
		
		return null;
	}
	
	/**
	 * Get total score of the student
	 * @param studentName a string contains student't name
	 * @return the student's total score
	 */
	public float getTotalScore(String studentName) {
		float totalScore = 0;
		for (Assignment a: AssnList) {
			int score = a.getScore(studentName);
			int maxScore = a.getMaxScore();
			int weight = a.getWeight();
			
			totalScore += 1.f * score / maxScore * weight;
		}
		
		return totalScore;
	}
}
