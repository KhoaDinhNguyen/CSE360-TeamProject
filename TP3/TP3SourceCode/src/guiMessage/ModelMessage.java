package guiMessage;

import entityClasses.MessageStore;
import entityClasses.UserStore;

class ModelMessage {
	public static MessageStore theMessageStore = new MessageStore();
	private static UserStore theUserStore = applicationMain.FoundationsMain.theUserStore; 
	
	static {
		theMessageStore.addMessage("Hello", "Welcome to the class", "staff", "student");
		theMessageStore.addMessage("Hello, Sir", "Hello" , "student", "staff");
	}
	
	protected static String addMessage(String content, String sender, String receiver) {
		String senderMessgage = theUserStore.isUserStaffAndStudent(sender);
		String receiverMessage = theUserStore.isUserStaffAndStudent(receiver);
		
		if (!senderMessgage.isEmpty()) return "Invalid sender: " + senderMessgage;
		if (!receiverMessage.isEmpty()) return "Invalid receiver: " + receiverMessage;
		if (content == null) return "Content cannot be null";
		if (content.isBlank()) return "Content cannot be empty";
		
		theMessageStore.addMessage("", content, sender, receiver);
		
		return "";
	}
}