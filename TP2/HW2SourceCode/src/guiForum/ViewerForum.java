package guiForum;

import java.util.List;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import database.Database;
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
import javafx.scene.control.ListView;
import CRUD.Post;
import CRUD.Reply;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Provides the JavaFX view for the discussion forum.
 *
 * <p>This class builds and manages the forum user interface, including the post list,
 * post details, reply area, search controls, and actions for creating, editing,
 * deleting, and viewing posts and replies.</p>
 */
public class ViewerForum {
	
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	// would contain the widgets needed for the user to play the assigned role.
	
	private static ListView<Post> postListView;
	
	
	private static VBox detailPane;
	private static Label detailThread;
	private static Label detailTitle;
	private static Label detailAuthor;
	private static Label detailContent;
	private static ScrollPane detailScrollPane;
	protected static Button button_NewPost = new Button("New Post");
	
	// Reply UI (shown only when a post is selected)
	private static VBox replyPane;
	private static TextArea replyTextArea;
	private static Button replyButton;
	
	private static VBox repliesBox;
	private static Label repliesLabel;

	// Track which post is selected
	private static Post selectedPost;
	
	// Filter
	
	private static TextField tfSearch;
	private static Button button_Search;
	private static Button button_Clear;
	
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Return = new Button("Return");
	protected static Button button_Quit = new Button("Quit");
	
	private static Button editPostButton = new Button("Edit");
	private static Button deletePostButton = new Button("Delete");
	
	private static ViewerForum theView;
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	private static Scene theForumScene;	// The shared Scene each invocation populates
	protected static final int theRole = 2;
	
	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	
	/**
	 * Displays the forum view for the specified user on the provided stage.
	 *
	 * @param ps the stage used to display the forum scene
	 * @param user the user currently viewing the forum
	 */
	public static void displayViewerForum(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		
		if (postListView == null) {
			postListView = new ListView<>();
		}
		
		if (theView == null) theView = new ViewerForum();		// Instantiate singleton if needed
		
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
	private ViewerForum() {
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theForumScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Role1 Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
		
		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		setupButtonUI(button_NewPost, "Dialog", 16, 100, Pos.CENTER, 200, 55);
		button_NewPost.setOnAction((_) -> { showAddPostWindow(); });
		
		// Filter Search
		
		// Search TextField
		tfSearch = new TextField();
		tfSearch.setLayoutX(320);
		tfSearch.setLayoutY(55);
		tfSearch.setPrefWidth(160);
		tfSearch.setPrefHeight(28);
		tfSearch.setPromptText("Enter keyword...");

		// Search Button
		button_Search = new Button("Search");
		setupButtonUI(button_Search, "Dialog", 13, 75, Pos.CENTER, 490, 55);

		button_Search.setOnAction(e -> {
		    String keyword = tfSearch.getText();
		    List<Post> results = ModelForum.filterPosts(keyword);
		    updatingList(results);
		});
		
		// Clear Button		
		button_Clear = new Button("Clear");
		setupButtonUI(button_Clear, "Dialog", 13, 75, Pos.CENTER, 570, 55);

		button_Clear.setOnAction(e -> {
		    tfSearch.clear();
		    updatingList(ModelForum.getPostList());
		});

		
		// GUI Area 2
		
		postListView.setLayoutX(20);
		postListView.setLayoutY(105);
		postListView.setPrefWidth(300);
		postListView.setPrefHeight(410); // from y=105 to around y=525
		List<Post> PostItemList = ModelForum.getPostList();
		updatingList(PostItemList);
				
		
		detailPane = new VBox(10);
//		detailPane.setLayoutX(340);     // to the right of the list
//		detailPane.setLayoutY(105);
//		detailPane.setPrefWidth(width - 360);
//		detailPane.setPrefHeight(410);
//		detailPane.setStyle("-fx-padding: 15; -fx-border-color: #cccccc; -fx-border-width: 1;");

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

		detailThread = new Label("Thread: ");
		detailAuthor = new Label("Author: ");
		detailContent = new Label("Content: ");
		detailContent.setWrapText(true);

		detailPane.getChildren().addAll(detailTitle, detailThread, detailAuthor, detailContent);
		
		// Delete and Edit Button
		// disabled until a post is selected (and you are allowed)
		editPostButton.setDisable(true);
		deletePostButton.setDisable(true);

		editPostButton.setOnAction(e -> {
		    if (selectedPost == null) return;
		    showEditPostWindow(selectedPost);
		});

		deletePostButton.setOnAction(e -> {
		    if (selectedPost == null) return;
		    confirmAndDeletePost(selectedPost);
		});

		// Put buttons in a small row
		VBox postActions = new VBox(6, editPostButton, deletePostButton);
		// OR use HBox if you want them side-by-side:
		// HBox postActions = new HBox(10, editPostButton, deletePostButton);

		detailPane.getChildren().add(postActions);
		// Replies section
		repliesLabel = new Label("Replies:");
		repliesLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

		repliesBox = new VBox(6);
		repliesBox.setStyle("-fx-padding: 5 0 10 0;");

		// Add to detail pane BEFORE replyPane
		detailPane.getChildren().addAll(repliesLabel, repliesBox);
		
		postListView.setOnMouseClicked(event -> {
		    selectedPost = postListView.getSelectionModel().getSelectedItem();
		    displayPostDetails(selectedPost);
		});
		
		replyPane = new VBox(8);
		replyPane.setStyle("-fx-padding: 10 0 0 0;");

		Label replyLabel = new Label("Reply:");
		replyTextArea = new TextArea();
		replyTextArea.setPrefHeight(90);
		replyTextArea.setWrapText(true);
		replyTextArea.setPromptText("Write your reply...");

		replyButton = new Button("Reply");
		replyButton.setDisable(true);     
		replyPane.setVisible(false);      
		replyPane.setManaged(false);      

		replyButton.setOnAction(e -> {
		    if (selectedPost == null) return;

		    String replyText = replyTextArea.getText();
		    String author = theUser.getUserName();
		    // basic validation
		    int parentId = selectedPost.getId();
		    String errorMessage = ModelForum.addReply(replyText, author, parentId);
		    
		    
	        // If Model returns error → show it
	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Create Post");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }
	        
	        displayPostDetails(selectedPost);

		    // For now just clear input and show success
		    replyTextArea.clear();
		    Alert ok = new Alert(AlertType.INFORMATION);
		    ok.setTitle("Reply Posted");
		    ok.setHeaderText(null);
		    ok.setContentText("Your reply was posted.");
		    ok.showAndWait();
		});

		replyPane.getChildren().addAll(replyLabel, replyTextArea, replyButton);
		detailPane.getChildren().add(replyPane);
		// GUI Area 3
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 540);
        button_Logout.setOnAction((_) -> {ControllerForum.performLogout(); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((_) -> {ControllerForum.performQuit(); });
        
        
        // Add in Functionality

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
        theRootPane.getChildren().addAll(
        	    label_PageTitle, label_UserDetails, line_Separator1,
        	    line_Separator4, button_Logout, button_Quit,
        	    button_NewPost, postListView, detailPane, detailScrollPane, 
        	    tfSearch, button_Search, button_Clear
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
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	/**
	 * Replaces the contents of the forum post list view with the provided posts.
	 *
	 * @param newPosts the posts to display in the list view
	 */
	private static void updatingList(List<Post> newPosts) {
		postListView.getItems().setAll(newPosts);
		
	}
	
	/**
	 * Displays the details of the selected post and loads its associated replies.
	 *
	 * <p>This method also updates the enabled or disabled state of edit, delete,
	 * and reply controls based on ownership and deletion status.</p>
	 *
	 * @param selectedPost the post whose details should be shown
	 */
	private void displayPostDetails(Post selectedPost) {

	    if (selectedPost == null) return;

	    detailThread.setText("Thread: " + selectedPost.getThread());;
	    detailTitle.setText("Title: " + selectedPost.getTitle());
	    detailAuthor.setText("Author: " + selectedPost.getAuthor());
	    detailContent.setText("Content: " + selectedPost.getContent());

	    // Enable edit/delete only if current user is author
	    boolean isOwner = (theUser != null)
	            && (selectedPost.getAuthor() != null)
	            && selectedPost.getAuthor().equals(theUser.getUserName());

	    boolean isDeleted = selectedPost.isDeleted();

	    editPostButton.setDisable(!isOwner || isDeleted);
	    deletePostButton.setDisable(!isOwner || isDeleted);
	    
	    replyButton.setDisable(isDeleted);
	    replyTextArea.setDisable(isDeleted);

	    // Clear old replies
	    repliesBox.getChildren().clear();

	    // Load replies
	    List<Reply> replies = ModelForum.getRepliesByPostId(selectedPost.getId());

	    for (Reply r : replies) {

	        Label replyLabel = new Label(r.getAuthor() + ": " + r.getContent());
	        replyLabel.setWrapText(true);
	        replyLabel.setPickOnBounds(true); // helps clicking

	        // TEMP: attach menu to EVERY reply so you can test it works
	        ContextMenu menu = new ContextMenu();

	        MenuItem editItem = new MenuItem("Edit");
	        editItem.setOnAction(e -> {
	            showEditReplyWindow(r);  // should open your edit window
	        });

	        MenuItem deleteItem = new MenuItem("Delete");
	        deleteItem.setOnAction(e -> {
	        	confirmAndDeleteReply(r); // should open confirm + delete
	        });

	        menu.getItems().addAll(editItem, deleteItem);
	        replyLabel.setContextMenu(menu);

	        repliesBox.getChildren().add(replyLabel);
	    }
	    
	    // Enable reply UI
	    replyPane.setVisible(true);
	    replyPane.setManaged(true);
	    replyButton.setDisable(isDeleted);
	    replyTextArea.setDisable(isDeleted);
	}	
	
	/**
	 * Opens a window that allows the current user to create a new post.
	 */
	private static void showAddPostWindow() {
	    Stage addStage = new Stage();
	    addStage.setTitle("Create New Post");

	    Pane addRoot = new Pane();
	    Scene addScene = new Scene(addRoot, 520, 420);

	    Label titleLabel = new Label("Create a New Post");
	    titleLabel.setFont(Font.font("Arial", 20));
	    titleLabel.setLayoutX(20);
	    titleLabel.setLayoutY(15);

	    Label authorLabel = new Label("Posting as: " + (theUser == null ? "" : theUser.getUserName()));
	    authorLabel.setFont(Font.font("Arial", 14));
	    authorLabel.setLayoutX(20);
	    authorLabel.setLayoutY(55);

	    HBox threadContainer = new HBox();
	    threadContainer.setLayoutX(20);
	    threadContainer.setLayoutY(80);
	    threadContainer.setAlignment(Pos.CENTER);
	    threadContainer.setSpacing(10);
	    
	    Label threadLabel = new Label("Thread:");
	    threadLabel.setFont(Font.font("Arial"));
	    
	    ChoiceBox<String> threadChoiceBox = new ChoiceBox<String>();
	    threadChoiceBox.getItems().addAll(ModelForum.getAllThreads());
	    threadChoiceBox.setValue("General");
	    
	    threadContainer.getChildren().addAll(threadLabel, threadChoiceBox);

	    Label labelTitle = new Label("Title:");
	    labelTitle.setLayoutX(20);
	    labelTitle.setLayoutY(110);

	    TextField tfTitle = new TextField();
	    tfTitle.setLayoutX(20);
	    tfTitle.setLayoutY(135);
	    tfTitle.setPrefWidth(480);
	    tfTitle.setPromptText("Enter a short, clear title");

	    Label labelContent = new Label("Content:");
	    labelContent.setLayoutX(20);
	    labelContent.setLayoutY(175);

	    TextArea taContent = new TextArea();
	    taContent.setLayoutX(20);
	    taContent.setLayoutY(200);
	    taContent.setPrefWidth(480);
	    taContent.setPrefHeight(160);
	    taContent.setWrapText(true);
	    taContent.setPromptText("Write your post here...");

	    Button btnPost = new Button("Post");
	    Button btnCancel = new Button("Cancel");

	    // Use your style helper
	    setupButtonUI(btnPost, "Dialog", 16, 160, Pos.CENTER, 340, 375);
	    setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 160, 375);

	    btnCancel.setOnAction(e -> addStage.close());

	    btnPost.setOnAction(e -> {

	    	String thread = threadChoiceBox.getValue();
	        String title = tfTitle.getText();
	        String content = taContent.getText();
	        String author = theUser.getUserName();

	        // Let ModelForum handle all validation
	        String errorMessage = ModelForum.addPost(thread, title, content, author);

	        // If Model returns error → show it
	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Create Post");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

	        // Success
	        updatingList(ModelForum.getPostList());
	        addStage.close();
	    });

	    addRoot.getChildren().addAll(
	        titleLabel, authorLabel,
	        labelTitle, tfTitle,
	        labelContent, taContent,
	        btnCancel, btnPost,
	        threadContainer
	    );

	    addStage.setScene(addScene);
	    addStage.initOwner(theStage); // makes it feel attached to main window
	    addStage.show();
	}
	
	/**
	 * Opens a window that allows the current user to edit an existing post.
	 *
	 * @param post the post to edit
	 */
	private static void showEditPostWindow(Post post) {
	    Stage editStage = new Stage();
	    editStage.setTitle("Edit Post");

	    Pane editRoot = new Pane();
	    Scene editScene = new Scene(editRoot, 520, 420);

	    Label titleLabel = new Label("Edit Post");
	    titleLabel.setFont(Font.font("Arial", 20));
	    titleLabel.setLayoutX(20);
	    titleLabel.setLayoutY(15);

	    Label authorLabel = new Label("Editing as: " + (theUser == null ? "" : theUser.getUserName()));
	    authorLabel.setFont(Font.font("Arial", 14));
	    authorLabel.setLayoutX(20);
	    authorLabel.setLayoutY(55);

	    HBox threadContainer = new HBox();
	    threadContainer.setLayoutX(20);
	    threadContainer.setLayoutY(80);
	    threadContainer.setAlignment(Pos.CENTER);
	    threadContainer.setSpacing(10);
	    
	    Label threadLabel = new Label("Thread:");
	    threadLabel.setFont(Font.font("Arial"));
	    
	    ChoiceBox<String> threadChoiceBox = new ChoiceBox<String>();
	    threadChoiceBox.getItems().addAll(ModelForum.getAllThreads());
	    threadChoiceBox.setValue(post.getThread());
	    
	    threadContainer.getChildren().addAll(threadLabel, threadChoiceBox);
	    
	    Label labelTitle = new Label("Title:");
	    labelTitle.setLayoutX(20);
	    labelTitle.setLayoutY(110);

	    TextField tfTitle = new TextField(post.getTitle());
	    tfTitle.setLayoutX(20);
	    tfTitle.setLayoutY(135);
	    tfTitle.setPrefWidth(480);

	    Label labelContent = new Label("Content:");
	    labelContent.setLayoutX(20);
	    labelContent.setLayoutY(175);

	    TextArea taContent = new TextArea(post.getContent());
	    taContent.setLayoutX(20);
	    taContent.setLayoutY(200);
	    taContent.setPrefWidth(480);
	    taContent.setPrefHeight(160);
	    taContent.setWrapText(true);

	    Button btnSave = new Button("Save");
	    Button btnCancel = new Button("Cancel");

	    setupButtonUI(btnSave, "Dialog", 16, 160, Pos.CENTER, 340, 375);
	    setupButtonUI(btnCancel, "Dialog", 16, 160, Pos.CENTER, 160, 375);

	    btnCancel.setOnAction(e -> editStage.close());

	    btnSave.setOnAction(e -> {
	    	String newThread = threadChoiceBox.getValue();
	        String newTitle = tfTitle.getText();
	        String newContent = taContent.getText();

	        // You can rename this to match your actual ModelForum method
	        String errorMessage = ModelForum.editPost(post.getId(), newThread, theUser.getUserName(), newTitle, newContent);

	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Update Post");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

	        // Refresh list + keep selection updated
	        updatingList(ModelForum.getPostList());

	        // Re-select the edited post if still present
	        Post refreshed = postListView.getItems().stream()
	                .filter(p -> p.getId() == post.getId())
	                .findFirst()
	                .orElse(null);

	        selectedPost = refreshed;
	        if (refreshed != null) postListView.getSelectionModel().select(refreshed);

	        // Update detail display
	        if (theView != null) theView.displayPostDetails(refreshed);

	        editStage.close();
	    });

	    editRoot.getChildren().addAll(
	            titleLabel, authorLabel,
	            labelTitle, tfTitle,
	            labelContent, taContent,
	            btnCancel, btnSave,
	            threadContainer
	    );

	    editStage.setScene(editScene);
	    editStage.initOwner(theStage);
	    editStage.show();
	}
	
	/**
	 * Shows a confirmation dialog and, if confirmed, deletes the specified post.
	 *
	 * <p>After deletion, the forum list and detail pane are refreshed to reflect
	 * the updated post state.</p>
	 *
	 * @param post the post to delete
	 */
	private static void confirmAndDeletePost(Post post) {

	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    confirm.setTitle("Delete Post");
	    confirm.setHeaderText("Are you sure you want to delete this post?");
	    confirm.setContentText("This cannot be undone.");

	    confirm.showAndWait().ifPresent(result -> {
	        if (result.getButtonData().isDefaultButton()) {

	            // rename to match your model
	            String errorMessage = ModelForum.deletePost(post.getId(), theUser.getUserName());

	            if (errorMessage != null && !errorMessage.isBlank()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Cannot Delete Post");
	                alert.setHeaderText(null);
	                alert.setContentText(errorMessage);
	                alert.showAndWait();
	                return;
	            }

	            // Refresh list
	            updatingList(ModelForum.getPostList());

	            // Clear selection + detail UI
	            /*
	            postListView.getSelectionModel().clearSelection();
	            selectedPost = null;

	            detailTitle.setText("Title: ");
	            detailAuthor.setText("Author: ");
	            detailContent.setText("Content: ");
	            */
	            //updatingList(ModelForum.getPostList());

	            Post refreshed = postListView.getItems().stream()
	                    .filter(p -> p.getId() == post.getId())
	                    .findFirst()
	                    .orElse(null);

	            selectedPost = refreshed;

	            if (refreshed != null) {
	                postListView.getSelectionModel().select(refreshed);
	                if (theView != null) theView.displayPostDetails(refreshed);
	            } else {
	                postListView.getSelectionModel().clearSelection();
	                selectedPost = null;

	                detailTitle.setText("Title: ");
	                detailAuthor.setText("Author: ");
	                detailContent.setText("Content: ");
	                repliesBox.getChildren().clear();

	                replyPane.setVisible(false);
	                replyPane.setManaged(false);
	                replyButton.setDisable(true);

	                editPostButton.setDisable(true);
	                deletePostButton.setDisable(true);
	            }
	            
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
	private void showEditReplyWindow(Reply reply) {
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

	        // rename these to your actual ModelForum method name
	        String errorMessage = ModelForum.editReply(reply.getId(), theUser.getUserName(), newContent);

	        if (errorMessage != null && !errorMessage.isBlank()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Cannot Update Reply");
	            alert.setHeaderText(null);
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return;
	        }

	        // refresh current post details (reload replies)
	        displayPostDetails(selectedPost);

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
	private void confirmAndDeleteReply(Reply reply) {
	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    confirm.setTitle("Delete Reply");
	    confirm.setHeaderText("Delete this reply?");
	    confirm.setContentText("This cannot be undone.");

	    confirm.showAndWait().ifPresent(result -> {
	        if (result == ButtonType.OK) {

	            // rename these to your actual ModelForum method name
	            String errorMessage = ModelForum.deleteReply(reply.getId(), theUser.getUserName());

	            if (errorMessage != null && !errorMessage.isBlank()) {
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Cannot Delete Reply");
	                alert.setHeaderText(null);
	                alert.setContentText(errorMessage);
	                alert.showAndWait();
	                return;
	            }

	            // refresh UI
	            displayPostDetails(selectedPost);
	        }
	    });
	}
	
}