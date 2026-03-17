package CRUD;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {
	private final int id;
    private String title;
    private String content;
    private final String author;
    private final LocalDateTime createdAt;
    private ArrayList<Integer> replyPostId;
	
    public Post(int id, String title, String content, String author) {
    	this.id = id;
    	this.title = title;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.replyPostId = new ArrayList<>();
    }
    
    // Getter Function
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
    
    public ArrayList<Integer> getReplyPostId(){
    	return this.replyPostId;
    }
    
    // Setter Function
 // In CRUD.Post class
    public String setTitle(String title) {
        if (title == null || title.isBlank()) {
            return "Title could not be empty";
        }
        this.title = title;
        return "";
    }

    public String setContent(String content) {
        if (content == null || content.isBlank()) {
            return "Content could not be empty";
        }
        this.content = content;
        return "";
    }
    
    public void addReplyId(int replyId) {
    	this.replyPostId.add(replyId);
    }
    
    @Override
    public String toString() {
    	return title;
    }
}