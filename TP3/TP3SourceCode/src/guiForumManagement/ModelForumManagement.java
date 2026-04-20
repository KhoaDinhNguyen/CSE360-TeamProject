package guiForumManagement;

import java.util.ArrayList;
import java.util.List;

import entityClasses.Post;
import entityClasses.ThreadStore;
import guiForum.ModelForum;

/**
 * {@code ModelForumManagement} is used to contain database operation related to forum management
 */
public class ModelForumManagement {
	private static ThreadStore theThreadStore = ModelForum.getThreadStore();
	private static entityClasses.PostStore postStore = ModelForum.getPostStore();
	private static entityClasses.ReplyStore replyStore = ModelForum.getReplyStore();
	
	/**
	 * The constructor of the class but it will not be used in this project 
	 */
	public ModelForumManagement() { }
	
	/**
	 * Adds new thread to the database
	 * @param threadName is a String that represents thread's name
	 * @return a String displaying error message, empty if there is no error
	 */
	protected static String createThread(String threadName) {
		if (threadName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (theThreadStore.checkThreadExist(threadName)) {
			return "Thread already existed in the database";
		}
		
		theThreadStore.addThread(threadName);
		
		return "";
	}
	
	/**
	 * Verifies that delete operation on thread is valid
	 * @param threadName is a String that represents deleted thread
	 * @return a String displaying error message, empty if there is no error
	 */
	protected static String validateDeleteThread(String threadName) {
		if (threadName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (threadName.compareTo("General") == 0) {
			return "Cannot delete General thread";
		}
		else if (!theThreadStore.checkThreadExist(threadName)) {
			return "Thread does not exist in the database";
		}
		
		return "";
	}
	
	/**
	 * Deletes thread from the database
	 * @param threadName is a String that represents deleted thread
	 * @return a String displaying error message, empty if there is no error
	 */
	protected static String deteleThread(String threadName) {
		System.out.println(theThreadStore.getAllThreads().size());
		String errorMessage = validateDeleteThread(threadName);
		
		if (!errorMessage.isEmpty()) return errorMessage;
		
		theThreadStore.deleteThread(threadName);
		
		List<Post> postList = ModelForum.getPostList();
		
		for (int i = 0; i < postList.size(); ++i) {
			Post p = postList.get(i);
			
			if (p.getThread().compareTo(threadName) == 0) {
				ModelForum.editPost(p.getId(), "General", p.getAuthor(), p.getTitle(), p.getContent());
			}
		}
		
		return "";
	}
	
	/**
	 * Verifies that update operation on thread is valid
	 * @param oldName is a String that represents old thread's name
	 * @param newName is a String that represents new thread's name
	 * @return a String displaying error message, empty if there is no error
	 */
	protected static String validateEditThread(String oldName, String newName) {
		if (newName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (oldName.compareTo("General") == 0) {
			return "Cannot edit General thread";
		}
		else if (!theThreadStore.checkThreadExist(oldName)) {
			return "Thread does not exist in the database";
		}
		else if (theThreadStore.checkThreadExist(newName)) {
			return "Thread already exists in the database";
		}
		
		return "";
	}
	
	/**
	 * Updates thread from the database
	 * @param oldName is a String that represents old thread's name
	 * @param newName is a String that represents new thread's name
	 * @return a String displaying error message, empty if there is no error
	 */
	protected static String editThread(String oldName, String newName) {
		String errorMessage = validateEditThread(oldName, newName);
		
		if (!errorMessage.isEmpty()) return errorMessage;
		
		theThreadStore.editThread(oldName, newName);
		
		List<Post> postList = ModelForum.getPostList();
		
		for (int i = 0; i < postList.size(); ++i) {
			Post p = postList.get(i);
			
			if (p.getThread().compareTo(oldName) == 0) {
				ModelForum.editPost(p.getId(), newName, p.getAuthor(), p.getTitle(), p.getContent());
			}
		}
		
		return "";
	}
	
	/**
	 * Get all thread from the database
	 * @return list of String
	 */
	protected static ArrayList<String> readAllThread() {
		return theThreadStore.getAllThreads();
	}
	
}