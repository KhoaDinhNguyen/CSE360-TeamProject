package entityClasses;

public class InvitationCode {
	private String code;
	private String emailAddress;
	private String role;
	
	public InvitationCode(String code, String emailAddress, String role) {
		this.code = code;
		this.emailAddress = emailAddress;
		this.role = role;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void role(String role) {
		this.role = role;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public String getRole() {
		return this.role;
	}
}