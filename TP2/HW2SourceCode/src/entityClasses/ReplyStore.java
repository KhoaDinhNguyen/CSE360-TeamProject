package entityClasses;
import java.util.ArrayList;

// Note: ArrayList is used to store the data because it supports dynamic resizing, simplifies memory management, and provides fast random access to elements.

/**
 * Stores and manages forum replies in memory.
 *
 * <p>This class provides basic operations for adding, retrieving, removing,
 * and scanning reply objects.</p>
 */
public class ReplyStore {
	
	// ArrayList
	private ArrayList<Reply> ReplyList;
	private ArrayList<Reply> subsetReplyList;
	
	/**
	 * Creates an empty reply store.
	 */
	public ReplyStore() {
	    this.ReplyList = new ArrayList<>();
	    this.subsetReplyList = new ArrayList<>();
	}
	
	/**
	 * Adds a new reply to the store.
	 *
	 * @param newReply the reply to add
	 */
	public void add(Reply newReply) {
		this.ReplyList.add(newReply);
	}
	
	/**
	 * Returns the complete list of replies currently stored.
	 *
	 * @return the full reply list
	 */
	public ArrayList<Reply> getReplyList() {
		return ReplyList;
	}
	
	/**
	 * Retrieves a reply by its unique identifier.
	 *
	 * @param id the ID of the reply to retrieve
	 * @return the matching reply if found; otherwise {@code null}
	 */
	public Reply retrieve(int id) {
		for (int index = 0; index < this.ReplyList.size(); index++) {
			Reply currentReply = ReplyList.get(index);
			if (currentReply.getId() == id) return currentReply;
		}
		return null;
	}
	 
	/**
	 * Removes a reply from the store.
	 *
	 * @param deletedReply the reply to remove
	 */
	public void remove(Reply deletedReply) {
		this.ReplyList.remove(deletedReply);
	}
	
	/**
	 * Returns the largest reply ID currently in the store.
	 *
	 * @return the maximum reply ID, or {@code -1} if the store is empty
	 */
	public int getMaxId() {

	    int max = -1;

	    for (Reply r : ReplyList) {
	        if (r.getId() > max) {
	            max = r.getId();
	        }
	    }

	    return max;
	}
	
	/**
	 * Return an subset contains the replies which the user have not read
	 * @param user a string contains the username
	 * @return an array list of reply class
	 */
	public ArrayList<Reply> getUnreadReplies(String user) {
		subsetReplyList.clear();
		
		for (int i = 0; i < ReplyList.size(); i++) {
			Reply reply = ReplyList.get(i);
			if (!reply.hadRead(user))
				subsetReplyList.add(reply);
		}
		
		return subsetReplyList;
	}
	/**
	 * Return an subset contains the replies which the user have not read in a post
	 * 
	 * @param user a string contains the username
	 * @param postId the id of the parent post
	 * @return an array list of reply class
	 */
	public ArrayList<Reply> getUnreadReplies(String user, int postId) {
		subsetReplyList.clear();
		
		for (int i = 0; i < ReplyList.size(); i++) {
			Reply reply = ReplyList.get(i);
			if (reply.getParentPostId() == postId && !reply.hadRead(user))
				subsetReplyList.add(reply);
		}
		
		return subsetReplyList;
	}
	
	/** 
	 * Reset the array lists
	 */
	 public void hardReset() { 
		 ReplyList.clear();
		 subsetReplyList.clear();
	 }
}