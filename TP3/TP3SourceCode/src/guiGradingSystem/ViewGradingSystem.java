package guiGradingSystem;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import database.Database;
import entityClasses.Assignment;
import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ThreadStore;
import entityClasses.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;


/**
 * Provides the JavaFX view for the discussion forum.
 *
 * <p>This class builds and manages the forum user interface, including the post list,
 * post details, reply area, search controls, and actions for creating, editing,
 * deleting, and viewing posts and replies.</p>
 */
public class ViewGradingSystem {
	
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	/**
	 * Page title label
	 */
	protected static Label label_PageTitle = new Label();
	
	/**
	 * User detail label
	 */
	protected static Label label_UserDetails = new Label();
	
	/**
	 * This is a separator and it is used to partition the GUI for various tasks
	 */
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	// would contain the widgets needed for the user to play the assigned role.
	
	private static ListView<String> studentListView;
	
	private static VBox detailPane;
	private static Label detailTitle;
	private static ScrollPane detailScrollPane;
	
	/**
	 * Create Assignment button
	 */
	protected static Button button_New_Assignment;
	
	// Track which student is selected
	private static String selectedStudent;
	
	/**
	 * This is a separator and it is used to partition the GUI for various tasks
	 */
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	/**
	 * Logout button
	 */
	protected static Button button_Logout = new Button("Logout");
	
	/**
	 * Return to homepage button
	 */
	protected static Button button_Return = new Button("Return");
	
	/**
	 * Quit application button
	 */
	protected static Button button_Quit = new Button("Quit");
	
	/**
	 * Delete button
	 */
	private static Button button_Delete = new Button("Delete");
	
	private static ViewGradingSystem theView;
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	private static Scene theForumScene;	// The shared Scene each invocation populates
	
	/**
	 * The role of user
	 */
	protected static int theRole = 2;
	
	/**
	 * The Stage that JavaFX has established for us	
	 */
	protected static Stage theStage;
	/**
	 * The Pane that holds all the GUI widgets
	 */
	protected static Pane theRootPane;
	/**
	 * The current logged in User
	 */
	protected static User theUser;				
	
	/**
	 * Displays the forum view for the specified user on the provided stage.
	 *
	 * @param ps the stage used to display the forum scene
	 * @param user the user currently viewing the forum
	 */
	public static void displayGradingSystem(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		if (user.getStaff()) {
			theRole = 3;
		}
		else if (user.getStudent()) {
			theRole = 2;
		}
		
		// If not yet established, populate the static aspects of the GUI
		
		if (studentListView == null) {
			studentListView = new ListView<>();
		}
		
		if (theView == null) theView = new ViewGradingSystem();		// Instantiate singleton if needed
		

		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
		
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Grader");
		theStage.setScene(theForumScene);
		theStage.show();
	}
	
	/**
	 * Creates the grader view object.
	 *
	 * <p>This constructor is private because the class uses shared static UI state
	 * and is not intended to be instantiated freely from outside the class.</p>
	 */
	private ViewGradingSystem() {
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theForumScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Grading System");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
		
		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		// Create Assignment button
		button_New_Assignment = new Button("Create Assignment");
		setupButtonUI(button_New_Assignment, "Dialog", 13, 75, Pos.CENTER, 375, 55);
		button_New_Assignment.setOnAction((_) -> { 
			ControllerGradingSystem.performNewAssignment(); 
		});
		
		// Create Assignment button
		button_Delete = new Button("Delete Assignment");
		setupButtonUI(button_Delete, "Dialog", 13, 75, Pos.CENTER, 515, 55);
		button_Delete.setOnAction((_) -> { 
			ControllerGradingSystem.performDelete(); 
		});

		studentListView.setFixedCellSize(50);
		
		studentListView.setLayoutX(20);
		studentListView.setLayoutY(105);
		studentListView.setPrefWidth(300);
		studentListView.setPrefHeight(410); // from y=105 to around y=525
		List<String> PostItemList = ModelGradingSystem.getStudentList();
		updatingList(PostItemList);
				
		
		detailPane = new VBox(10);

		detailScrollPane = new ScrollPane(detailPane);
		detailScrollPane.setPrefWidth(width - 360);
		detailScrollPane.setPrefHeight(410);
		detailScrollPane.setLayoutX(340);               
		detailScrollPane.setLayoutY(105);
		detailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scroll
		detailScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scroll only when text is long
		detailScrollPane.setStyle("-fx-padding: 15; -fx-border-color: #cccccc; -fx-border-width: 1;");
		detailScrollPane.setFitToWidth(true);
		
		detailTitle = new Label(" ");
		detailTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
		
		studentListView.setOnMouseClicked(event -> {
		    selectedStudent = studentListView.getSelectionModel().getSelectedItem();
		    
		    displayStudentDetails(selectedStudent);
		    
		    updatingList(ModelGradingSystem.getStudentList());
		});
		
		// GUI Area 3
		
		setupButtonUI(button_Return, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Return.setOnAction((_) -> {ControllerGradingSystem.performReturn(); });
        
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Logout.setOnAction((_) -> {ControllerGradingSystem.performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 580, 540);
        button_Quit.setOnAction((_) -> {ControllerGradingSystem.performQuit(); });
        
        
        // Add in Functionality

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
        theRootPane.getChildren().addAll(
        	    label_PageTitle, label_UserDetails, line_Separator1,

        	    line_Separator4, button_Logout, button_Quit, button_Return,
        	    button_New_Assignment, button_Delete, studentListView, detailPane,

        	    detailScrollPane 
        	);
		
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
		if (b == null) {
			System.out.println(x);
			System.out.println(y);
		}
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	/**
	 * Update the list view to have all the student
	 * @param studentUsernames a List of string contains all the student's username
	 */
	public static void updatingList(List<String> studentUsernames) {
		studentListView.getItems().setAll(studentUsernames);
	}
	
	/**
	 * This methods display all the assignment with the selected student username
	 * @param studentUsername a String that contains the selected student's username
	 */
	protected static void displayStudentDetails(String studentUsername) {
		
	    if (selectedStudent == null) return;
	    
	    // flush out detail pane
	    detailPane.getChildren().clear();
	    
	    VBox assignmentListVBox = new VBox();
	    
	    List<Assignment> assnList = ModelGradingSystem.getAssignmentList();

	    for (Assignment assn: assnList) {
	    	//get the current feedback of the assignment
	    	int score = assn.getFeedback(studentUsername).getScore();
	    	String comment = assn.getFeedback(studentUsername).getComment();

	    	// create a HBox to hold assignment info
	    	HBox assignmentHbox = new HBox();
	    	
	    	assignmentHbox.setPadding(new Insets(0, 15, 15, 15));;
	    	
	    	// get all the component to display a assignment detail
	    	Label titleLabel = new Label(assn.getTitle());
	    	TextField tfScore = new TextField("" + score);
	    	tfScore.setPrefWidth(50);

	    	Label seperatorLabel = new Label("/");
	    	TextField tfMaxScore = new TextField("" + assn.getMaxScore());
	    	tfMaxScore.setPrefWidth(50);

	    	Label weightLabel = new Label("Weight (%): ");
	    	TextField tfWeight = new TextField("" + assn.getWeight());
	    	tfWeight.setPrefWidth(50);
	    	Label commentLabel = new Label("Comment: ");
	    	TextField tfComment = new TextField(comment); 
	    	
	    	// add them to the Hbox and add the Hbox to Vbox
	    	assignmentHbox.getChildren().addAll(
	    			titleLabel, 
	    			tfScore, seperatorLabel, 
	    			tfMaxScore, 
	    			weightLabel, tfWeight,
	    			commentLabel, tfComment
	    			);

	    	assignmentListVBox.getChildren().addAll(assignmentHbox);
	    }

	    Label totalLabel = new Label("Total (%): " + ModelGradingSystem.getTotalScore(studentUsername));
	    	
	    assignmentListVBox.getChildren().addAll(totalLabel);
	    
	    // add message area to annouce helpful error messages
	    VBox errMsgVBox = new VBox();
	    
	    // add button to update grade
	    Button button_Update = new Button("Update");
	    assignmentListVBox.getChildren().add(button_Update);
	    
	    button_Update.setOnAction(e -> {
	    	
	    	errMsgVBox.getChildren().clear();
	    	
	    	for (int i = 0; i < ModelGradingSystem.getAssignmentList().size(); i++ ) {
	    		HBox assignmentHbox = (HBox) assignmentListVBox.getChildren().get(i);
	    			    	
	    		TextField tfScore = (TextField)(assignmentHbox.getChildren().get(1));
	    		TextField tfComment = (TextField)(assignmentHbox.getChildren().get(7));
	    		
	    		String errMsg = "";
	    			    	
	    		errMsg = ModelGradingSystem.setFeedback(i, studentUsername, Integer.parseInt(tfScore.getText()), tfComment.getText());
	    	
	    		// if there are err messages put that into the errMsgVBox
	    		if (errMsg != "") {
	    			errMsgVBox.getChildren().add(new Label(errMsg));
	    		}
	    		
	    		TextField tfMaxScore = (TextField)(assignmentHbox.getChildren().get(3));
	    		
	    		errMsg = ModelGradingSystem.setMaxScore(i, Integer.parseInt(tfMaxScore.getText()));
	    		
	    		// reset the score field after set max score
	    		tfScore.setText("" + ModelGradingSystem.getAssignmentList().get(i)
	    							.getFeedback(studentUsername).getScore());

	    		// if there are err messages put that into the errMsgVBox
	    		if (errMsg != "") {
	    			errMsgVBox.getChildren().add(new Label(errMsg));
	    		}
	    		
	    		TextField tfWeight = (TextField)(assignmentHbox.getChildren().get(5));
	    		
	    		errMsg = ModelGradingSystem.setWeight(i, Integer.parseInt(tfWeight.getText()));
	    		
	    		// if there are err messages put that into the errMsgVBox
	    		if (errMsg != "") {
	    			errMsgVBox.getChildren().add(new Label(errMsg));
	    		}
	    		
	    		// reload the total label 
	    		totalLabel.setText("Total (%): " + ModelGradingSystem.getTotalScore(studentUsername));
	    	}
	    	
	    	// check total weight
	    	Label msgWeightLabel = new Label("");
	    	String errMsg = ModelGradingSystem.checkTotalWeight();
	    	msgWeightLabel.setText(errMsg);
	    	errMsgVBox.getChildren().add(msgWeightLabel);

	    });
	   
	    // check total weight
	    Label msgWeightLabel = new Label("");
	    String errMsg = ModelGradingSystem.checkTotalWeight();
	    msgWeightLabel.setText(errMsg);
	    errMsgVBox.getChildren().add(msgWeightLabel);

	    assignmentListVBox.getChildren().add(errMsgVBox);
	   
	    // Add the Vbox to the detail pane
	    detailPane.getChildren().add(assignmentListVBox);
	}	
	
	/**
	 * Opens a window that allows the current user to create a new post.
	 */
	protected static void showNewAssignmentWindow() {
	    Stage addStage = new Stage();
	    addStage.setTitle("Create New Assignment");

	    VBox addRoot = new VBox(12);
	    addRoot.setPrefSize(560, 640);

	    Scene addScene = new Scene(addRoot, 560, 640);

	    Label titleLabel = new Label("Create Assignment");
	    titleLabel.setFont(Font.font("Arial", 20));

	    Label labelTitle = new Label("Title: ");
	    labelTitle.setFont(Font.font("Arial", 14));

	    TextField tfTitle = new TextField();
	    tfTitle.setPrefWidth(520);
	    tfTitle.setPromptText("Enter a short, clear title");
	    
	    // Making changes while typing
	    
	    Label labelContent = new Label("Content: ");
	    labelContent.setFont(Font.font("Arial", 14));

	    TextArea taContent = new TextArea();
	    taContent.setPrefWidth(520);
	    taContent.setPrefHeight(180);
	    taContent.setWrapText(true);
	    taContent.setPromptText("Write your assignment content here...");
	    
	    // Max Score field 
	    Label labelMaxScore = new Label("Max Score: ");
	    labelContent.setFont(Font.font("Arial", 14));

	    TextField tfMaxScore = new TextField();
	    tfMaxScore.setPrefWidth(50);
	    
	    HBox hboxMaxGrade = new HBox();
	    hboxMaxGrade.getChildren().addAll(labelMaxScore, tfMaxScore);

	    // Weight field
	    Label labelWeight = new Label("Weight (%): ");
	    labelContent.setFont(Font.font("Arial", 14));

	    TextField tfWeight = new TextField();
	    tfWeight.setPrefWidth(50);
	    
	    HBox hboxWeight = new HBox();
	    hboxWeight.getChildren().addAll(labelWeight, tfWeight);

	    Button btnUpdate = new Button("Update");
	    Button btnCancel = new Button("Cancel");

	    setupButtonUI(btnUpdate, "Dialog", 16, 160, Pos.CENTER, 0, 0);
	    setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 0, 0);

	    HBox buttonBar = new HBox(10, btnCancel, btnUpdate);
	    buttonBar.setAlignment(Pos.CENTER_RIGHT);

	    btnCancel.setOnAction(e -> addStage.close());

	    btnUpdate.setOnAction(e -> {
	        String title = tfTitle.getText();
	        String content = taContent.getText();
	        int maxScore = Integer.parseInt(tfMaxScore.getText());
	        int weight = Integer.parseInt(tfWeight.getText());

	        String errorMessage = ModelGradingSystem.newAssignment(title, content, maxScore, weight);

	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Create Assignment");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

	        // refresh and close
	        displayStudentDetails(selectedStudent);
	        addStage.close();
	    });
	    
	    addRoot.getChildren().addAll(
	        titleLabel,
	        labelTitle,
	        tfTitle,
	        labelContent,
	        taContent,
	        hboxMaxGrade,
	        hboxWeight,
	        buttonBar
	    );

	    addStage.setScene(addScene);
	    addStage.initOwner(theStage);
	    addStage.show();
	}
	
	/**
	 * Display delete window for deleting assignment
	 */
	protected static void showDeleteWindow() {
	    Stage addStage = new Stage();
	    addStage.setTitle("Delete Assignment");

	    VBox addRoot = new VBox(12);
	    addRoot.setPrefSize(560, 640);

	    Scene addScene = new Scene(addRoot, 560, 640);

	    Label titleLabel = new Label("Delete Assignment");
	    titleLabel.setFont(Font.font("Arial", 20));
	    addRoot.getChildren().add(titleLabel);
	    
	    // add the assignments
	    for (Assignment assn: ModelGradingSystem.getAssignmentList()) {
	    	// for the details and delete button
	    	HBox assnHBox = new HBox();
	    	assnHBox.setPadding(new Insets(0, 15, 15, 15));
	    	assnHBox.setSpacing(20);
	    	
	    	Label assnLabel = new Label(assn.getTitle());
	    	
	    	Button deleteButton = new Button("Delete");
	    	deleteButton.setOnAction(e -> {
	    		ModelGradingSystem.deleteAssignment(assn);
	    		
	    		// refresh and close
	    		showDeleteWindow();
	    		displayStudentDetails(selectedStudent);
	    		addStage.close();
	    	});
	    	
	    	assnHBox.getChildren().addAll(assnLabel, deleteButton);
	    	
	    	addRoot.getChildren().add(assnHBox);
	    }
	    

	    Button btnCancel = new Button("Cancel");

	    setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 0, 0);

	    HBox buttonBar = new HBox(10, btnCancel);
	    buttonBar.setAlignment(Pos.CENTER_RIGHT);

	    btnCancel.setOnAction(e -> {
	    	// refresh and close
    		displayStudentDetails(selectedStudent);
	    	addStage.close();
	    });

	    addRoot.getChildren().addAll(
	        buttonBar
	    );

	    addStage.setScene(addScene);
	    addStage.initOwner(theStage);
	    addStage.show();
	}
}