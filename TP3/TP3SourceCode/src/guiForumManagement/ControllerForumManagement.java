package guiForumManagement;

import java.util.Optional;

import entityClasses.PostStore;
import guiForum.ModelForum;
import guiForum.ViewerForum;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ControllerForumManagement {
	protected static void performThreadManagement() {
		guiForumManagement.ViewerForumManagement.displayThreadManagement();
	}
	
	protected static String performAddThread(String newThread) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot create thread";
		
		String errorMessage = ModelForumManagement.createThread(newThread);
		
		if (!errorMessage.isEmpty()) {
			ViewerForumManagement.displayAlert(AlertType.ERROR,errorTitle, errorHeader, errorMessage);
		}
		else {
			ViewerForumManagement.loadThreadData();
			ViewerForumManagement.displayAlert(AlertType.INFORMATION, 
					"Valid Operation", "Create thread successfully", "<" + newThread + ">" + " thread is in the database now");
		}
		
		return errorMessage;
	}
	
	protected static String performDeleteThread(String deleteThread) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot delete thread";
		
		String errorMessage = ModelForumManagement.validateDeleteThread(deleteThread);
		
		if (!errorMessage.isEmpty()) {
			ViewerForumManagement.displayAlert(AlertType.ERROR, errorTitle, errorHeader, errorMessage);
		}
		else {
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
	
	protected static String performEditThread(String oldName, String newName) {
		String errorTitle = "Invalid Operation";
		String errorHeader = "Cannot edit thread";
		
		String errorMessage = ModelForumManagement.validateEditThread(oldName, newName);
		
		if (!errorMessage.isEmpty()) {
			ViewerForumManagement.displayAlert(AlertType.ERROR, errorTitle, errorHeader, errorMessage);
		}
		else {
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
}