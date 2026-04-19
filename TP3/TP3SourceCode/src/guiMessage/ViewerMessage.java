package guiMessage;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.Database;
import entityClasses.Message;
import entityClasses.MessageStore;
import entityClasses.ThreadStore;
//import database.Database;
import entityClasses.User;
import guiForum.ControllerForum;
import guiForum.ModelForum;
import guiStaffHome.ControllerStaffHome;
import guiStudentHome.ControllerStudentHome;


public class ViewerMessage {
	
	/*-*******************************************************************************************
	
	Attributes
	
	 */
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	
	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
		
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);
	
	// GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	// would contain the widgets needed for the user to play the assigned role.
	
	protected static Button button_createMessage = new Button("Create Messsage");
	private static Button button_dm = new Button("DM");
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	protected static Label label_messageTitle = new Label();
	protected static Label label_messageSender = new Label();
	protected static Label label_messageContent = new Label();
	protected static ChoiceBox<String> choicebox_user = new ChoiceBox<>();
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Return = new Button("Return");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");
	
	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewerMessage theView;		// Used to determine if instantiation of the class
												// is needed
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	private static MessageStore theMessageStore = ModelMessage.theMessageStore;
	private static ListView<Message> receivedMessageView = new ListView<>();
	private static ListView<Message> DMView = new ListView<>();
	private static Message specifiedMessage = null;
	private static String specificReceiver = "";
	
	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	
	private static Scene scene;		// The shared Scene each invocation populates
	protected static final int theRole = 3;		// Admin: 1; Role1: 2; Role2: 3
	
	/*-*******************************************************************************************
	
	Constructors
	
	 */

	public static void displayMessageView(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewerMessage();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		loadReceivedMessage();
		
		label_UserDetails.setText("User: " + theUser.getUserName());// Set the username
	
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360: DM");
		theStage.setScene(scene);						// Set this page onto the stage
		theStage.show();											// Display it to the user
	}
	
	private ViewerMessage() {
		
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		scene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Messages");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
	
		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
	
		HBox container = new HBox(10);
		VBox message_list_container = new VBox(10);
		VBox message_detail_container = new VBox(5);
		
		container.setLayoutX(20);
		container.setLayoutY(110);
		
		// GUI Area 2		
		button_createMessage.setFont(Font.font("Dialog", 16));
		button_createMessage.setOnAction(_ -> {
			loadDMMessage("");
			ControllerMessage.performDM();
		});
				
		receivedMessageView.setCellFactory(_ -> new ListCell<Message>() {
			@Override
		    protected void updateItem(Message m, boolean empty) {
		        super.updateItem(m, empty);

		        if (empty || m == null) {
		            setText(null);
		            setGraphic(null);
		        } else {
		        	Label titleLabel = new Label(m.getSender() + " : ");
		        	titleLabel.setStyle("-fx-font-weight: bold;");
		        	Label contentLabel = new Label(m.getContent());

		        	HBox container = new HBox(titleLabel, contentLabel);
		        	setGraphic(container);
		        	
		        	setPrefWidth(250);
		        }
		    }
		});
		
		receivedMessageView.setOnMouseClicked(event -> {
		    specifiedMessage = receivedMessageView.getSelectionModel().getSelectedItem();
		    
		    readSpecifiedMessage();
		});
		
		receivedMessageView.setPrefWidth(250);
		receivedMessageView.setPrefHeight(350);
		
		message_list_container.getChildren().addAll(button_createMessage, receivedMessageView);
		
		
		HBox sender_container = new HBox(5);
		button_dm.setFont(Font.font("Dialog", 12));
		button_dm.setVisible(false);
		button_dm.setOnAction(_ -> {
			specificReceiver = specifiedMessage.getSender();
			ControllerMessage.performDM();
		});
		
		sender_container.setAlignment(Pos.CENTER_LEFT);
		sender_container.getChildren().addAll(label_messageSender, button_dm);

		
		message_detail_container.getChildren().addAll(sender_container, label_messageContent);
		label_messageTitle.setFont(Font.font("Dialog", FontWeight.BOLD, 20));
		label_messageSender.setFont(Font.font("Dialog", 20));
		label_messageContent.setFont(Font.font("Dialog", 16));
		label_messageContent.setPadding(new Insets(30, 0, 0, 0));
		
		 Line line_Separator = new Line();
		 line_Separator.setStrokeWidth(2);
		 line_Separator.setStartY(0);
		 line_Separator.setEndY(400);
			
		container.getChildren().addAll(message_list_container, line_Separator, message_detail_container);
		
		DMView.setCellFactory(_ -> new ListCell<Message>() {
			@Override
		    protected void updateItem(Message m, boolean empty) {
		        super.updateItem(m, empty);
		        if (empty || m == null) {
		            setText(null);
		            setGraphic(null);
		        } else {
		        	Label contentLabel = new Label(m.getSender() + ":" + m.getContent());
		        	contentLabel.setFont(Font.font("Dialog", 16));
		        	contentLabel.setTextFill(Color.BLACK);
		        	HBox container = new HBox(contentLabel);
		        	setGraphic(container);
		        	
		        	setStyle("-fx-background-color: transparent; " +
		                    "-fx-text-fill: black; " +
		                    "-fx-text-background-color: transparent;");

			       
		        	setPrefWidth(width - 150);
		        }
		    }
		});
		
		DMView.setMaxWidth(width - 150);
		DMView.setPrefHeight(300);
		DMView.setStyle(
			    "-fx-background-color: transparent; " +
			    "-fx-control-inner-background: transparent; " +
			    "-fx-text-fill: black; " +            
			    "-fx-text-background-color: transparent;"
			);
		
		choicebox_user.setOnAction(_ -> {
			specificReceiver = choicebox_user.getValue();
		    if (specificReceiver != null && !specificReceiver.isBlank()) {
		    	loadDMMessage(specificReceiver);
		    }
		});

		// GUI Area 3
		setupButtonUI(button_Return, "Dialog", 18, 250, Pos.CENTER, 20, 540);
		button_Return.setOnAction((_) -> { ControllerStaffHome.performReturn(); });
		
	    setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 300, 540);
//	    button_Logout.setOnAction((_) -> {ControllerStaffHome.performLogout(); });
	    
	    setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 580, 540);
//	    button_Quit.setOnAction((_) -> {ControllerStaffHome.performQuit(); });
	
		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
	    theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, line_Separator1,
	        line_Separator4, button_Return, button_Logout, button_Quit, container);
	}
	
	
	/*-********************************************************************************************
	
	Helper methods to reduce code length
	
	 */
	protected static void displayDMView() {
		Stage addStage = new Stage();
		Pane addPane = new Pane();
		addStage.setTitle("CSE360: Direct Message");
		
		Scene addScene = new Scene(addPane, width - 100, height - 50);
		
		VBox container = new VBox(10);
		container.setLayoutX(20);
		
		Label label_title = new Label("Direct Message");
		setupLabelUI(label_title, "Dialog", 20, width - 100, Pos.BASELINE_CENTER, 0, 0);
		
		HBox container_ChoiceBox = new HBox(5);
		Label label_ChoiceBox = new Label("To: ");
		label_ChoiceBox.setFont(Font.font("Dialog", 16));
		
		if (specificReceiver != null) {
			choicebox_user.setValue(specificReceiver);
		}
		
		container_ChoiceBox.getChildren().addAll(label_ChoiceBox, choicebox_user);
		
		TextArea message_input = new TextArea();
		message_input.setMaxWidth(width - 180);
		message_input.setPrefHeight(75);
		
		Button button_send = new Button("Send");
		button_send.setFont(Font.font("Dialog", 16));
		
		button_send.setOnAction(_ -> {
			ModelMessage.addMessage(message_input.getText(), theUser.getUserName(), specificReceiver);
			message_input.setText("");
			loadDMMessage(specificReceiver);
			
		});
		
		loadUsers();
		
		container.getChildren().addAll(label_title, container_ChoiceBox, DMView, message_input, button_send);
		
		addPane.getChildren().addAll(container);
		addStage.setScene(addScene);
		
		addStage.setOnCloseRequest(_ -> {
			specificReceiver = "";
		});
		
		addStage.show();
	}
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, 
			double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, 
			double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	private static void loadUsers() {
		List<String> usernameList = theDatabase.getUserList();
		ArrayList<String> studentAndStaffList = new ArrayList<String>();
		
		for (int i = 0; i < usernameList.size(); ++i) {
			User user = theDatabase.getUserDetails(usernameList.get(i));
			
			if (user == null) continue;
			if (user.getUserName().equals(theUser.getUserName())) continue;
			
			if (user.getStaff() || user.getStudent()) {
				studentAndStaffList.add(user.getUserName());
			}
		}
		
		choicebox_user.setItems(FXCollections.observableArrayList(studentAndStaffList));
	}
	
	
	private static void loadReceivedMessage() {
		ArrayList<Message> mList = theMessageStore.getMessagesByReceiverUsers(theUser.getUserName());
		ObservableList<Message> data = FXCollections.observableArrayList(mList);
		
		receivedMessageView.setItems(data);
	}
	
	private static void readSpecifiedMessage() {
		if (specifiedMessage != null) {
			label_messageContent.setText(specifiedMessage.getContent());
			label_messageSender.setText("From: " + specifiedMessage.getSender());
			button_dm.setVisible(true);
		}
		else {
			button_dm.setVisible(false);
		}
	}
	
	private static void loadDMMessage(String user) {
		ArrayList<Message> mList = theMessageStore.getMessagesByTwoUsers(user, theUser.getUserName());
		ObservableList<Message> data = FXCollections.observableArrayList(mList);
		
		DMView.setItems(data);
	}
}
