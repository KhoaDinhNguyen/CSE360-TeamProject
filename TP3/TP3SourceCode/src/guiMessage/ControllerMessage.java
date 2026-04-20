package guiMessage;

import guiStaffHome.ViewStaffHome;
import guiStudentHome.ViewStudentHome;

/**
 * {@code ControllerMessage} is the interaction between Model and Viewer class of the message
 */
public class ControllerMessage {
	/**
	 * The constructor of the class but it will not be used
	 */
	public ControllerMessage() {}
	
	/**
	 * Displays direct message view
	 */
	protected static void performDM() {
		ViewerMessage.displayDMView();
	}
	
	/**
	 * Sends message to other user
	 * @param content is message's content
	 * @param sender is username who is message's sender
	 * @param receiver is username who is message's receiver
	 */
	protected static void performSendMessage(String content, String sender, String receiver) {
		ModelMessage.addMessage(content, sender, receiver);
	}
	
	/**
	 * Returns to staff or student homepage 
	 */
	protected static void performReturn() {
		if (ViewerMessage.theUser.getStudent()) ViewStudentHome.displayStudentHome(ViewerMessage.theStage, ViewerMessage.theUser);
		else if(ViewerMessage.theUser.getStaff()) ViewStaffHome.displayStaffHome(ViewerMessage.theStage, ViewerMessage.theUser);
	}
	
	/**
	 * Logs out the application
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewerMessage.theStage);
	}
	
	/**
	 * Quits the application
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}