package guiForum;

import java.util.ArrayList;
import java.util.List;

import CRUD.Post;
import CRUD.PostStore;
import CRUD.Reply;
import CRUD.ReplyStore;
import entityClasses.*;

/**
 * Provides the in-memory model logic for the forum feature.
 *
 * <p>This class manages forum posts and replies, validates user actions,
 * supports soft deletion of posts, and provides filtering and retrieval
 * operations for the forum user interface.</p>
 */
public class ModelForum {
	private static CRUD.PostStore postStore = new CRUD.PostStore();
	private static CRUD.ReplyStore replyStore = new CRUD.ReplyStore();
	private static ThreadStore threadStore = new ThreadStore();
	
	
	static {

	    // Sample THREADS
	    addThread("General");
	    addThread("Lectures");
	    addThread("Sections");
	    addThread("Problem Sets");
	    addThread("Assignments");
	    addThread("Social");
	    
	    // Sample POSTS
	    addPost("Welcome", "Welcome to the CSE 360 discussion forum!", "Admin");

	    addPost("Java OOP Question",
	            "Can someone explain inheritance vs composition?",
	            "Alice");

	    addPost("Binary Search Confusion",
	            "Why is binary search O(log n)?",
	            "Bob");

	    addPost("Database Normalization",
	            "What is 3NF and why is it important?",
	            "Charlie");

	    addPost("Git Merge Conflict",
	            "How do you resolve a merge conflict safely?",
	            "David");

	    addPost("Recursion Depth",
	            "Why do I get StackOverflowError in Java?",
	            "Emma");

	    // Sample REPLIES

	    addReply("Inheritance models an 'is-a' relationship.", "Admin", 1);
	    addReply("Composition is usually more flexible.", "Alice", 1);

	    addReply("Each step halves the search space.", "Charlie", 2);
	    addReply("That’s why it grows logarithmically.", "Admin", 2);

	    addReply("3NF removes transitive dependency.", "Emma", 3);

	    addReply("Always pull before pushing changes.", "Bob", 4);
	    addReply("Use git status to inspect conflicts.", "Admin", 4);

	    addReply("Infinite recursion without base case causes it.", "Alice", 5);
	    
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
	//TODO: Remove addPost(title, content, author) if the addPost(thread, title, content, author) works
	public static String addPost(String title, String content, String author) {

	    // Validate title
	    if (title == null || title.isBlank()) {
	        if (content == null || content.isBlank()) {
	            return "Title Content could not be empty";
	        }
	        return "Title could not be empty";
	    }

	    // Validate content
	    if (content == null || content.isBlank()) {
	        return "Content could not be empty";
	    }

	    // Validate author
	    if (author == null) {
	        return "Author can’t be null";
	    }

	    // Generate ID
	    int id = postStore.getMaxId() + 1;

	    // Save new post
	    Post newPost = new Post(id, title, content, author);
	    postStore.addPost(newPost);

	    return "";
	}
	
	public static String addPost(String thread, String title, String content, String author) {
		// Validate thread
		if (thread == null || thread.isBlank()) {
			return addPost("General", title, content, author);
		}
		else if (thread.length() > 100) {
			return "Thread name could not be longer than 100 characters";
		}
		else if (!threadStore.checkThreadExist(thread)) {
			return "Thread does not exist in the database";
		}
		
	    // Validate title
	    if (title == null || title.isBlank()) {
	        if (content == null || content.isBlank()) {
	            return "Title Content could not be empty";
	        }
	        return "Title could not be empty";
	    }

	    // Validate content
	    if (content == null || content.isBlank()) {
	        return "Content could not be empty";
	    }

	    // Validate author
	    if (author == null) {
	        return "Author can’t be null";
	    }

	    // Generate ID
	    int id = postStore.getMaxId() + 1;

	    // Save new post
	    Post newPost = new Post(id, thread, title, content, author);
	    postStore.addPost(newPost);

	    return "";
	}
	
  	/**
	 * Returns the complete list of forum posts.
	 *
	 * @return the list of posts currently stored in the forum
	 */
	public static List<Post> getPostList() {
		return postStore.getPostList();
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
	// TODO: remove editPost(id, author, title, content) if editPost(id, thread, author, title,content) works
	public static String editPost (int id, String author, String title, String content) {
	    // Check existence
	    Post editedPost = postStore.retrieve(id);
	    if (editedPost == null) return "Post doesn't exist";
	    
	    if (editedPost.isDeleted())
	    	return "Can't edit a deleted post";
	    
	    // Safe authorization check (handles null authors)
	    if (!java.util.Objects.equals(editedPost.getAuthor(), author))
	        return "Can't edit other's user post";
	    
	    // Attempt to set title and content (setTitle/setContent return error strings or "")
	    String setTitleErrorMessage = editedPost.setTitle(title);
	    String setContentErrorMessage = editedPost.setContent(content);

	    boolean titleError = setTitleErrorMessage != null && !setTitleErrorMessage.isBlank();
	    boolean contentError = setContentErrorMessage != null && !setContentErrorMessage.isBlank();

	    if (titleError && contentError) return "Title Content could not be empty";
	    if (titleError) return "Title could not be empty";
	    if (contentError) return "Content could not be empty";

	    return "";
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
	public static String editPost (int id, String thread, String author, String title, String content) {
	    // Check existence
	    Post editedPost = postStore.retrieve(id);
	    if (editedPost == null) return "Post doesn't exist";

	    // Safe authorization check (handles null authors)
	    if (!java.util.Objects.equals(editedPost.getAuthor(), author))
	        return "Can't edit other's user post";

	    // Attempt to set title and content (setTitle/setContent return error strings or "")
	    String setThreadErrorMessage = editedPost.setThread(thread);
	    String setTitleErrorMessage = editedPost.setTitle(title);
	    String setContentErrorMessage = editedPost.setContent(content);

	    boolean threadError = setThreadErrorMessage != null && !setThreadErrorMessage.isBlank();
	    boolean titleError = setTitleErrorMessage != null && !setTitleErrorMessage.isBlank();
	    boolean contentError = setContentErrorMessage != null && !setContentErrorMessage.isBlank();

	    //TODO: return wrong error if the title or content exceed limited characters
	    if (threadError) return setThreadErrorMessage;
	    if (titleError && contentError) return "Title Content could not be empty";
	    if (titleError) return "Title could not be empty";
	    if (contentError) return "Content could not be empty";

	    return "";
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

	    // Validate
	    if (content == null || content.isBlank()) {
	        return "Content could not be empty";
	    }

	    Post parentPost = postStore.retrieve(parentId);

	    if (parentPost == null) {
	        return "Parent post not found";
	    }

	    if (parentPost.isDeleted()) {
	        return "Cannot reply to a deleted post";
	    }

	    int id = replyStore.getMaxId()+1;
	    
//	    System.out.println(id);

	    // Create + save reply
	    Reply newReply = new Reply(id, content, author, parentId);
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
		if (deletedReply == null) return "Post doesn't exist";
		
		// Check authorization to delete
		if (!deletedReply.getAuthor().equals(author)) return "Can't delete other's user post";
		
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
		String errorMessage = "";
		
		// Checking if post exist
		Reply editedReply = replyStore.retrieve(id);
		if (editedReply == null) return "Post doesn't exist";
		
		// Check authorization to delete
		if (!editedReply.getAuthor().equals(author)) return "Can't edit other's user post";
		
		String setContentErrorMessage = editedReply.setContent(content);
		
		return errorMessage + setContentErrorMessage;
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
	    	System.out.println(replyId);
	        Reply r = replyStore.retrieve(replyId); // assuming retrieve(id) exists
	        if (r != null) {
	            replies.add(r);
	        }
	    }

	    return replies;
	}
	
	/**
	 * Filters the list of posts using the provided keyword.
	 *
	 * @param keyword the keyword used to filter posts
	 * @return the filtered list of matching posts
	 */
	public static List<Post> filterPosts(String keyword) {
	    return postStore.filterPosts(keyword); // uses your PostStore filter
	}

	/**
	 * Clears the current post filter so that all posts are included again.
	 */
	public static void clearPostFilter() {
	    postStore.clearFilter();
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
	 * <p>Read all threads in the database</p>
	 * @return a String list that contains all thread's name in the database
	 */
	public static ArrayList<String> getAllThreads() {
		return threadStore.getAllThreads();
	}
	
	public static PostStore getPostStore() {
		return postStore;
	}
	
	public static ReplyStore getReplyStore() {
		return replyStore;
	}
	
	public static ThreadStore getThreadStore() {
		return threadStore;
	}
	
	public static void hardReset() {
		replyStore.hardReset();
		postStore.hardReset();
		threadStore.hardReset();
	}
	
	public static void setUpDefaultForum() {
		// Sample THREADS
	    addThread("General");
	    addThread("Lectures");
	    addThread("Sections");
	    addThread("Problem Sets");
	    addThread("Assignments");
	    addThread("Social");
	    
	    // Sample POSTS
	    addPost("Welcome", "Welcome to the CSE 360 discussion forum!", "Admin");

	    addPost("Java OOP Question",
	            "Can someone explain inheritance vs composition?",
	            "Alice");

	    addPost("Binary Search Confusion",
	            "Why is binary search O(log n)?",
	            "Bob");

	    addPost("Database Normalization",
	            "What is 3NF and why is it important?",
	            "Charlie");

	    addPost("Git Merge Conflict",
	            "How do you resolve a merge conflict safely?",
	            "David");

	    addPost("Recursion Depth",
	            "Why do I get StackOverflowError in Java?",
	            "Emma");

	    // Sample REPLIES

	    addReply("Inheritance models an 'is-a' relationship.", "Admin", 1);
	    addReply("Composition is usually more flexible.", "Alice", 1);

	    addReply("Each step halves the search space.", "Charlie", 2);
	    addReply("That’s why it grows logarithmically.", "Admin", 2);

	    addReply("3NF removes transitive dependency.", "Emma", 3);

	    addReply("Always pull before pushing changes.", "Bob", 4);
	    addReply("Use git status to inspect conflicts.", "Admin", 4);

	    addReply("Infinite recursion without base case causes it.", "Alice", 5);
	}
}
