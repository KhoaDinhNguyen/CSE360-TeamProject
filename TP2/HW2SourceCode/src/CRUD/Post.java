package CRUD;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a forum post.
 *
 * <p>A post stores its unique identifier, title, content, author, creation time,
 * the identifiers of any replies attached to it, and whether it has been soft deleted.</p>
 */
public class Post {
	private final int id;
    private String title;
    private String content;
    private String author;
    private final LocalDateTime createdAt;
    private ArrayList<Integer> replyPostId;
    private boolean deleted;
	
    /**
     * Creates a new post with the given identifier, title, content, and author.
     *
     * @param id the unique identifier for the post
     * @param title the title of the post
     * @param content the body content of the post
     * @param author the username of the author who created the post
     */
    public Post(int id, String title, String content, String author) {
    	this.id = id;
    	this.title = title;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.replyPostId = new ArrayList<>();
    	this.deleted = false;
    }
    
    /**
     * Returns the unique identifier of the post.
     *
     * @return the post ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the post.
     *
     * @return the post title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the content of the post.
     *
     * @return the post content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the author of the post.
     *
     * @return the author username
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the time at which the post was created.
     *
     * @return the creation timestamp of the post
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Returns the list of reply identifiers associated with this post.
     *
     * @return the list of reply IDs for this post
     */
    public ArrayList<Integer> getReplyPostId() {
    	return this.replyPostId;
    }
    
    /**
     * Indicates whether this post has been soft deleted.
     *
     * @return {@code true} if the post is marked as deleted; {@code false} otherwise
     */
    public boolean isDeleted() {
    	return this.deleted;
    }
    
    /**
     * Soft deletes the post by replacing its visible fields with {@code "[DELETED]"}
     * and marking it as deleted.
     */
    public void softDelete() {
    	this.author = "[DELETED]";
    	this.title = "[DELETED]";
    	this.content = "[DELETED]";
    	this.deleted = true;
    }
    
    /**
     * Updates the title of the post if the provided title is valid.
     *
     * @param title the new title to assign to the post
     * @return an empty string if the title was updated successfully; otherwise,
     *         an error message describing why the update failed
     */
    public String setTitle(String title) {
        if (title == null || title.isBlank()) {
            return "Title could not be empty";
        }
        this.title = title;
        return "";
    }

    /**
     * Updates the content of the post if the provided content is valid.
     *
     * @param content the new content to assign to the post
     * @return an empty string if the content was updated successfully; otherwise,
     *         an error message describing why the update failed
     */
    public String setContent(String content) {
        if (content == null || content.isBlank()) {
            return "Content could not be empty";
        }
        this.content = content;
        return "";
    }
    
    /**
     * Adds the identifier of a reply associated with this post.
     *
     * @param replyId the reply ID to associate with this post
     */
    public void addReplyId(int replyId) {
    	this.replyPostId.add(replyId);
    }
    
    /**
     * Returns the string representation of the post.
     *
     * <p>The title is used so that post objects display clearly in UI controls
     * such as list views.</p>
     *
     * @return the title of the post
     */
    @Override
    public String toString() {
    	return title;
    }
}