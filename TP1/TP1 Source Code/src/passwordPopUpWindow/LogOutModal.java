package passwordPopUpWindow;


import guiAdminHome.ViewAdminHome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import passwordPopUpWindow.PasswordEvaluationGUITestbed;

/*******
 * <p> Title: View Class - establishes the Graphics User interface, presents information to the
 * user, and accept information from the user.</p>
 *
 * <p> Description: This View class is a major component of a Model View Controller (MVC)
 * application design that provides the user with Graphical User Interface with JavaFX
 * widgets as opposed to a command line interface.
 * 
 * In this case the GUI consists of numerous widgets to show the user where to enter the password,
 * where any errors are located, and a set of requirements for a valid password and whether or not
 * they have been satisfied
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 2.00	2025-07-30 Rewrite of this application for the Fall 2025 offering of CSE 360
 * and other ASU courses.
 */

public class LogOutModal {
	private static String string_ApplicationTitle = new String("Logout");
	
	public static Stage theStage;

	public final static double WINDOW_WIDTH = 400;
	public final static double WINDOW_HEIGHT = 175;
	
	
	
	public static void view () {
		theStage = new Stage();
		
		theStage.setTitle(string_ApplicationTitle);
		
		Pane theRoot = new Pane();
		
		Button logoutButton = new Button();
		Label successMeessage = new Label("Password Updated Successfully");
		Label note = new Label("For your account's protection, you must log out and log in with your new password");
		
		setupLabelWidget(successMeessage, 20, 20, "Arial", 18, WINDOW_WIDTH, Pos.BASELINE_LEFT);
		successMeessage.setFont(Font.font("Arial", FontWeight.BOLD, 18));
	
		setupLabelWidget(note, 20, 50, "Arial", 14, 350, Pos.BASELINE_LEFT);
		note.setMaxWidth(250);
		note.setWrapText(true);

		setupButtonWidget(logoutButton, "LOG OUT NOW", WINDOW_WIDTH / 2 - 20, 120, "Arial", 16, WINDOW_WIDTH / 2, Pos.CENTER);
		logoutButton.setOnAction((_) -> {
			guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
			theStage.close();
		});
		
		theRoot.getChildren().addAll(successMeessage, note, logoutButton);
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setOnCloseRequest((_) -> {
			guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
			theStage.close();
		});
		theStage.setScene(theScene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
	}

	
	/*
	 * Private local method to initialize the standard attribute fields for a label
	 */
	static private void setupLabelWidget(Label l, double x, double y, String ff, double f, double w, 
			Pos p){
		l.setLayoutX(x);
		l.setLayoutY(y);		
		l.setFont(Font.font(ff, f));	// The font face and the font size
		l.setMinWidth(w);
		l.setAlignment(p);
	}


	/*
	 * Private local method to initialize the standard fields for a button
	 */
	static private void setupButtonWidget(Button b, String s, double x, double y, String ff, double f, double w, 
			Pos p){
		b.setText(s);
		b.setFont(Font.font(ff, f));	// The font face and the font size
		b.setMinWidth(w);
		b.setMaxWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
}