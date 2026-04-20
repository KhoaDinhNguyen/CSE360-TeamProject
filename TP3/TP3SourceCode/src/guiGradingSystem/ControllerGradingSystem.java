package guiGradingSystem;

/**
 * Provides controller actions for the forum user interface.
 *
 * <p>This class contains event-handling methods for actions such as logging out
 * of the forum view and terminating the application.</p>
 */
public class ControllerGradingSystem {
	/**
	 * Creates a forum controller.
	 */
	public ControllerGradingSystem(){
	}
	
	/**
	 * <p>Directs user to add post window</p>
	 */
	protected static void performNewAssignment() {
		ViewGradingSystem.showNewAssignmentWindow();
	}
	
	/**
	 * Delete Assignment 
	 */
	protected static void performDelete() {
		ViewGradingSystem.showDeleteWindow();
	}
	
	/**
	 * Logs the current user out of the forum and returns to the login screen.
	 */
	protected static void performLogout() {
		// TODO: fix this later
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewGradingSystem.theStage);
	}
	
	/**
	 * Terminates execution of the application.
	 */
	protected static void performQuit() {
		System.exit(0);
	}
	
	/**
	 * This function return the user to the home page
	 */
	protected static void performReturn() {
		if (ViewGradingSystem.theRole == 2) {
			guiStudentHome.ViewStudentHome.displayStudentHome(ViewGradingSystem.theStage, ViewGradingSystem.theUser);
		}
		else {
			guiStaffHome.ViewStaffHome.displayStaffHome(ViewGradingSystem.theStage, ViewGradingSystem.theUser);
		}
		
	}
}