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

	private static entityClasses.PostStore postStore = new entityClasses.PostStore();
	private static entityClasses.ReplyStore replyStore = new entityClasses.ReplyStore();
	private static ThreadStore threadStore = new ThreadStore();
	
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
	 * Soft deletes a post if it exists and the requesting user is its author.
	 *
	 * <p>Soft deletion preserves the post in the store but replaces its visible
	 * fields with {@code "[DELETED]"} and marks it as deleted.</p>
	 *
	 * @param id the ID of the post to delete
	 * @param author the username of the user attempting to delete the post
	 * @return an empty string if the post is deleted successfully; otherwise, an error message
	 */
	public static String deletePost(int id, String author) {
		// Checking if post exist
		Post deletedPost = postStore.retrieve(id);
		if (deletedPost == null) return "Post doesn't exist";
		
		if (deletedPost.isDeleted()) return "Post already deleted";

		// Check authorization to delete
		if (!java.util.Objects.equals(deletedPost.getAuthor(), author)) {
			return "Can't delete another user's post";
		}
		
		deletedPost.softDelete();
		return "";
	}
	
	/**
	 * Edits an existing post if it exists, has not been deleted, and the requesting
	 * user is its author.
	 *
	 * @param id the ID of the post to edit
	 * @param author the username of the user attempting to edit the post
	 * @param title the new title for the post
	 * @param content the new content for the post
	 * @return an empty string if the edit succeeds; otherwise, an error message
	 */
	public static String editPost(int id, String author, String title, String content) {
	    Post editedPost = postStore.retrieve(id);
	    if (editedPost == null) return "Post doesn't exist";

	    return editPost(id, editedPost.getThread(), author, title, content);
	}
	

  /**
	 * <p>Update existed post with new thread, title, and content (cannot update author)</p>
	 * @param id is an integer that represents post's id
	 * @param thread is a String that represents name of new thread
	 * @param author is a String that represents post's author (unused)
	 * @param title is a String that represents new title
	 * @param content is a String that represents new content
	 * @return a String - error message, empty string if there is no error
	 */	
	public static String editPost(int id, String thread, String author, String title, String content) {
	    Post editedPost = postStore.retrieve(id);
	    if (editedPost == null) return "Post doesn't exist";

	    if (editedPost.isDeleted()) {
	        return "Can't edit a deleted post";
	    }

	    if (!java.util.Objects.equals(editedPost.getAuthor(), author)) {
	        return "Can't edit other's user post";
	    }

	    // Attempt to set thread and title and content (setTitle/setContent return error strings or "")
	    
	    String errorMessage = "";
	    
	    // Validate thread
	    String threadErrorMessage = Post.validateThread(thread, threadStore);
	    if (threadErrorMessage.isEmpty()) editedPost.setThread(thread); 
	    else errorMessage += threadErrorMessage;
	    
	    // Validate title
		String titleErrorMessage = Post.validateTitle(title);
		if (titleErrorMessage.isEmpty()) editedPost.setTitle(title);
		else errorMessage += (errorMessage.isEmpty() ? "" : "\n") + titleErrorMessage;
		
		// Validate content
	    String contentErrorMessage = Post.validateContent(content);
		if (contentErrorMessage.isEmpty()) editedPost.setContent(content);
		else errorMessage += (errorMessage.isEmpty() ? "" : "\n") + contentErrorMessage;
	    
	    return errorMessage;
	}
	
	/**
	 * Get post belongs to the given user with the username
	 * 
	 * @param user the username of the user
	 * @return an ArrayList of Post class
	 */
	public static ArrayList<Post> getPostsByUser(String user) {
		return postStore.getPostsByUser(user);
	}
	// Reply Action
  /**
	 * Adds a reply to an existing post after validating the reply content.
	 *
	 * <p>Replies cannot be added to deleted posts.</p>
	 *
	 * @param content the content of the reply
	 * @param author the username of the reply author
	 * @param parentId the ID of the parent post receiving the reply
	 * @return an empty string if the reply is added successfully; otherwise, an error message
	 */
	public static String addReply(String content, String author, int parentId) {
	    return addReply(content, author, parentId, false);
	}
	
	public static String addReply(String content, String author, int parentId, boolean isPrivate) {

	    // Validate content
		String contentErrorMessage = Post.validateContent(content);
	    if (!contentErrorMessage.isEmpty()) return contentErrorMessage;

	    Post parentPost = postStore.retrieve(parentId);

	    if (parentPost == null) {
	        return "Parent post not found";
	    }

	    if (parentPost.isDeleted()) {
	        return "Cannot reply to a deleted post";
	    }

	    int id = replyStore.getMaxId()+1;
	    
	    System.out.println("replyID:" + parentId);

	    // Create + save reply
	    Reply newReply = new Reply(id, content, author, parentId, isPrivate);
	    replyStore.add(newReply);

	    // Link reply id to parent post 
	    parentPost.addReplyId(id);


	    return "";
	}
	
	/**
	 * Deletes an existing reply if it exists and the requesting user is its author.
	 *
	 * @param id the ID of the reply to delete
	 * @param author the username of the user attempting to delete the reply
	 * @return an empty string if the reply is deleted successfully; otherwise, an error message
	 */
	public static String deleteReply(int id, String author) {
		// Checking if post exist
		Reply deletedReply = replyStore.retrieve(id);
		if (deletedReply == null) return "Reply doesn't exist";
		
		// Check authorization to delete
		if (!deletedReply.getAuthor().equals(author)) return "Can't delete other's user reply";
		
		// Delete the post
		replyStore.remove(deletedReply);
		return "";
	}
	
	/**
	 * Edits an existing reply if it exists and the requesting user is its author.
	 *
	 * @param id the ID of the reply to edit
	 * @param author the username of the user attempting to edit the reply
	 * @param content the new content of the reply
	 * @return an empty string if the reply is edited successfully; otherwise, an error message
	 */
	public static String editReply (int id, String author,String content) {		
		// Checking if post exist
		Reply editedReply = replyStore.retrieve(id);
		if (editedReply == null) return "Reply doesn't exist";
		
		// Check authorization to delete
		if (!editedReply.getAuthor().equals(author)) return "Can't edit other's user reply";
		
	    String contentErrorMessage = Post.validateContent(content);
		if (contentErrorMessage.isEmpty()) editedReply.setContent(content);
		else return contentErrorMessage;
		
		return "";
	}
	
	/**
	 * Returns all replies associated with a given post.
	 *
	 * @param id the ID of the post whose replies should be retrieved
	 * @return a list of replies associated with the specified post
	 */
	public static List<Reply> getRepliesByPostId(int id) {

	    Post currentPost = postStore.retrieve(id);
	    if (currentPost == null) {
	        return new ArrayList<>();
	    }

	    ArrayList<Integer> replyIdList = currentPost.getReplyPostId();
	    
	    List<Reply> replies = new ArrayList<>();
	    
	    for (Integer replyId : replyIdList) {
	        Reply r = replyStore.retrieve(replyId); // assuming retrieve(id) exists
	        if (r != null) {
	            replies.add(r);
	        }
	    }

	    return replies;
	}
	
	public static List<Reply> getRepliesByPostId(User reader, int id) {

	    Post currentPost = postStore.retrieve(id);
	    if (currentPost == null) {
	        return new ArrayList<>();
	    }

	    ArrayList<Integer> replyIdList = currentPost.getReplyPostId();
	    
	    List<Reply> replies = new ArrayList<>();
	    
	    for (Integer replyId : replyIdList) {
	        Reply r = replyStore.retrieve(replyId); // assuming retrieve(id) exists
	        boolean visibleReply = false;
	        
	        if (r != null) {
	        	if (!r.getIsPrivate()) {
	        		visibleReply = true;
	        	}
	        	else if (r.getIsPrivate() && 
	        			(r.getAuthor().equals(reader.getUserName()) || currentPost.getAuthor().equals(reader.getUserName()))) {
	        		visibleReply = true;
	        	}
	        }
	        
	        if (visibleReply) {
	        	replies.add(r);
	        }
	    }

	    return replies;
	}
	
	/**
	 * Filters the list of posts using the provided keyword.
	 *
	 * @param keyword the keyword used to filter posts
	 * @param thread is the thread name used to filter posts
	 * @return the filtered list of matching posts
	 */
	public static List<Post> filterPosts(String keyword, String thread) {
	    return postStore.filterPosts(keyword, thread); // uses your PostStore filter
	}

	/**
	 * Clears the current post filter so that all posts are included again.
	 */
	public static void clearPostFilter() { postStore.clearFilter();
	}

	/**
	 * Returns the current filtered post list.
	 *
	 * @return the filtered subset of posts
	 */
	public static List<Post> getFilteredPostList() {
	    return postStore.getSubsetPostList();
	}
	
	// Thread action
	
	/**
	 * <p>Create new thread in the database</p>
	 * @param name is a String that represents a new name of thread
	 * @return a String that represents the error message, if there is no error, the return string is empty
	 */
	public static String addThread(String name) {
		if (name == null || name.isBlank()) {
			return "Thread name could not be empty";
		}
		
		if (name.length() > 100) {
			return "Thread name could not be longer than 100 characters";
		}
		
		if (threadStore.checkThreadExist(name)) {
			return "Thread name could not be duplicated";
		}
		
		threadStore.addThread(name);
		
		return "";
	}
	
	/**
	 * <p>Reads all threads in the database</p>
	 * @return a String list that contains all thread's name in the database
	 */
	public static ArrayList<String> getAllThreads() {
		return threadStore.getAllThreads();
	}
	
	/**
	 * <p>Gets the database storing all posts, used for testing</p>
	 * @return PostStore object is the object storing all posts
	 */
	public static PostStore getPostStore() {
		return postStore;
	}
	
	/**
	 * <p>Gets the database storing all replies, used for testing</p>
	 * @return ReplyStore object is the object storing all replies
	 */
	public static ReplyStore getReplyStore() {
		return replyStore;
	}
	
	/**
	 * <p>Gets the database storing all threads, used for testing</p>
	 * @return ThreadStore object is the object storing all threads
	 */
	public static ThreadStore getThreadStore() {
		return threadStore;
	}
	
	/**
	 * <p><strong>Danger</strong>: clears all posts, replies, and threads in the database, used only be used when testing</p>
	 */
	public static void hardReset() {
		replyStore.hardReset();
		postStore.hardReset();
		threadStore.hardReset();
	}
	
	/**
	 * <p>Reset to the default state of the forum</p>
	 */
	public static void setUpDefaultForum() {
	}	
	
	/**
	 * Return a list of unread posts given the username
	 * @param user a string contain the username
	 * @return an ArrayList class of Post class
	 */
	public static List<Post> getUnreads(String user) {
		return postStore.getUnreadPosts(user);
	}
	
	/**
	 * Return a list of unread replies given the user and postId
	 * 
	 * @param user the username
	 * @param postId the unique id of the post
	 * @return a list of Reply class
	 */
	public static List<Reply> getUnreadReplies(String user, int postId) {
		return replyStore.getUnreadReplies(user, postId);
	}
	
	/**
	 * Mark all the reply belong to a post as read
	 * @param postId the post id
	 */
	public static void markAsReadAllRepies(int postId) {
		for (Reply reply: getRepliesByPostId(postId)) {
			reply.markAsRead(ViewGradingSystem.theUser.getUserName());
		}
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
}
