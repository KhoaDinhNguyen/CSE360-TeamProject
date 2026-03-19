package CRUD;
import java.util.ArrayList;

// ArrayList is used to store the data because it supports dynamic resizing, simplifies memory management, and provides fast random access to elements.

public class ReplyStore {
	
	// ArrayList
	private ArrayList<Reply> ReplyList;
	
	public ReplyStore() {
	    this.ReplyList = new ArrayList<>();
	}
	
	public void add(Reply newReply) {
		this.ReplyList.add(newReply);
	}
	
	public ArrayList<Reply> getReplyList() {
		return ReplyList;
	}
	
	public Reply retrieve(int id) {
		for (int index = 0; index < this.ReplyList.size(); index++) {
			Reply currentReply = ReplyList.get(index);
			if (currentReply.getId() == id) return currentReply;
		}
		return null;
	}
	 
	public void remove(Reply deletedReply) {
		this.ReplyList.remove(deletedReply);
	}
	
	public int getMaxId() {

	    int max = -1;

	    for (Reply r : ReplyList) {
	        if (r.getId() > max) {
	            max = r.getId();
	        }
	    }

	    return max;
	}
	
	 public void hardReset() {
		 ReplyList.clear();
	 }
}