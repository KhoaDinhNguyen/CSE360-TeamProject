package CRUD;
import java.util.ArrayList;

// ArrayList is used to store the data because it supports dynamic resizing, simplifies memory management, and provides fast random access to elements.

public class PostStore {
	
	// ArrayList
	private ArrayList<Post> PostList;
	private ArrayList<Post> subsetPostList;
	
	public PostStore() {
	    this.PostList = new ArrayList<>();
	    this.subsetPostList = new ArrayList<>();
	    this.subsetPostList.addAll(this.PostList);
	}
	
	public void addPost(Post newPost) {
		PostList.add(newPost);
	}
	
	public ArrayList<Post> getPostList() {
		return PostList;
	}
	
	public Post retrieve(int id) {
		for (int index = 0; index < this.PostList.size(); index++) {
			Post currentPost = PostList.get(index);
			if (currentPost.getId() == id) return currentPost;
		}
		return null;
	}
	 
	public void deletePost(Post deletedPost) {
		// Checking if post exist
		this.PostList.remove(deletedPost);
	}
	
	public ArrayList<Post> getSubsetPostList() {
	    return subsetPostList;
	}
	
	public int getMaxId() {

	    int max = -1;

	    for (Post p : PostList) {
	        if (p.getId() > max) {
	            max = p.getId();
	        }
	    }

	    return max;
	}
	
	public ArrayList<Post> filterPosts(String keyword) {
        subsetPostList = new ArrayList<>();

        if (keyword == null) return subsetPostList;

        String key = keyword.trim().toLowerCase();

        // If empty keyword => show all posts
        if (key.isEmpty()) {
            subsetPostList.addAll(PostList);
            return subsetPostList;
        }

        for (Post p : PostList) {
            String title = safeLower(p.getTitle());
            String content = safeLower(p.getContent());
            String author = safeLower(p.getAuthor());

            if (title.contains(key) || content.contains(key) || author.contains(key)) {
                subsetPostList.add(p);
            }
        }

        return subsetPostList;
    }

    /** Clears filtering and makes subsetPostList equal to all posts. */
    public void clearFilter() {
        subsetPostList = new ArrayList<>();
        subsetPostList.addAll(PostList);
    }

    private String safeLower(String s) {
        return (s == null) ? "" : s.toLowerCase();
    }
    
    public void hardReset() {
    	PostList.clear();
    	subsetPostList.clear();
    }
}