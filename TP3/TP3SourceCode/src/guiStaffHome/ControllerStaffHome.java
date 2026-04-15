package guiStaffHome;

import guiStudentHome.ViewStudentHome;

/*******
 * <p> Title: ControllerStaffHome Class. </p>
 * 
 * <p> Description: The Java/FX-based Role 2 Home Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page is a stub for establish future roles for the application.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 */

public class ControllerStaffHome {
	
	/**
	 * Default constructor is not used.
	 */
	public ControllerStaffHome() {
	}

	/**********
	 * <p> Method: performUpdate() </p>
	 * 
	 * <p> Description: This method directs the user to the User Update Page so the user can change
	 * the user account attributes. </p>
	 * 
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewStaffHome.theStage, ViewStaffHome.theUser);
	}	

	protected static void performForumManagement() {
		guiForumManagement.ViewerForumManagement.displayForumManagement(ViewStaffHome.theStage, ViewStaffHome.theUser);
	}
	
	public static void performReturn() {
		guiStaffHome.ViewStaffHome.displayStaffHome(ViewStaffHome.theStage, ViewStaffHome.theUser);
	}
	
	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewStaffHome.theStage);
	}
	
	/**********
	 * <p> Method: performForum() </p>
	 * 
	 * <p> Description: This method move user to the Forum page.</p>
	 * 
	 */
	protected static void performForum() {
		guiForum.ViewerForum.displayViewerForum(ViewStaffHome.theStage, ViewStaffHome.theUser);
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
