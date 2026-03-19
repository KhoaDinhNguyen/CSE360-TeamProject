package CRUD;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import entityClasses.ThreadStore;

public class Post {
	private final int id;
    private String title;
    private String content;
    private String thread;
    private final String author;
    private final LocalDateTime createdAt;
    private ArrayList<Integer> replyPostId;
	
    // TODO: Remove Post(id, title, content, author) if Post(id, thread, title, content, author) works
    public Post(int id, String title, String content, String author) {
    	this.id = id;
    	this.thread = "General";
    	this.title = title;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.replyPostId = new ArrayList<>();
    }
    
    public Post(int id, String thread, String title, String content, String author) {
    	this.id = id;
    	this.thread = thread == null? "General": thread;
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

    public String getThread() {
    	return thread;
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
    public String setThread(String thread) {
    	if (thread == null || thread.isBlank()) {
    		return "Thread could not be empty";
    	}
    	else if (thread.length() > 100) {
    		return "Thread name could not be longer than 100 characters";
    	}
    	
    	this.thread = thread;
    	
    	return "";
    }
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
    
    @Override
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (o == null || getClass() != o.getClass()) return false;
    	
    	Post otherPost = (Post)o;
    	
    	ArrayList<Boolean> checkConditions = new ArrayList<>(Arrays.asList(
    			this.thread == otherPost.thread, 
    			this.author == otherPost.author,
    			this.title == otherPost.title,
    			this.content == otherPost.content
    			));
    	
    	return !checkConditions.contains(false);
    }
    
    @Override
    public int hashCode() {
    	return java.util.Objects.hash(this.id, this.author);
    }
}