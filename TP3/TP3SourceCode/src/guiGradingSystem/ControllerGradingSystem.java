package guiGradingSystem;

import java.util.List;

import entityClasses.Post;
import entityClasses.Reply;

/**
 * Provides controller actions for the forum user interface.
 *
 * <p>This class contains event-handling methods for actions such as logging out
 * of the forum view and terminating the application.</p>
 */
public class ControllerGradingSystem {
	/**
	 * Creates a forum controller.
	 */
	public ControllerGradingSystem(){
	}
	
	/**
	 * <p>Directs user to add post window</p>
	 */
	protected static void performNewAssignment() {
		ViewGradingSystem.showNewAssignmentWindow();
	}
	
	/**
	 * <p>Opens detail of specific post</p>
	 * @param post that user want to read
	 */
	protected static void performReadSpecificPost(Post post) {
		// post selected, mark the user as read
		post.markAsRead( ViewGradingSystem.theUser.getUserName());
	    ModelGradingSystem.markAsReadAllRepies(post.getId());

	    //ViewGradingSystem.displayPostDetails(post);	
	}

	/**
	 * <p>Directs user to edit post window</p>
	 * @param post is being edited
	 */
	protected static void performEditPost(Post post) {
		ViewGradingSystem.showEditPostWindow(post);
	}
	
	/**
	 * <p>Verifies users on post deletion</p>
	 * 
	 * @param post is being deleted
	 */
	protected static void performDeletePost(Post post) {
		ViewGradingSystem.confirmAndDeletePost(post);
	}
	
	/**
	 * <p>Updates post list whenever an user search for specific keyword or thread or both
	 * 
	 * @param keyword is text that user want to find in title or content
	 * @param thread is post thread
	 */
	protected static void performSearchButton(String keyword, String thread) {
		 List<Post> results = ModelGradingSystem.filterPosts(keyword, thread);
		 //ViewGradingSystem.updatingList(results);
	}
	
	/**
	 * <p>Updates post list to all posts in the database </p>
	 */
	protected static void performClearButton() {
//		List<Post> results = ModelGradingSystem.getStudentList();
//		ViewGradingSystem.updatingList(results);
	}
	
	/**
	 * <p>Performs action when reply button is clicked</p>
	 * @param postId is the id of the post to be replied
	 * @param author is an author's reply
	 * @param content is reply's content
	 * @return a String represents error message, empty if there is no error
	 */
	protected static String performAddReply(int postId, String author, String content, boolean isPrivate) {
		return ModelGradingSystem.addReply(content, author, postId, isPrivate);
	}
	
	/**
	 * <p>Directs user to edit reply window</p>
	 * 
	 * @param reply is being edited
	 */
	protected static void performEditReply(Reply reply) {
		ViewGradingSystem.showEditReplyWindow(reply);
	}
	
	/**
	 * <p>Verifies users on reply deletion</p>
	 * 
	 * @param reply is being deleted
	 */
	protected static void performDeleteReply(Reply reply) {
		ViewGradingSystem.confirmAndDeleteReply(reply);
	}
	
	/**
	 * <p>Performs button to list all posts of the current user</p>
	 */
	protected static void performMyPostButton() {
//		List<Post> results = ModelGradingSystem.getPostsByUser(ViewGradingSystem.theUser.getUserName());
//		ViewGradingSystem.updatingList(results);
	}
	
	/**
	 * Logs the current user out of the forum and returns to the login screen.
	 */
	protected static void performLogout() {
		// TODO: fix this later
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewGradingSystem.theStage);
	}
	
	/**
	 * Terminates execution of the application.
	 */
	protected static void performQuit() {
		System.exit(0);
	}
	
	/**
	 * This function return the user to the home page
	 */
	protected static void performReturn() {
		if (ViewGradingSystem.theRole == 2) {
			guiStudentHome.ViewStudentHome.displayStudentHome(ViewGradingSystem.theStage, ViewGradingSystem.theUser);
		}
		else {
			guiStaffHome.ViewStaffHome.displayStaffHome(ViewGradingSystem.theStage, ViewGradingSystem.theUser);
		}
		
	}
}