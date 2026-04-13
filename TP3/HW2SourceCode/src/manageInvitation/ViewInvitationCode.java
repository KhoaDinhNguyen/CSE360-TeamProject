package manageInvitation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.InvitationCode;
import entityClasses.User;

public class ViewInvitationCode {
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	private static ViewInvitationCode theView;
	protected static Stage theStage;
	protected static User theUser;
	
	private static Pane theRootPane;
	private static Scene theListAllUsersScene;
	
	protected static Label label_PageTitle = new Label();
	private static TableView<InvitationCode> table;
	protected static Button button_return;
	
	public static void displayViewInvitationCodes(Stage ps, User user) {
		theStage = ps;
		theUser = user;
		
		if (table == null) {
			table = new TableView<>();
		}
		
		if (theView == null) theView = new ViewInvitationCode();
		
		theStage.setTitle("CSE 360: List All Users Page");
		theStage.setScene(theListAllUsersScene);
		
		loadInvitationCodes();

		theStage.show();
	}
	
	
	private ViewInvitationCode() {
		theRootPane = new Pane();
		theListAllUsersScene = new Scene(theRootPane, width, height);
		
		// GUI Area 1
		label_PageTitle.setText("Manage Invitation");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
		
		addColumn(table, "Code", "code");
		addColumn(table, "Email address", "emailAddress");
		addColumn(table, "Role", "role");
		addDeleteColumn();
		
	    
		table.setLayoutY(75);
		table.setMinWidth(width);
		
		button_return = new Button("Return");
		
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
	
	private static void addDeleteColumn() {
		TableColumn<InvitationCode, Void> deleteCol = new TableColumn<>("");
		
	    deleteCol.setCellFactory(_ -> new TableCell<>() {
	    	private Button btn = new Button("Delete");

	        {
	            btn.setOnAction(_ -> {
	                InvitationCode invitation = getTableView().getItems().get(getIndex());
	                
	                Alert alert_deleteInvitation = new Alert(AlertType.CONFIRMATION);
	                
	                alert_deleteInvitation.setTitle("Delete Confirmation");
	                alert_deleteInvitation.setHeaderText("Email: " + invitation.getEmailAddress() + ", Code: " + invitation.getCode());
	                alert_deleteInvitation.setContentText("Are you sure you want to delete this invitation?");
	                

	                Optional<ButtonType> result = alert_deleteInvitation.showAndWait();
	                
	                if (result.isPresent() && result.get() == ButtonType.OK) {               	
	                	theDatabase.deleteInvitationCode(invitation.getCode());
	                	getTableView().getItems().remove(invitation);
	                }
	                
	            });
	        }

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setGraphic(null);
	            } else {
	                setGraphic(btn);
	            }
	        }
	    });

	    table.getColumns().add(deleteCol);
	}
	
	private static void loadInvitationCodes() {
		table.getItems().clear();
	    
		List<InvitationCode> invitationList = theDatabase.getAllInvitationCodes();
		
		for (int i = 0; i < invitationList.size(); ++i) {
			table.getItems().add(invitationList.get(i));
		}
		
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