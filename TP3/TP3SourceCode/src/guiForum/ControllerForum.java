package guiForum;

import java.util.List;

import entityClasses.Post;
import entityClasses.Reply;

/**
 * Provides controller actions for the forum user interface.
 *
 * <p>This class contains event-handling methods for actions such as logging out
 * of the forum view and terminating the application.</p>
 */
public class ControllerForum {
	/**
	 * Creates a forum controller.
	 */
	public ControllerForum(){
	}
	
	/**
	 * <p>Directs user to add post window</p>
	 */
	protected static void performAddPost() {
		ViewerForum.showAddPostWindow();
	}
	
	/**
	 * <p>Opens detail of specific post</p>
	 * @param post that user want to read
	 */
	protected static void performReadSpecificPost(Post post) {
		// post selected, mark the user as read
		post.markAsRead( ViewerForum.theUser.getUserName());
	    ModelForum.markAsReadAllRepies(post.getId());

	    ViewerForum.displayPostDetails(post);	
	}

	/**
	 * <p>Directs user to edit post window</p>
	 * @param post is being edited
	 */
	protected static void performEditPost(Post post) {
		ViewerForum.showEditPostWindow(post);
	}
	
	/**
	 * <p>Verifies users on post deletion</p>
	 * 
	 * @param post is being deleted
	 */
	protected static void performDeletePost(Post post) {
		ViewerForum.confirmAndDeletePost(post);
	}
	
	/**
	 * <p>Updates post list whenever an user search for specific keyword or thread or both
	 * 
	 * @param keyword is text that user want to find in title or content
	 * @param thread is post thread
	 */
	protected static void performSearchButton(String keyword, String thread) {
		 List<Post> results = ModelForum.filterPosts(keyword, thread);
		 ViewerForum.updatingList(results);
	}
	
	/**
	 * <p>Updates post list to all posts in the database </p>
	 */
	protected static void performClearButton() {
		List<Post> results = ModelForum.getPostList();
		ViewerForum.updatingList(results);
	}
	
	/**
	 * <p>Performs action when reply button is clicked</p>
	 * @param postId is the id of the post to be replied
	 * @param author is an author's reply
	 * @param content is reply's content
	 * @return a String represents error message, empty if there is no error
	 */
	protected static String performAddReply(int postId, String author, String content) {
		return ModelForum.addReply(content, author, postId);
	}
	
	/**
	 * <p>Directs user to edit reply window</p>
	 * 
	 * @param reply is being edited
	 */
	protected static void performEditReply(Reply reply) {
		ViewerForum.showEditReplyWindow(reply);
	}
	
	/**
	 * <p>Verifies users on reply deletion</p>
	 * 
	 * @param reply is being deleted
	 */
	protected static void performDeleteReply(Reply reply) {
		ViewerForum.confirmAndDeleteReply(reply);
	}
	
	/**
	 * <p>Performs button to list all posts of the current user</p>
	 */
	protected static void performMyPostButton() {
		List<Post> results = ModelForum.getPostsByUser(ViewerForum.theUser.getUserName());
		ViewerForum.updatingList(results);
	}
	
	/**
	 * Logs the current user out of the forum and returns to the login screen.
	 */
	protected static void performLogout() {
		// TODO: fix this later
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewerForum.theStage);
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
		Student.ViewRole1Home.displayRole1Home(ViewerForum.theStage, ViewerForum.theUser);
	}
}