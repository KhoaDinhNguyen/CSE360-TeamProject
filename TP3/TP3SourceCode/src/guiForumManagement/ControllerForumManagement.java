package guiForumManagement;

import java.util.Optional;

import entityClasses.PostStore;
import guiForum.ModelForum;
import guiForum.ViewerForum;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * {@code ControllerForumManagement} is used to interact between View and Model in forum management
 */
public class ControllerForumManagement {
	
	/**
	 * The constructor of the class but it will not be used in this project
	 */
	public ControllerForumManagement() { }
	
	/**
	 * Displays thread management as a staff
	 */
	protected static void performThreadManagement() { guiForumManagement.ViewerForumManagement.displayThreadManagement(); }
	
	/**
	 * Adds new thread to the database
	 * @param newThread is a String that displays new thread's name
	 * @return a String that represents error message, empty if there is no error
	 */
	protected static String performAddThread(String newThread) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot create thread";
		
		String errorMessage = ModelForumManagement.createThread(newThread);
		
		if (!errorMessage.isEmpty()) {
			// if the error exists, displays alert notification
			ViewerForumManagement.displayAlert(AlertType.ERROR,errorTitle, errorHeader, errorMessage);
		}
		else {
			ViewerForumManagement.loadThreadData();
			ViewerForumManagement.displayAlert(AlertType.INFORMATION, 
					"Valid Operation", "Create thread successfully", "<" + newThread + ">" + " thread is in the database now");
		}
		
		return errorMessage;
	}
	
	/**
	 * Deletes thread from the database as a staff
	 * @param deleteThread is a String that represents deleted thread
	 * @return a String that represents error message, empty if there is no error
	 */
	protected static String performDeleteThread(String deleteThread) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot delete thread";
		
		String errorMessage = ModelForumManagement.validateDeleteThread(deleteThread);
		
		if (!errorMessage.isEmpty()) {
			// If the error exists, displays alert notification
			ViewerForumManagement.displayAlert(AlertType.ERROR, errorTitle, errorHeader, errorMessage);
		}
		else {
			// If there is no error, prompting an warning that how many posts that will be updated
			int numberOfPostUsingDeleteThread = ModelForum.filterPosts("", deleteThread).size();
			
			Optional<ButtonType> result = ViewerForumManagement.displayAlert(AlertType.CONFIRMATION, 
					"Warning Operation", 
					"There are " + numberOfPostUsingDeleteThread + " post(s) using this thread",
					"All of post's deleted thread will become General");
		
			if (result.isPresent()) {
				if (result.get() == ButtonType.OK) {
					ModelForumManagement.deteleThread(deleteThread);
					ViewerForumManagement.loadThreadData();
					ViewerForumManagement.displayAlert(AlertType.INFORMATION, 
							"Valid Operation", "Delete thread successfully", "<" + deleteThread + ">" + " thread got deleted from the database");
					ViewerForum.updatingList(ModelForum.getPostStore().getPostList());
				}
			}
		}
		
		return errorMessage;
	}
	
	/**
	 * Updates thread from the database as a staff
	 * @param oldName is a String that displays old thread's name
	 * @param newName is a String that displays new thread's name
	 * @return a String that represents error message, empty if there is no error
	 */
	protected static String performEditThread(String oldName, String newName) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot edit thread";
		
		String errorMessage = ModelForumManagement.validateEditThread(oldName, newName);
		
		if (!errorMessage.isEmpty()) {
			// If the error exists, displays alert notification
			ViewerForumManagement.displayAlert(AlertType.ERROR, errorTitle, errorHeader, errorMessage);
		}
		else {
			// If there is no error, prompting an warning that how many posts that will be updated
			int numberOfPostUsingEditThread = ModelForum.filterPosts("", oldName).size();
			
			Optional<ButtonType> result = ViewerForumManagement.displayAlert(AlertType.CONFIRMATION, 
					"Warning Operation", 
					"There are " + numberOfPostUsingEditThread + " post(s) using this thread",
					"All of post's thread will be updated");
		
			if (result.isPresent()) {
				if (result.get() == ButtonType.OK) {
					ModelForumManagement.editThread(oldName, newName);
					ViewerForumManagement.loadThreadData();
					ViewerForumManagement.displayAlert(AlertType.INFORMATION, 
							"Valid Operation", "Edit thread successfully", "<" + oldName + ">" + " thread is updated to" + " <" + newName + ">");
					ViewerForum.updatingList(ModelForum.getPostStore().getPostList());

				}
			}
		}
		
		return errorMessage;
	}
	
	/**
	 * Logs out from window
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewerForumManagement.theStage);
	}
	
    /**
     * Quits application from the window
     */
	protected static void performQuit() {
		System.exit(0);
	}
	

	/**
	 * Returns to staff home page
	 */
	protected static void performReturn() {
		guiStaffHome.ViewStaffHome.displayStaffHome(ViewerForumManagement.theStage, ViewerForumManagement.theUser);
	}
}