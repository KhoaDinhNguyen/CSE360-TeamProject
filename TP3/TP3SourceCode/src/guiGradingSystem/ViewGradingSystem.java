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
import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ThreadStore;
import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
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
	private static Label detailThread;
	private static Label detailTitle;
	private static Label detailAuthor;
	private static Label detailContent;
	private static ScrollPane detailScrollPane;
	
	/**
	 * Create Post button
	 */
	protected static Button button_New_Assignment;
	
	// Reply UI (shown only when a post is selected)
	private static VBox replyPane;
	private static TextArea replyTextArea;
	private static Button replyButton;
	private static Button unreadReplyButton;
	
	private static VBox repliesBox;
	private static Label repliesLabel;

	// Track which post is selected
	private static String selectedPost;
	
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
	 * Edit button
	 */
	private static Button editPostButton = new Button("Edit");
	
	/**
	 * Delete button
	 */
	private static Button deletePostButton = new Button("Delete");
	
	private static boolean unreadState = false;
	
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
		theStage.setTitle("CSE 360 Foundations: Role1 Home Page");
		theStage.setScene(theForumScene);
		theStage.show();
	}
	
	/**
	 * Creates the forum view object.
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
		
		// Create Post button
		button_New_Assignment = new Button("Create Assignment");
		setupButtonUI(button_New_Assignment, "Dialog", 13, 75, Pos.CENTER, 225, 55);
		button_New_Assignment.setOnAction((_) -> { ControllerGradingSystem.performNewAssignment(); });
		
		studentListView.setFixedCellSize(50);
		
		studentListView.setLayoutX(20);
		studentListView.setLayoutY(105);
		studentListView.setPrefWidth(300);
		studentListView.setPrefHeight(410); // from y=105 to around y=525
		List<String> PostItemList = ModelGradingSytem.getStudentList();
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
		
		detailTitle = new Label("Title: ");
		detailTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
		
		studentListView.setOnMouseClicked(event -> {
		    selectedPost = studentListView.getSelectionModel().getSelectedItem();
		    
		    // post selected, mark the user as read
//		    updatingList(ModelGradingSytem.getPostList());
		    
		    unreadState = false;


		   // ControllerGradingSystem.performReadSpecificPost(selectedPost);	
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
        	    button_New_Assignment, studentListView, detailPane,

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
	 * Displays the details of the selected post and loads its associated replies.
	 *
	 * <p>This method also updates the enabled or disabled state of edit, delete,
	 * and reply controls based on ownership and deletion status.</p>
	 *
	 * @param selectedPost the post whose details should be shown
	 */
	protected static void displayPostDetails(String studentUsername) {

	    if (selectedPost == null) return;
	    //TODO: add view 

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

	        String errorMessage = ModelGradingSytem.newAssignment(title, content, maxScore, weight);

	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Create Assignment");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

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
	 * Opens a window that allows the current user to edit an existing post.
	 *
	 * @param post the post to edit
	 */
	protected static void showEditPostWindow(Post post) {
	   // Stage editStage = new Stage();
	   // editStage.setTitle("Edit Post");

	   // Pane editRoot = new Pane();
	   // Scene editScene = new Scene(editRoot, 520, 420);

	   // Label titleLabel = new Label("Edit Post");
	   // titleLabel.setFont(Font.font("Arial", 20));
	   // titleLabel.setLayoutX(20);
	   // titleLabel.setLayoutY(15);

	   // Label authorLabel = new Label("Editing as: " + (theUser == null ? "" : theUser.getUserName()));
	   // authorLabel.setFont(Font.font("Arial", 14));
	   // authorLabel.setLayoutX(20);
	   // authorLabel.setLayoutY(55);

	   // HBox threadContainer = new HBox();
	   // threadContainer.setLayoutX(20);
	   // threadContainer.setLayoutY(80);
	   // threadContainer.setAlignment(Pos.CENTER);
	   // threadContainer.setSpacing(10);
	   // 
	   // Label threadLabel = new Label("Thread:");
	   // threadLabel.setFont(Font.font("Arial"));
	   // 
	   // ChoiceBox<String> threadChoiceBox = new ChoiceBox<String>();
	   // threadChoiceBox.getItems().addAll(ModelGradingSytem.getAllThreads());
	   // threadChoiceBox.setValue(post.getThread());
	   // 
	   // threadContainer.getChildren().addAll(threadLabel, threadChoiceBox);
	   // 
	   // Label labelTitle = new Label("Title:");
	   // labelTitle.setLayoutX(20);
	   // labelTitle.setLayoutY(110);

	   // TextField tfTitle = new TextField(post.getTitle());
	   // tfTitle.setLayoutX(20);
	   // tfTitle.setLayoutY(135);
	   // tfTitle.setPrefWidth(480);

	   // Label labelContent = new Label("Content:");
	   // labelContent.setLayoutX(20);
	   // labelContent.setLayoutY(175);

	   // TextArea taContent = new TextArea(post.getContent());
	   // taContent.setLayoutX(20);
	   // taContent.setLayoutY(200);
	   // taContent.setPrefWidth(480);
	   // taContent.setPrefHeight(160);
	   // taContent.setWrapText(true);

	   // Button btnSave = new Button("Save");
	   // Button btnCancel = new Button("Cancel");

	   // setupButtonUI(btnSave, "Dialog", 16, 160, Pos.CENTER, 340, 375);
	   // setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 160, 375);

	   // btnCancel.setOnAction(e -> editStage.close());

	   // btnSave.setOnAction(e -> {
	   // 	String newThread = threadChoiceBox.getValue();
	   //     String newTitle = tfTitle.getText();
	   //     String newContent = taContent.getText();
	   //     
	   //     // You can rename this to match your actual ModelGradingSytem method
	   //     String errorMessage = ModelGradingSytem.editPost(post.getId(), newThread, theUser.getUserName(), newTitle, newContent);
	   //     System.out.println(errorMessage);
	   //     if (errorMessage != null && !errorMessage.isBlank()) {
	   //         Alert alert = new Alert(AlertType.ERROR);
	   //         alert.setTitle("Cannot Update Post");
	   //         alert.setHeaderText(null);
	    //        alert.setContentText(errorMessage);
	    //        alert.showAndWait();
	    //        return;
	    //    }

	    //    // Refresh list + keep selection updated
	    //    updatingList(ModelGradingSytem.getStudentList());

	    //    // Re-select the edited post if still present
	    //    Post refreshed = studentListView.getItems().stream()
	    //            .filter(p -> p.getId() == post.getId())
	    //            .findFirst()
	    //            .orElse(null);

	    //    selectedPost = refreshed;
	    //    if (refreshed != null) studentListView.getSelectionModel().select(refreshed);

	    //    // Update detail display
	    //    if (theView != null) ControllerGradingSystem.performReadSpecificPost(post);

	    //    editStage.close();
	    //});

	    //editRoot.getChildren().addAll(
	    //        titleLabel, authorLabel,
	    //        labelTitle, tfTitle,
	    //        labelContent, taContent,
	    //        btnCancel, btnSave,
	    //        threadContainer
	    //);

	    //editStage.setScene(editScene);
	    //editStage.initOwner(theStage);
	    //editStage.show();
	}
	
	/**
	 * Shows a confirmation dialog and, if confirmed, deletes the specified post.
	 *
	 * <p>After deletion, the forum list and detail pane are refreshed to reflect
	 * the updated post state.</p>
	 *
	 * @param post the post to delete
	 */
	protected static void confirmAndDeletePost(Post post) {

	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    confirm.setTitle("Delete Post");
	    confirm.setHeaderText("Are you sure you want to delete this post?");
	    confirm.setContentText("This cannot be undone.");

	    confirm.showAndWait().ifPresent(result -> {
	        if (result.getButtonData().isDefaultButton()) {

	            // rename to match your model
	            String errorMessage = ModelGradingSytem.deletePost(post.getId(), theUser.getUserName());

	            if (errorMessage != null && !errorMessage.isBlank()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Cannot Delete Post");
	                alert.setHeaderText(null);
	                alert.setContentText(errorMessage);
	                alert.showAndWait();
	                return;
	            }

	            // Refresh list
	            updatingList(ModelGradingSytem.getStudentList());

	            // Clear selection + detail UI
	            /*
	            studentListView.getSelectionModel().clearSelection();
	            selectedPost = null;

	            detailTitle.setText("Title: ");
	            detailAuthor.setText("Author: ");
	            detailContent.setText("Content: ");
	            */
	            //updatingList(ModelGradingSytem.getPostList());

	       //     Post refreshed = studentListView.getItems().stream()
	       //             .filter(p -> p.getId() == post.getId())
	       //             .findFirst()
	       //             .orElse(null);

	       //     selectedPost = refreshed;

	       //     if (refreshed != null) {
	       //         studentListView.getSelectionModel().select(refreshed);
	       //         if (theView != null) ControllerGradingSystem.performReadSpecificPost(refreshed);
	       //     } else {
	       //         studentListView.getSelectionModel().clearSelection();
	       //         selectedPost = null;

	       //         detailTitle.setText("Title: ");
	       //         detailAuthor.setText("Author: ");
	       //         detailContent.setText("Content: ");
	       //         repliesBox.getChildren().clear();

	       //         replyPane.setVisible(false);
	       //         replyPane.setManaged(false);
	       //         replyButton.setDisable(true);

	       //         editPostButton.setDisable(true);
	       //         deletePostButton.setDisable(true);
	       //     }
	            
	            /*
	            repliesBox.getChildren().clear();

	            // Hide reply UI again
	            replyPane.setVisible(false);
	            replyPane.setManaged(false);
	            replyButton.setDisable(true);

	            editPostButton.setDisable(true);
	            deletePostButton.setDisable(true);
	            */
	        }
	    });
	}
	
	/**
	 * Opens a window that allows the current user to edit an existing reply.
	 *
	 * @param reply the reply to edit
	 */
	protected static void showEditReplyWindow(Reply reply) {
	    Stage editStage = new Stage();
	    editStage.setTitle("Edit Reply");

	    Pane root = new Pane();
	    Scene scene = new Scene(root, 520, 320);

	    Label title = new Label("Edit Reply");
	    title.setFont(Font.font("Arial", 20));
	    title.setLayoutX(20);
	    title.setLayoutY(15);

	    Label author = new Label("Editing as: " + (theUser == null ? "" : theUser.getUserName()));
	    author.setFont(Font.font("Arial", 14));
	    author.setLayoutX(20);
	    author.setLayoutY(55);

	    Label contentLabel = new Label("Content:");
	    contentLabel.setLayoutX(20);
	    contentLabel.setLayoutY(90);

	    TextArea ta = new TextArea(reply.getContent());
	    ta.setLayoutX(20);
	    ta.setLayoutY(115);
	    ta.setPrefWidth(480);
	    ta.setPrefHeight(120);
	    ta.setWrapText(true);

	    Button btnSave = new Button("Save");
	    Button btnCancel = new Button("Cancel");

	    setupButtonUI(btnSave, "Dialog", 16, 160, Pos.CENTER, 340, 250);
	    setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 160, 250);

	    btnCancel.setOnAction(e -> editStage.close());

	    btnSave.setOnAction(e -> {
	        String newContent = ta.getText();

	        // rename these to your actual ModelGradingSytem method name
	        String errorMessage = ModelGradingSytem.editReply(reply.getId(), theUser.getUserName(), newContent);

	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Update Reply");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

	        // refresh current post details (reload replies)
	//        ControllerGradingSystem.performReadSpecificPost(selectedPost);

	        editStage.close();
	    });

	    root.getChildren().addAll(title, author, contentLabel, ta, btnCancel, btnSave);

	    editStage.setScene(scene);
	    editStage.initOwner(theStage);
	    editStage.show();
	}
	
	/**
	 * Shows a confirmation dialog and, if confirmed, deletes the specified reply.
	 *
	 * @param reply the reply to delete
	 */
	protected static void confirmAndDeleteReply(Reply reply) {
	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    confirm.setTitle("Delete Reply");
	    confirm.setHeaderText("Delete this reply?");
	    confirm.setContentText("This cannot be undone.");

	    confirm.showAndWait().ifPresent(result -> {
	        if (result == ButtonType.OK) {

	            // rename these to your actual ModelGradingSytem method name
	            String errorMessage = ModelGradingSytem.deleteReply(reply.getId(), theUser.getUserName());

	            if (errorMessage != null && !errorMessage.isBlank()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Cannot Delete Reply");
	                alert.setHeaderText(null);
	                alert.setContentText(errorMessage);
	                alert.showAndWait();
	                return;
	            }

	            // refresh UI
	   //         ControllerGradingSystem.performReadSpecificPost(selectedPost);
	        }
	    });
	}

	
}