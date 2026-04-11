package guiListAllUsers;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;

public class ViewListAllUsers {
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	private static ViewListAllUsers theView;
	protected static Stage theStage;
	protected static User theUser;
	
	private static Pane theRootPane;
	private static Scene theListAllUsersScene;
	
	protected static Label label_PageTitle = new Label();
	private static TableView<User> table = new TableView<>();
	protected static Button button_return = new Button("Return");
	
	public static void displayViewListAllUser(Stage ps, User user) {
		theStage = ps;
		theUser = user;
		
		if (theView == null) theView = new ViewListAllUsers();
		
		loadUsers();
		
		theStage.setTitle("CSE 360: List All Users Page");
		theStage.setScene(theListAllUsersScene);
		theStage.show();
	}
	
	
	private ViewListAllUsers() {
		theRootPane = new Pane();
		theListAllUsersScene = new Scene(theRootPane, width, height);
		
		// GUI Area 1
		label_PageTitle.setText("List All Users Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
		
		
		addColumn(table, "Username", "userName");
		addColumn(table, "First name", "firstName");
		addColumn(table, "Preferred first name", "preferredFirstName");
		addColumn(table, "Middle name", "middleName");
		addColumn(table, "Last name", "lastName");
		addColumn(table, "Email address", "emailAddress");
		addColumn(table, "Roles", "roles");
		
		table.setLayoutY(75);
		table.setMinWidth(width);
		
		
		setupButtonUI(button_return, "Dialog", 20, 150, Pos.CENTER, 300, height - 100);
		button_return.setOnAction((_) -> {guiAdminHome.ViewAdminHome.displayAdminHome(theStage, theUser);});
		
		// Place all items into the Root Pane
		theRootPane.getChildren().addAll(label_PageTitle, table, button_return);
	}
	
	private static <S, T> void addColumn(TableView <S> targetTable, String columnName, String propertyName) {
		TableColumn<S, T> column = new TableColumn<>(columnName);
		column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
		
		targetTable.getColumns().add(column);
	}
	
	private static void loadUsers() {
		table.getItems().clear();
		
		List<String> userNameList = theDatabase.getUserList();
		
		for (int i = 1; i < userNameList.size(); ++i) {
			User user = theDatabase.getUserDetails(userNameList.get(i));
			if (user != null) {
				table.getItems().add(user);
			}
		}
		
//		for (int i = 1; i < 100; ++i) {
//			table.getItems().add(new User("username" + i, "", "", "", "", "", "", false, false, false));
//		}
	}
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	
	protected static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	
	
}