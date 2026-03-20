package CRUD;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {
	private final int id;
    private String title;
    private String content;
    private String author;
    private final LocalDateTime createdAt;
    private ArrayList<Integer> replyPostId;

    // A list of user who had read the post
    private ArrayList<String> readUsers;
	
    public Post(int id, String title, String content, String author) {
    	this.id = id;
    	this.title = title;
    	this.content = content;
    	this.author = author;
    	this.createdAt = LocalDateTime.now();
    	this.replyPostId = new ArrayList<>();
    	
    	this.readUsers = new ArrayList<String>();
    	readUsers.add(this.author); // the author who created the class is consider already read it
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
    
    /**
     * This function return a list of usernames who had read the post
     * @return A list of string contain the usernames
     */
    public ArrayList<String> getReadUsers() {
    	return this.readUsers;
    }
    
    /**
     * This function report whether or not the given user have read the post
     * @param user The username of the user 
     * @return true if the user have read the post, otherwise return false
     */
    public boolean hasRead(String user) {
    	return readUsers.contains(user);
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
    
    /**
     * This function mark the given user as read, return true or false if successful or not
     * @param user a string which is the user name of the user
     * @return true if successfully mark as read, otherwise return false
     */
    public boolean markAsRead(String user) {
    	// if the user have read the post, no need to mark as read
    	if (hasRead(user)) {
    		return false;
    	}
    	
    	readUsers.add(user);
    	return true;
    }
    
    @Override
    public String toString() {
    	return title;
    }
}