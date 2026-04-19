package entityClasses;

import java.util.ArrayList;

public class MessageStore {
	private ArrayList<Message> messageList;
	
	public MessageStore() {
		this.messageList = new ArrayList<>();
	}
	
	public void addMessage(String title, String content, String sender, String receiver) {
		Message m = new Message.Builder()
				.sender(sender)
				.receiver(receiver)
				.content(content)
				.title(title)
				.build();
		
		messageList.add(m);
	}
	
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
	
	public ArrayList<Message> getMessagesByUsers(String user) {
		ArrayList<Message> mList = new ArrayList<>();
		
		for (int i = 0; i < messageList.size(); ++i) {
			Message m = messageList.get(i);
			String r = m.getReceiver(), s = m.getSender();
			
			if (r.equals(user) || s.equals(user)) {
				mList.add(m);
			}
		}
		
		return mList;
	}
	
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