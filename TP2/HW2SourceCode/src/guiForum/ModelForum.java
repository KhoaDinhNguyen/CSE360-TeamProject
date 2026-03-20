package guiForum;

import java.util.ArrayList;
import java.util.List;

import CRUD.Post;
import CRUD.Reply;

public class ModelForum {

/*******
 * <p> Title: ModelRole1Home Class. </p>
 * 
 * <p> Description: The Role1Home Page Model.  This class is a stub for future expansion.
 * 
 * This class is not used as there is no unique data manipulation for this GUI page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-15 Initial version
 * @version 1.01		2025-09-13 Updated JavaDoc description
 *  
 */
	
	
	
	private static CRUD.PostStore postStore = new CRUD.PostStore();
	private static CRUD.ReplyStore replyStore = new CRUD.ReplyStore();
	
	
	
	static {

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
	
	// Post Action
	
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
	
	public static List<Post> getPostList() {
		return postStore.getPostList();
	}
	
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
	
	// Reply Action
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

	    System.out.println(id);

	    // Create + save reply
	    Reply newReply = new Reply(id, content, author, parentId);
	    replyStore.add(newReply);

	    // Link reply id to parent post 
	    parentPost.addReplyId(id);


	    return ""; // 
	}
	
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
	
	// Filter
	
	public static List<Post> filterPosts(String keyword) {
	    return postStore.filterPosts(keyword); // uses your PostStore filter
	}

	/** Clears the filter so the "subset" becomes the full list again. */
	public static void clearPostFilter() {
	    postStore.clearFilter();
	}

	/** If your GUI wants to directly read the current filtered list. */
	public static List<Post> getFilteredPostList() {
	    return postStore.getSubsetPostList();
	}
}
