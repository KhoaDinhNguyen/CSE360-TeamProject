package guiMessage;

import entityClasses.MessageStore;
import entityClasses.UserStore;

/**
 * {@code ModelMessage} is responsible for database operation related to messages
 */
public class ModelMessage {
	/**
	 * {@code theMessageStore} is the temporary message storage in run time
	 */
	protected static MessageStore theMessageStore = new MessageStore();
	private static UserStore theUserStore = applicationMain.FoundationsMain.theUserStore; 
	
	/**
	 * The constructor of the class but it will not be used in this project
	 */
	public ModelMessage() {}
	
	static {
		theMessageStore.addMessage("Welcome to the class", "staff", "student");
		theMessageStore.addMessage("Hello" , "student", "staff");
	}
	
	/**
	 * Creates new message into the database
	 * @param content is a message's content
	 * @param sender is a username who is message's sender
	 * @param receiver is a username who is message's receiver
	 * @return a String represents error message, empty if there is no message
	 */
	protected static String addMessage(String content, String sender, String receiver) {
		String senderMessgage = theUserStore.isUserStaffAndStudent(sender);
		String receiverMessage = theUserStore.isUserStaffAndStudent(receiver);
		
		if (!senderMessgage.isEmpty()) return "Invalid sender: " + senderMessgage;
		if (!receiverMessage.isEmpty()) return "Invalid receiver: " + receiverMessage;
		if (content == null) return "Content cannot be null";
		if (content.isBlank()) return "Content cannot be empty";
		
		theMessageStore.addMessage(content, sender, receiver);
		
		return "";
	}
}