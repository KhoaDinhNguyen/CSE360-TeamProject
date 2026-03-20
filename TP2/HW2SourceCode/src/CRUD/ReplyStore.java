package CRUD;
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
	
	/**
	 * Creates an empty reply store.
	 */
	public ReplyStore() {
	    this.ReplyList = new ArrayList<>();
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
	
}