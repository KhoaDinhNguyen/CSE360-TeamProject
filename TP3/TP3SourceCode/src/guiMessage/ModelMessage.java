package guiMessage;

import entityClasses.MessageStore;

class ModelMessage {
	public static MessageStore theMessageStore = new MessageStore();
	
	static {
		theMessageStore.addMessage("Hello", "Welcome to the class", "staff", "student");
		theMessageStore.addMessage("Hello, Sir", "Hello" , "student", "staff");
	}
	
	protected static String addMessage(String content, String sender, String receiver) {
		theMessageStore.addMessage("", content, sender, receiver);
		
		return "";
	}
}