package CRUD;
import java.time.LocalDateTime;

/**
 * Represents a reply to a forum post.
 *
 * <p>A reply stores its unique identifier, content, author, creation time,
 * and the identifier of the parent post it belongs to.</p>
 */
public class Reply {
	private final int id;

    private String content;
    private final String author;
    private final LocalDateTime createdAt;
    private int parentPostId;
	
    /**
     * Creates a new reply with the given identifier, content, author, and parent post ID.
     *
     * @param id the unique identifier for the reply
     * @param content the content of the reply
     * @param author the username of the reply author
     * @param parentPostId the identifier of the post this reply belongs to
     */
    public Reply(int id, String content, String author, int parentPostId) {
    	this.id = id;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.parentPostId = parentPostId;
    }
    
    /**
     * Returns the unique identifier of the reply.
     *
     * @return the reply ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Returns the identifier of the parent post associated with this reply.
     *
     * @return the parent post ID
     */
    public int getParentPostId() {
    	return parentPostId;
    }

    /**
     * Returns the content of the reply.
     *
     * @return the reply content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the author of the reply.
     *
     * @return the reply author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the time at which the reply was created.
     *
     * @return the creation timestamp of the reply
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Updates the reply content if the provided content is valid.
     *
     * @param content the new content for the reply
     * @return an empty string if the update succeeds; otherwise, an error message
     */
    public String setContent(String content) {
    	String errorMessage = "Content could not be empty";
    	if (content.isBlank() || content == null) return errorMessage;
    	this.content = content;
    	return "";
    }
    
    /**
     * <p>Defines the comparison of two replies</p>
     * 
     * @return a boolean value is true if two replies are the same, otherwise, return false.
     */
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	
    	Reply otherPost = (Reply)o;
    	
    	return java.util.Objects.equals(this.author, otherPost.author)
                && java.util.Objects.equals(this.content, otherPost.content)
                && java.util.Objects.equals(this.parentPostId, otherPost.parentPostId);
    }
    
    /**
     * <p>Defines the hash value of the reply</p>
     * 
     * @return a integer represents the hash code of the reply 
     */
    @Override
    public int hashCode() {
    	return java.util.Objects.hash(this.id, this.author);
    }
    
}