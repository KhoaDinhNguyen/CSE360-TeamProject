package entityClasses;

/**
 * {@code Message} is used to display the information of one message from DM feature
 */
public class Message {
	private String receiver;
	private String sender;
	private String content;
		
	/**
	 * A constructor of the class which is built from the builder
	 * @param builder is a Builder object
	 */
	private Message (Builder builder) {
		this.receiver = builder.receiver;
		this.sender = builder.sender;
		this.content = builder.content;
	}
	
	/**
	 * {@code Builder} is used to build message attribute
	 */
	public static class Builder {
		private String receiver;
		private String sender;
		private String content;
		
		/**
		 * A constructor of the builder
		 */
		public Builder() { }
		
		/**
		 * Build receiver
		 * @param receiver is a String represents username that will receive the message
		 * @return a Builder object
		 */
		public Builder receiver(String receiver) { this.receiver = receiver; return this; }
		/**
		 * Build sender
		 * @param sender is a String represents username who send the message
		 * @return a Builder object
		 */
		public Builder sender(String sender) { this.sender = sender; return this; }
		
		/**
		 * Build content
		 * @param content is  String represents the content of message
		 * @return a Builder object
		 */
		public Builder content(String content) { this.content = content; return this; }
	
		/**
		 * Build a new message
		 * @return a Message object
		 */
		public Message build() { return new Message(this); }
	}
	
	/**
	 * Get the receiver
	 * @return a String that represents username
	 */
	public String getReceiver() { return this.receiver; }
	
	/**
	 * Get the sender
	 * @return a String that represents username
	 */
	public String getSender() { return this.sender; }
	
	/**
	 * Get the content
	 * @return a String
	 */
	public String getContent() { return this.content; }
}