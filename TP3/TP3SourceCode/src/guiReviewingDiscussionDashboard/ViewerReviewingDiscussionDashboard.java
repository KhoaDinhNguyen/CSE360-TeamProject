package guiReviewingDiscussionDashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

import database.Database;
import entityClasses.ThreadStore;
import entityClasses.User;
import guiStaffHome.ControllerStaffHome;

/**
 * Provides the JavaFX view for the reviewing discussion dashboard.
 *
 * <p>This class displays a dashboard of discussion metrics, allows the user to
 * choose which metrics appear on the cards, and supports filtering the dashboard
 * by student.</p>
 */
public class ViewerReviewingDiscussionDashboard {

    /**
     * The window width used by this view.
     */
    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;

    /**
     * The window height used by this view.
     */
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    /**
     * The page title label.
     */
    protected static Label label_PageTitle = new Label();

    /**
     * The label that displays the current user.
     */
    protected static Label label_UserDetails = new Label();

    /**
     * The label used to introduce the student filter.
     */
    protected static Label label_Select = new Label("View:");

    /**
     * The separator line for the header area.
     */
    protected static Line line_Separator1 = new Line(20, 95, width - 20, 95);

    /**
     * The separator line for the footer area.
     */
    protected static Line line_Separator4 = new Line(20, 525, width - 20, 525);

    /**
     * The return button.
     */
    protected static Button button_Return = new Button("Return");

    /**
     * The logout button.
     */
    protected static Button button_Logout = new Button("Logout");

    /**
     * The quit button.
     */
    protected static Button button_Quit = new Button("Quit");

    /**
     * The button used to configure the dashboard cards.
     */
    protected static Button button_Config = new Button("Configure Cards");

    /**
     * The student selection drop-down list.
     */
    protected static ChoiceBox<String> studentChoiceBox = new ChoiceBox<String>();

    /**
     * The singleton view instance used by this page.
     */
    private static ViewerReviewingDiscussionDashboard theView;

    /**
     * Reference to the shared database object.
     */
    private static Database theDatabase = applicationMain.FoundationsMain.database;

    /**
     * Reference to the forum thread store.
     */
    private static ThreadStore theThreadStore = guiForum.ModelForum.getThreadStore();

    /**
     * The thread table, if one is used by this view.
     */
    private static TableView<String> threadList = null;

    /**
     * The data backing the thread table.
     */
    private static ObservableList<String> threadData;

    /**
     * The JavaFX stage used by this view.
     */
    protected static Stage theStage;

    /**
     * The root pane that contains all widgets for this page.
     */
    protected static Pane theRootPane;

    /**
     * The current logged-in user.
     */
    protected static User theUser;

    /**
     * The shared scene used by this view.
     */
    private static Scene scene;

    /**
     * The four dashboard cards displayed on the page.
     */
    private static VBox[] cards = new VBox[4];

    /**
     * The available metric options shown in the configuration dialog.
     */
    private static final String[] OPTIONS = {
        "Read Posts per Student",
        "Created Posts per Student",
        "Created Unique Posts per Student",
        "Created Replies per Student",
        "Created Replies on Unique Posts per Student",
        "Post Length per Student",
        "Reply Length per Student"
    };

    /**
     * The list of users available for dashboard filtering.
     */
    protected static ArrayList<String> USERS = new ArrayList<String>();

    /**
     * Displays the reviewing discussion dashboard for the specified user.
     *
     * @param ps the stage used to display the dashboard scene
     * @param user the user currently viewing the dashboard
     */
    public static void displayReviewingDiscussionDashboard(Stage ps, User user) {
        theStage = ps;
        theUser = user;

        USERS.add("All Students");
        USERS.add("Admin");
        USERS.add("Alice");
        USERS.add("Bob");
        USERS.add("Charlie");
        USERS.add("David");
        USERS.add("Emma");
        USERS.add(user.getUserName());

        if (theView == null) theView = new ViewerReviewingDiscussionDashboard();

        theDatabase.getUserAccountDetails(user.getUserName());
        label_UserDetails.setText("User: " + theUser.getUserName());

        theStage.setTitle("Reviewing Discussion Dashboard");
        theStage.setScene(scene);
        theStage.show();
    }

    /**
     * Creates the reviewing discussion dashboard view.
     *
     * <p>This constructor is private because the class uses shared static UI state
     * and is intended to behave like a singleton view.</p>
     */
    private ViewerReviewingDiscussionDashboard() {
        theRootPane = new Pane();
        scene = new Scene(theRootPane, width, height);

        setupHeader();
        GridPane grid = buildDashboard();
        setupFooter();
        setupActions();

        theRootPane.getChildren().addAll(
            label_PageTitle,
            label_UserDetails,
            label_Select,
            studentChoiceBox,
            line_Separator1,
            line_Separator4,
            button_Config,
            button_Return,
            button_Logout,
            button_Quit,
            grid
        );
    }

    /**
     * Initializes the header section of the dashboard.
     */
    private void setupHeader() {
        label_PageTitle.setText("Discussion Dashboard");
        setupLabelUI(label_PageTitle, 28, width, Pos.CENTER, 0, 5);
        setupLabelUI(label_UserDetails, 20, width, Pos.BASELINE_LEFT, 20, 55);

        setupButtonUI(button_Config, 16, 180, Pos.CENTER, 20, 110);
        setupLabelUI(label_Select, 16, 60, Pos.CENTER_LEFT, 230, 110);

        studentChoiceBox.setLayoutX(280);
        studentChoiceBox.setLayoutY(110);
        studentChoiceBox.setPrefWidth(180);
        studentChoiceBox.setPrefHeight(28);

        for (String user : USERS) {
            studentChoiceBox.getItems().add(user);
        }

        studentChoiceBox.setValue("All Students");
    }

    /**
     * Builds the dashboard card layout.
     *
     * @return the grid pane containing the four metric cards
     */
    private GridPane buildDashboard() {
        GridPane grid = new GridPane();
        grid.setLayoutX(120);
        grid.setLayoutY(150);
        grid.setHgap(25);
        grid.setVgap(25);

        for (int i = 0; i < 4; i++) {
            cards[i] = new VBox(8);
            cards[i].setPadding(new Insets(10));
            cards[i].setPrefSize(380, 140);
            cards[i].setStyle("-fx-border-color:black;");
            updateCard(i, "Empty", "");
            grid.add(cards[i], i % 2, i / 2);
        }

        return grid;
    }

    /**
     * Initializes the footer buttons.
     */
    private void setupFooter() {
        setupButtonUI(button_Return, 18, 250, Pos.CENTER, 20, 540);
        setupButtonUI(button_Logout, 18, 250, Pos.CENTER, 300, 540);
        setupButtonUI(button_Quit, 18, 250, Pos.CENTER, 580, 540);
    }

    /**
     * Sets up the actions for the dashboard controls.
     */
    private void setupActions() {
        button_Return.setOnAction(e -> ControllerStaffHome.performReturn());

        button_Config.setOnAction(e -> showChecklistModal());

        studentChoiceBox.setOnAction(e -> {
            ControllerReviewingDiscussionDashboard.performStudentFilter(
                studentChoiceBox.getValue()
            );
        });
    }

    /**
     * Opens a modal window that lets the user choose which metrics appear on the cards.
     *
     * <p>The first four selected metrics are used to fill Card 1 through Card 4.</p>
     */
    private void showChecklistModal() {
        Stage modal = new Stage();
        modal.initOwner(theStage);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Select Metrics (Top 4 Used)");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        ArrayList<CheckBox> checks = new ArrayList<>();
        ArrayList<String> currentMetrics = ControllerReviewingDiscussionDashboard.getSelectedMetrics();

        for (String option : OPTIONS) {
            CheckBox cb = new CheckBox(option);

            if (currentMetrics.contains(option)) {
                cb.setSelected(true);
            }

            cb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                long count = checks.stream().filter(CheckBox::isSelected).count();

                if (count >= 4) {
                    for (CheckBox box : checks) {
                        if (!box.isSelected()) box.setDisable(true);
                    }
                } else {
                    for (CheckBox box : checks) {
                        box.setDisable(false);
                    }
                }
            });

            checks.add(cb);
            root.getChildren().add(cb);
        }

        Label info = new Label("First 4 selected items fill Card 1 to Card 4");
        Button apply = new Button("Apply");
        Button cancel = new Button("Cancel");
        HBox row = new HBox(10, apply, cancel);

        apply.setOnAction(e -> {
            ArrayList<String> selected = new ArrayList<>();

            for (CheckBox cb : checks) {
                if (cb.isSelected()) {
                    selected.add(cb.getText());
                }
            }

            ControllerReviewingDiscussionDashboard.performApplyMetrics(selected);
            modal.close();
        });

        cancel.setOnAction(e -> modal.close());

        root.getChildren().addAll(info, row);

        modal.setScene(new Scene(root, 320, 320));
        modal.showAndWait();
    }

    /**
     * Refreshes the four dashboard cards using the selected metric list.
     *
     * @param selected the metrics selected by the user
     * @param student the selected student filter
     */
    protected static void refreshCards(ArrayList<String> selected, String student) {
        for (int i = 0; i < 4; i++) {
            if (i < selected.size()) {
                updateCard(i, selected.get(i), student);
            } else {
                updateCard(i, "Empty", student);
            }
        }
    }

    /**
     * Updates one dashboard card with the given metric and student filter.
     *
     * @param index the card index to update
     * @param type the metric name to display
     * @param student the selected student filter
     */
    private static void updateCard(int index, String type, String student) {
        cards[index].getChildren().clear();

        double result = -1;

        switch (type) {
            case "Read Posts per Student":
                result = ModelReviewingDiscussionDashboard.averageReadPost(student, USERS);
                break;

            case "Created Posts per Student":
                result = ModelReviewingDiscussionDashboard.numberOfCreatedPost(student, USERS);
                break;

            case "Created Unique Posts per Student":
                result = ModelReviewingDiscussionDashboard.averageReadPost("", USERS);
                break;

            case "Created Replies per Student":
                result = ModelReviewingDiscussionDashboard.numberOfCreatedReply(student, USERS);
                break;

            case "Created Replies on Unique Posts per Student":
                result = ModelReviewingDiscussionDashboard.averageReadPost("", USERS);
                break;

            case "Post Length per Student":
                result = ModelReviewingDiscussionDashboard.averagePostLength(student, USERS);
                break;

            case "Reply Length per Student":
                result = ModelReviewingDiscussionDashboard.averageReplyLength(student, USERS);
                break;
        }

        cards[index].getChildren().addAll(
            new Label("Card " + (index + 1)),
            new Label(type)
        );

        if (result != -1) {
            cards[index].getChildren().add(new Label(String.valueOf(result)));
        }
    }

    /**
     * Initializes a label with standard layout and font settings.
     *
     * @param l the label to configure
     * @param f the font size
     * @param w the minimum width of the label
     * @param p the label alignment
     * @param x the x-coordinate of the label
     * @param y the y-coordinate of the label
     */
    private static void setupLabelUI(Label l, double f, double w, Pos p, double x, double y) {
        l.setFont(Font.font("Arial", f));
        l.setMinWidth(w);
        l.setAlignment(p);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }

    /**
     * Initializes a button with standard layout and font settings.
     *
     * @param b the button to configure
     * @param f the font size
     * @param w the minimum width of the button
     * @param p the button alignment
     * @param x the x-coordinate of the button
     * @param y the y-coordinate of the button
     */
    private static void setupButtonUI(Button b, double f, double w, Pos p, double x, double y) {
        b.setFont(Font.font("Dialog", f));
        b.setMinWidth(w);
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);
    }

    /**
     * Reloads the thread data into the thread table, if the table has been created.
     */
    protected static void loadThreadData() {
        if (threadList != null) {
            ArrayList<String> allThreads = theThreadStore.getAllThreads();
            threadData = FXCollections.observableArrayList(allThreads);
            threadList.setItems(threadData);
        }
    }

    /**
     * Displays an alert dialog and returns the result.
     *
     * @param type the alert type to display
     * @param title the alert title
     * @param header the alert header text
     * @param msg the alert message text
     * @return the user's response from the alert dialog
     */
    protected static Optional<ButtonType> displayAlert(Alert.AlertType type, String title, String header, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        return alert.showAndWait();
    }
}