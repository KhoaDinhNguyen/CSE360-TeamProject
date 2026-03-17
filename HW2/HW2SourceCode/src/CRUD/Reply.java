package CRUD;
import java.time.LocalDateTime;

public class Reply {
	private final int id;

    private String content;
    private final String author;
    private final LocalDateTime createdAt;
    private static int parentPostId;
	
    public Reply(int id, String content, String author, int parentPostId) {
    	this.id = id;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.parentPostId = parentPostId;
    }
    
    // Getter Function
    public int getId() {
        return id;
    }
    
    public int getParentPostId() {
    	return parentPostId;
    }



    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
   
    public String setContent(String content) {
    	String errorMessage = "Content could not be empty";
    	if (content.isBlank() || content == null) return errorMessage;
    	this.content = content;
    	return "";
    }
}