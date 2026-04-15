package guiForumManagement;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

import database.Database;
import entityClasses.ThreadStore;
//import database.Database;
import entityClasses.User;
import guiStaffHome.ControllerStaffHome;
import guiStudentHome.ControllerStudentHome;


public class ViewerForumManagement {
	
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
	protected static Button button_Thread_Management = new Button("Thread Management");
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Return = new Button("Return");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");
	
	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewerForumManagement theView;		// Used to determine if instantiation of the class
												// is needed
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	private static ThreadStore theThreadStore = guiForum.ModelForum.getThreadStore();
	private static TableView<String> threadList = null;

	private static ObservableList<String> threadData;
	
	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	
	private static Scene scene;		// The shared Scene each invocation populates
	protected static final int theRole = 3;		// Admin: 1; Role1: 2; Role2: 3
	
	/*-*******************************************************************************************
	
	Constructors
	
	 */

	public static void displayForumManagement(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewerForumManagement();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());// Set the username
	
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360: Role2 Forum Management");
		theStage.setScene(scene);						// Set this page onto the stage
		theStage.show();											// Display it to the user
	}
	
	/**********
	 * <p> Method: ViewStaffHome() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object. </p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */
	private ViewerForumManagement() {
		
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		scene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Role2 Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
	
		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
	
		// GUI Area 2
		setupButtonUI(button_Thread_Management, "Dialog",18, 250, Pos.CENTER, 20, 140 );
		button_Thread_Management.setOnAction((_)-> { ControllerForumManagement.performThreadManagement(); });
		
		
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
	        line_Separator4, button_Return, button_Logout, button_Quit,
	        button_Thread_Management);
	}
	
	
	/*-********************************************************************************************
	
	Helper methods to reduce code length
	
	 */
	
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
	
	protected static void displayThreadManagement() {
		double stageWidth = width - 50, stageHeight = height - 20;

	    Stage addStage = new Stage();
	    addStage.setTitle("Thread Management");
	    
	    VBox addRoot = new VBox();
	    addRoot.setPrefSize(stageWidth, stageHeight);
	    
	    Label label_Title = new Label("Thread Management");
	    setupLabelUI(label_Title, "Dialog", 24, stageWidth, Pos.CENTER, 0, 100);
	    
	    HBox splitWindow = new HBox(15);
	
	    VBox instructionRoot = new VBox(10);
	    HBox threadListRoot = new HBox(10);
	    
	    Label label_Instruction = new Label("Instructions");
	    label_Instruction.setFont(Font.font("Dialog", FontWeight.BOLD ,20));
	    
	    Label label_Note_1 = new Label("Here is the list of thread.");
	    Label label_Note_2 = new Label(
	    		"As a staff member, you can modify forum's thread with following rules:\n" +
	    		"1. You can create new thread but thread must be unique\n" +
	    		"2. You can update thread and the post's thread will be updated as well\n" +
	    		"3. You can update thread by directly editting the table. However, you cannot update General thread\n" +
	    		"4. You can delete thread but you cannot delete General thread"
	    		);
	    
	    double instructionWidth = (stageWidth - 100) / 2;
	    
	    label_Note_1.setFont(Font.font("Dialog", 16));
	    label_Note_1.setWrapText(true);
	    label_Note_1.setMaxWidth(instructionWidth);
	    label_Note_2.setFont(Font.font("Dialog", 16));
	    label_Note_2.setWrapText(true);
	    label_Note_2.setMaxWidth(instructionWidth);
	    
	    instructionRoot.getChildren().addAll(label_Instruction, label_Note_1, label_Note_2);
	    instructionRoot.setPrefWidth(instructionWidth);
	    
	    // Right-hand window
	    
	    // Thread Table
	    threadList = new TableView<>();
	    
	    TableColumn<String, String> threadCol = new TableColumn<>("Thread name");
	    threadCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
	    threadCol.setCellFactory(TextFieldTableCell.forTableColumn());
	    threadCol.setOnEditCommit(event -> {
	    	String oldName = event.getOldValue(), newName = event.getNewValue();
	    	ControllerForumManagement.performEditThread(oldName, newName);
	    });
	    
	    threadList.getColumns().addAll(threadCol);
	    threadList.setEditable(true);
	    threadList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
	    threadList.setPrefWidth(200);
	    
	    loadThreadData();
	    
	    // Thread buttons
	    VBox threadButtonCard = new VBox(10);
	    
	    // Create button card
	    HBox createThreadCard = new HBox(5);
	    TextField newThreadInput = new TextField();
	    
	    newThreadInput.setFont(Font.font("Dialog", 14));
	    newThreadInput.setPrefWidth(150);
	    newThreadInput.setPromptText("Thread's name");
	    
	    Button button_createThread = new Button("Create");
	    button_createThread.setFont(Font.font("Dialog", 14));
	    button_createThread.setOnAction((_) -> { 
	    	String errorMessage = ControllerForumManagement.performAddThread(newThreadInput.getText());
	    	if (errorMessage.isEmpty()) {
	    		newThreadInput.setText("");
	    	}
	    	});
	    createThreadCard.getChildren().addAll(newThreadInput, button_createThread);
	    
	    // Delete button card
	    HBox deleteThreadCard = new HBox(5);
	    TextField deleteThreadInput = new TextField();
	    deleteThreadInput.setPromptText("Thread's name");
	    
	    deleteThreadInput.setFont(Font.font("Dialog", 14));
	    deleteThreadInput.setPrefWidth(150);
	    
	    Button button_deleteThread = new Button("Delete");
	    button_deleteThread.setFont(Font.font("Dialog", 14));
	    button_deleteThread.setOnAction((_) -> { 
	    	String errorMessage = ControllerForumManagement.performDeleteThread(deleteThreadInput.getText());
	    	if (errorMessage.isEmpty()) {
	    		deleteThreadInput.setText("");
	    	}
	    	});
	    
	    deleteThreadCard.getChildren().addAll(deleteThreadInput, button_deleteThread);
	    
	    threadButtonCard.getChildren().addAll(createThreadCard, deleteThreadCard);
	    
	    threadListRoot.getChildren().addAll(threadList, threadButtonCard);

	    // Line separator
	    Line line_Separator = new Line();
	   	line_Separator.setStrokeWidth(2);
//		line_Separator.setStartX(0);
//		line_Separator.setEndX(0);
		line_Separator.setStartY(0);
		line_Separator.setEndY(stageHeight - 100);
	    
	    splitWindow.getChildren().addAll(instructionRoot, line_Separator, threadListRoot);
	    splitWindow.setPadding(new Insets(50, 20, 0, 20));
	    
	    addRoot.getChildren().addAll(label_Title, splitWindow);

	    Scene addScene = new Scene(addRoot, width - 50, height - 20);
	    
	    addStage.setScene(addScene);
	    addStage.initOwner(theStage);
	    addStage.show();
	}
	
	protected static void loadThreadData() {
		if (threadList != null) {
			ArrayList<String> allThreads = theThreadStore.getAllThreads();
			threadData = FXCollections.observableArrayList(allThreads);
			threadList.setItems(threadData);
		}
	}
	
	protected static Optional<ButtonType> displayAlert(AlertType alertType, String title, String headerText, String message) {
		Alert alert = new Alert(alertType);

		alert.setContentText(message);
		alert.setTitle(title);
		
		alert.setHeaderText(headerText);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		return result;
	}
}
