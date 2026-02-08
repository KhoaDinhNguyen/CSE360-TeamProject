package guiAdminHome;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;

import database.Database;
/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page.  This class provides the controller actions
 * basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page contains a number of buttons that have not yet been implemented.  WHen those buttons
 * are pressed, an alert pops up to tell the user that the function associated with the button has
 * not been implemented. Also, be aware that What has been implemented may not work the way the
 * final product requires and there maybe defects in this code.
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

public class ControllerAdminHome {
	
	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/
	
	/**
	 * Default constructor is not used.
	 */
	public ControllerAdminHome() {
	}
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/**********
	 * <p> 
	 * 
	 * Title: performInvitation () Method. </p>
	 * 
	 * <p> Description: Protected method to send an email inviting a potential user to establish
	 * an account and a specific role. </p>
	 */
	protected static void performInvitation () {
		// Verify that the email address is valid - If not alert the user and return
		String emailAddress = ViewAdminHome.text_InvitationEmailAddress.getText();
		if (invalidEmailAddress(emailAddress)) {
			return;
		}
		
		// Check to ensure that we are not sending a second message with a new invitation code to
		// the same email address.  
		if (theDatabase.emailaddressHasBeenUsed(emailAddress)) {
			ViewAdminHome.alertEmailError.setContentText(
					"An invitation has already been sent to this email address.");
			ViewAdminHome.alertEmailError.showAndWait();
			return;
		}
		
		// Inform the user that the invitation has been sent and display the invitation code
		String theSelectedRole = (String) ViewAdminHome.combobox_SelectRole.getValue();
		String invitationCode = theDatabase.generateInvitationCode(emailAddress,
				theSelectedRole);
		String msg = "Code: " + invitationCode + " for role " + theSelectedRole + 
				" was sent to: " + emailAddress;
		System.out.println(msg);
		ViewAdminHome.alertEmailSent.setContentText(msg);
		ViewAdminHome.alertEmailSent.showAndWait();
		
		// Update the Admin Home pages status
		ViewAdminHome.text_InvitationEmailAddress.setText("");
		ViewAdminHome.label_NumberOfInvitations.setText("Number of outstanding invitations: " + 
				theDatabase.getNumberOfInvitations());
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: manageInvitations () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void manageInvitations () {
		System.out.println("\n*** WARNING ***: Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.setTitle("*** WARNING ***");
		ViewAdminHome.alertNotImplemented.setHeaderText("Manage Invitations Issue");
		ViewAdminHome.alertNotImplemented.setContentText("Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: setOnetimePassword () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void setOnetimePassword () {
		System.out.println("\n*** WARNING ***: One-Time Password Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.setTitle("*** WARNING ***");
		ViewAdminHome.alertNotImplemented.setHeaderText("One-Time Password Issue");
		ViewAdminHome.alertNotImplemented.setContentText("One-Time Password Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: deleteUser () Method. </p>
	 * 
	 * <p> Description: Protected method that prompts the user to delete a username.</p>
	 */
	protected static void deleteUser() {		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Delete User");
		dialog.setHeaderText("Confirm Deletion");
	    dialog.setContentText("Enter username to delete:");
	    
	    ButtonType deleteBtn = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().setAll(deleteBtn, ButtonType.CANCEL);
	    
	    Optional<String> result = dialog.showAndWait();
	    if (result.isEmpty()) return;
	    
	    String username = result.get().trim();
	    if (username.isEmpty()) {
	        Alert a = new Alert(AlertType.ERROR);
	        a.setTitle("Delete User");
	        a.setHeaderText("Invalid username");
	        a.setContentText("Username cannot be empty.");
	        a.showAndWait();
	        return;
	    }
	    
	    // Prevent deleting oneself when logged in
	    if (username.equals(ViewAdminHome.theUser.getUserName())) {
	    	Alert err = new Alert(AlertType.ERROR);
	    	err.setTitle("Delete User");
	    	err.setHeaderText("Not allowed");
	    	err.setContentText("You cannot delete the account you are currently logged into.");
	        err.showAndWait();
	        return;
	    }
	    
	    // Prevent deleting other admins
	    if (theDatabase.hasAdminRole(username)) {
	    	Alert err = new Alert(AlertType.ERROR);
	    	err.setTitle("Delete User");
	    	err.setHeaderText("Not allowed");
	    	err.setContentText("You cannot delete a user with the Admin role.");
	    	err.showAndWait();
	    	return;
	    }
	    
	    // Let the user confirm user deletion
	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    confirm.setTitle("Delete User");
	    confirm.setHeaderText("Are you sure?");
	    confirm.setContentText("Delete user: " + username + "?");
	    
	    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
	    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
	    confirm.getButtonTypes().setAll(yes, no);
	    
	    Optional<ButtonType> choice = confirm.showAndWait();
	    if (choice.isEmpty() || choice.get() != yes) return;

	    // Perform user deletion
    	boolean success = guiDeleteUser.DeleteUser.deleteUser(username);
    	
    	Alert a = new Alert(success ? AlertType.INFORMATION : AlertType.ERROR);
        a.setTitle("Delete User");
        a.setHeaderText(success ? "Success!" : "Failed");
        a.setContentText(success
            ? ("Deleted user: " + username)
            : ("Could not delete user: " + username));
        a.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: listUsers () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void listUsers() {
		guiListAllUsers.ViewListAllUsers.displayViewListAllUser(ViewAdminHome.theStage, ViewAdminHome.theUser);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: addRemoveRoles () Method. </p>
	 * 
	 * <p> Description: Protected method that allows an admin to add and remove roles for any of
	 * the users currently in the system.  This is done by invoking the AddRemoveRoles Page. There
	 * is no need to specify the home page for the return as this can only be initiated by and
	 * Admin.</p>
	 */
	protected static void addRemoveRoles() {
		guiAddRemoveRoles.ViewAddRemoveRoles.displayAddRemoveRoles(ViewAdminHome.theStage, 
				ViewAdminHome.theUser);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: invalidEmailAddress () Method. </p>
	 * 
	 * <p> Description: Protected method that is intended to check an email address before it is
	 * used to reduce errors.  The code currently only checks to see that the email address is not
	 * empty.  In the future, a syntactic check must be performed and maybe there is a way to check
	 * if a properly email address is active.</p>
	 * 
	 * @param emailAddress	This String holds what is expected to be an email address
	 */
	protected static boolean invalidEmailAddress(String emailAddress) {
		if (emailAddress.length() == 0) {
			ViewAdminHome.alertEmailError.setContentText(
					"Correct the email address and try again.");
			ViewAdminHome.alertEmailError.showAndWait();
			return true;
		}
		return false;
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performLogout () Method. </p>
	 * 
	 * <p> Description: Protected method that logs this user out of the system and returns to the
	 * login page for future use.</p>
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performQuit () Method. </p>
	 * 
	 * <p> Description: Protected method that gracefully terminates the execution of the program.
	 * </p>
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}
