package GradingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the prototype for Assignment class 
 */
public class Assignment {
	/**
	 * title of the assignment
	 */
	private String title;
	
	/**
	 * max score of the assignment
	 */
	private int maxScore;
	
	/**
	 * current list of student's score of the assignment 
	 */
	private Map<String, Integer> score;
	
	/**
	 * weight percentage of the assignment
	 */
	private int weight; 
	
	/**
	 * Default constructor 
	 */
	public Assignment() {
		title = "";
		maxScore = 0;
		weight = 0;
		
		score = new HashMap<>();
	}
	
	/**
	 * Constructor to create an assignment with tile, maxScore, score and weight
	 * @param title A string contain the title of the assignment
	 * @param maxScore the maximum integer score of the assignment 
	 * @param weight the percentage weight of the assignment
	 */
	public Assignment(String title, int maxScore, int weight) {
		this.title = title; 
		this.maxScore = maxScore;
		this.weight = weight;
		
		score = new HashMap<>();
	}
	
	/**
	 * Function to get the title of the assignment
	 * @return a string which is the title 
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Function to get the set max score of the assignment 
	 * @return an integer which is the max score
	 */
	public int getMaxScore() {
		return maxScore;
	}
	
	/**
	 * Function to get the current student's score of the assignment
	 * @param studentName a string contains student's name
	 * @return an integer which is the current score, if the student dont have a score yet, return 0
	 */
	public int getScore(String studentName) {
		if (!score.containsKey(studentName))
			return 0;
		return score.get(studentName);
	}

	/**
	 * Function to get the score map of students
	 * @return a map of score
	 */
	public Map<String, Integer> getScoreMap() {
		return score;
	}
	
	/** 
	 * Function to get the weight of the assignment 
	 * @return an integer which is the percentage weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Change the title of the assignment 
	 * @param title a string which is the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Change the maxScore of the assignment 
	 * After changing the maxScore, the current score reset
	 * @param maxScore an integer which is the new max score
	 */
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
		resetScore();
	}
	
	/**
	 * Set score for a student given a name and the score
	 * @param studentName a string of the student's name
	 * @param studentScore an integer of the student's score
	 */
	public void setScore(String studentName, int studentScore) {
		if (score.containsKey(studentName))
			score.replace(studentName, studentScore);
		else 
			score.put(studentName, studentScore);
	}
	
	/**
	 * Reset the score map
	 */
	public void resetScore() {
		score.clear();
	}
	
	/**
	 * Change the weight of the assignment 
	 * @param weight an integer which is the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
