package guiForum;


/**
 * <p> Title: Controller Forum class</p>
 * <p> This is a controller class for forum GUI to display the forum </p>
 */
public class ControllerForum {
	/**
	 * Unused default constructor
	 */
	public ControllerForum(){
	}
	
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewerForum.theStage); // fix this latere
	}
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */	
	protected static void performQuit() {
		System.exit(0);
	}
}