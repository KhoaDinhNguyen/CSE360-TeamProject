package CRUD;
import java.util.ArrayList;

// Note: ArrayList is used to store the data because it supports dynamic resizing, simplifies memory management, and provides fast random access to elements.

/**
 * Stores and manages forum posts in memory.
 *
 * <p>This class maintains the complete list of posts as well as a filtered subset
 * used by search operations in the forum interface.</p>
 */
public class PostStore {
	
	// ArrayList
	private ArrayList<Post> PostList;
	private ArrayList<Post> subsetPostList;
	
	/**
	 * Creates an empty post store.
	 */
	public PostStore() {
	    this.PostList = new ArrayList<>();
	    this.subsetPostList = new ArrayList<>();
	    this.subsetPostList.addAll(this.PostList);
	}
	
	/**
	 * Adds a new post to the store.
	 *
	 * @param newPost the post to add
	 */
	public void addPost(Post newPost) {
		PostList.add(newPost);
	}
	
	/**
	 * Returns the complete list of posts currently stored.
	 *
	 * @return the full post list
	 */
	public ArrayList<Post> getPostList() {
		return PostList;
	}
	
	/**
	 * Retrieves a post by its unique identifier.
	 *
	 * @param id the ID of the post to retrieve
	 * @return the matching post if found; otherwise {@code null}
	 */
	public Post retrieve(int id) {
		for (int index = 0; index < this.PostList.size(); index++) {
			Post currentPost = PostList.get(index);
			if (currentPost.getId() == id) return currentPost;
		}
		return null;
	}
	 
	/**
	 * Removes a post from the store.
	 *
	 * @param deletedPost the post to remove
	 */
	public void deletePost(Post deletedPost) {
		// Checking if post exist
		this.PostList.remove(deletedPost);
	}
	
	/**
	 * Returns the current filtered subset of posts.
	 *
	 * @return the filtered post list
	 */
	public ArrayList<Post> getSubsetPostList() {
	    return subsetPostList;
	}
	
	/**
	 * Returns the largest post ID currently in the store.
	 *
	 * @return the maximum post ID, or {@code -1} if the store is empty
	 */
	public int getMaxId() {

	    int max = -1;

	    for (Post p : PostList) {
	        if (p.getId() > max) {
	            max = p.getId();
	        }
	    }

	    return max;
	}
	
	/**
	 * Filters posts by checking whether the given keyword appears in the title,
	 * content, or author of each post.
	 *
	 * @param keyword the keyword used for filtering
	 * @return the filtered list of matching posts
	 */
	public ArrayList<Post> filterPosts(String keyword, String threadTag) {
        subsetPostList = new ArrayList<>();

        if (keyword == null) return subsetPostList;

        String key = keyword.trim().toLowerCase();
        
        // If empty keyword => show all posts
        if (key.isEmpty() && threadTag == "Default") {
            subsetPostList.addAll(PostList);
            return subsetPostList;
        }
        
        for (Post p : PostList) {
            String title = safeLower(p.getTitle());
            String content = safeLower(p.getContent());
            String author = safeLower(p.getAuthor());
            String thread = p.getThread();
            boolean matchesCriteria = true;
            
            // Filtering base on Thread and Keyword
            if (!(key.isEmpty())) {matchesCriteria = title.contains(key) || content.contains(key) || author.contains(key);}
            if (threadTag != "Default") {matchesCriteria = matchesCriteria && thread.equals(threadTag);}
            
            if (matchesCriteria) {
                subsetPostList.add(p);
            }
        }

        return subsetPostList;
    }

	/**
	 * Clears any active filtering and resets the subset to include all posts.
	 */
    public void clearFilter() {
        subsetPostList = new ArrayList<>();
        subsetPostList.addAll(PostList);
    }

    /**
     * Safely converts a string to lowercase.
     *
     * @param s the string to convert
     * @return the lowercase version of the string, or an empty string if the input is {@code null}
     */
    private String safeLower(String s) {
        return (s == null) ? "" : s.toLowerCase();
    }
    
    /**
     * Get an subset list of unread posts given the user name
     * @param user a string contain the user name
     * @return an ArrayList of Post class
     */
    public ArrayList<Post> getUnreadPosts(String user) {
    	subsetPostList.clear();
    	for (int i = 0; i < PostList.size(); i++) {
    		Post post = PostList.get(i);
    		if (!post.hasRead(user) ) {
    			subsetPostList.add(post);
    		}
    	}
    	
    	return subsetPostList;
    }
    
    public void hardReset() {
    	PostList.clear();
    	subsetPostList.clear();
    }
}