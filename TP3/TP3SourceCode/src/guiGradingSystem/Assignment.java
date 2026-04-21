package guiGradingSystem;

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
	 * content of the assignment
	 */
	private String content;
	
	/**
	 * max score of the assignment
	 */
	private int maxScore;
	
	/** 
	 * A class used for feedback including numeric grade and and comment 
	 */
	class Feedback {
		/** 
		 * The numeric score of the feedback
		 */
		private int score;
		
		/**
		 * The comment of the grade
		 */
		private String comment;
		
		/**
		 * Default constructor, set score to 0 and comment to empty 
		 */
		public Feedback() {
			score = 0;
			comment = "";
		}

		/**
		 * Public constructor to create a feedback
		 * @param score an Integer which is the set score of the student
		 * @param comment an String which is the comment of the grade
		 */
		public Feedback(int score, String comment) {
			this.score = score;
			this.comment = comment;
		}
		
		/**
		 * Get the score of the feedback
		 * @return an integer which is the score 
		 */
		public int getScore() {
			return score;
		}
		
		/** 
		 * Get the comment string of the feedback
		 * @return a String which is the comment 
		 */
		public String getComment() {
			return comment;
		}
		
		/** 
		 * Set the score for the feedback
		 * @param score an integer which is the feedback score
		 */
		public void setScore(int score) {
			this.score = score;
		}
		
		/**
		 * Set the comment for the feedback
		 * @param comment an String which is the comment
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}
	}
	/**
	 * current list of student's score and feedbacks (pair of Integer and String) of the assignment  
	 */
	private Map<String, Feedback> feedbacks;
	
	/**
	 * weight percentage of the assignment
	 */
	private int weight; 
	
	/**
	 * Default constructor 
	 */
	public Assignment() {
		title = "";
		content = "";
		maxScore = 0;
		weight = 0;
		
		feedbacks = new HashMap<>();
	}
	
	/**
	 * Constructor to create an assignment with tile, maxScore, score and weight
	 * @param title A string contain the title of the assignment
	 * @param content A string contain the content of the assignment
	 * @param maxScore the maximum integer score of the assignment 
	 * @param weight the percentage weight of the assignment
	 */
	public Assignment(String title, String content, int maxScore, int weight) {
		this.title = title; 
		this.maxScore = maxScore;
		this.weight = weight;
		this.content = content;
		
		feedbacks = new HashMap<>();
	}
	
	/**
	 * Function to get the title of the assignment
	 * @return a string which is the title 
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Get the content of the assignment
	 * @return a String which is the content of the assignment
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Function to get the set max score of the assignment 
	 * @return an integer which is the max score
	 */
	public int getMaxScore() {
		return maxScore;
	}
	
	/**
	 * Return the feedback given the student's name. If the student haven't graded yet, return default feedback
	 * @param studentName an string contains the student's name
	 * @return a Feedback class that contain information about the student's grade
	 */
	public Feedback getFeedback(String studentName) {
		if (!feedbacks.containsKey(studentName))
			return new Feedback();
		return feedbacks.get(studentName);
	}

	/**
	 * Function to get the feedbacks map of students
	 * @return a map of feedbacks
	 */
	public Map<String, Feedback> getFeedbacksMap() {
		return feedbacks;
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
	 * Set the content of the assignment
	 * @param content a string which is the new content
	 */
	public void setContent(String content) {
		this.content = content;
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
		if (!feedbacks.containsKey(studentName))
			feedbacks.put(studentName, new Feedback(studentScore, ""));
		else {
			String currentComment = feedbacks.get(studentName).getComment();
			feedbacks.replace(studentName, new Feedback(studentScore, currentComment));
		}
	}
	
	/**
	 * Set feedback for a student given their name and the comment string
	 * @param studentName a String contains the name of the student
	 * @param comment a String contains the feedback's comment
	 */
	public void setComment(String studentName, String comment) {
		if (!feedbacks.containsKey(studentName))
			feedbacks.put(studentName, new Feedback(0, comment));
		else {
			int currentScore = feedbacks.get(studentName).getScore();
			feedbacks.replace(studentName, new Feedback(currentScore, comment));
		}
	}
	/**
	 * Reset the score map
	 */
	public void resetScore() {
		feedbacks.clear();
	}
	
	/**
	 * Change the weight of the assignment 
	 * @param weight an integer which is the new weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
