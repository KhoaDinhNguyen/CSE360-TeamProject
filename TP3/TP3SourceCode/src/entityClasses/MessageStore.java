package entityClasses;

import java.util.ArrayList;

/**
 * {@code MessageStore} is used to control database operation related to message
 */
public class MessageStore {
	private ArrayList<Message> messageList;
	
	/**
	 * A constructor of the class and initialize empty message list
	 */
	public MessageStore() {
		this.messageList = new ArrayList<>();
	}
	
	/**
	 * Adds new message
	 * @param content is message's content
	 * @param sender is username that represents message's sender
	 * @param receiver is username that represents message's receiver
	 */
	public void addMessage(String content, String sender, String receiver) {
		Message m = new Message.Builder()
				.sender(sender)
				.receiver(receiver)
				.content(content)
				.build();
		
		messageList.add(m);
	}
	
	/**
	 * Gets the conversation of two users
	 * @param user1 is a String that represents one of user in the conversation
	 * @param user2 is a String that represents one of user in the conversation
	 * @return a list of messages
	 */
	public ArrayList<Message> getMessagesByTwoUsers(String user1, String user2) {
		ArrayList<Message> mList = new ArrayList<>();
		
		for (int i = 0; i < messageList.size(); ++i) {
			Message m = messageList.get(i);
			String r = m.getReceiver(), s = m.getSender();
			
			if ((r.equals(user1) && s.equals(user2)) || (s.equals(user1) && r.equals(user2))) {
				mList.add(m);
			}
		}
		
		return mList;
	}
	
	/**
	 * Get all messages whom receiver is given
	 * @param user is a String that represents username
	 * @return a list of messages
	 */
	public ArrayList<Message> getMessagesByReceiverUsers(String user) {
		ArrayList<Message> mList = new ArrayList<>();
		
		for (int i = 0; i < messageList.size(); ++i) {
			Message m = messageList.get(i);
			String r = m.getReceiver();
			
			if (r.equals(user)) {
				mList.add(m);
			}
		}
		
		return mList;
	}
}