package guiForum;

/**
 * Provides controller actions for the forum user interface.
 *
 * <p>This class contains event-handling methods for actions such as logging out
 * of the forum view and terminating the application.</p>
 */
public class ControllerForum {
	/**
	 * Creates a forum controller.
	 */
	public ControllerForum(){
	}
	
	/**
	 * Logs the current user out of the forum and returns to the login screen.
	 */
	protected static void performLogout() {
		// TODO: fix this later
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewerForum.theStage);
	}
	
	/**
	 * Terminates execution of the application.
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}