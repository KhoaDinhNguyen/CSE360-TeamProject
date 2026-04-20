package entityClasses;

public class Message {
	private String receiver;
	private String sender;
	private String content;
	private String title;
		
	private Message (Builder builder) {
		this.receiver = builder.receiver;
		this.sender = builder.sender;
		this.content = builder.content;
		this.title = builder.title;
	}
		
	public static class Builder {
		private String receiver;
		private String sender;
		private String content;
		private String title;
		
		public Builder() { }
		
		public Builder receiver(String receiver) { this.receiver = receiver; return this; }
		public Builder sender(String sender) { this.sender = sender; return this; }
		public Builder content(String content) { this.content = content; return this; }
		public Builder title(String title) { this.title = title; return this; }
	
		public Message build() { return new Message(this); }
	}
	
	public String getReceiver() { return this.receiver; }
	public String getSender() { return this.sender; }
	public String getContent() { return this.content; }
	public String getTitle() { return this.title; }
}