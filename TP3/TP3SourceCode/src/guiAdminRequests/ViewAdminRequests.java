package guiAdminRequests;

import java.util.List;

import entityClasses.AdminRequest;
import entityClasses.AdminRequestAction;
import entityClasses.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*******
 * <p> Title: ViewAdminRequests Class. </p>
 *
 * <p> Description: The Java/FX-based Admin Request Center. This page is shared
 * by staff and admin users. It displays open and closed requests, request
 * details, and action history. Staff users may create and reopen requests,
 * while admin users may document actions and close requests.</p>
 *
 * <p> The class follows the singleton design pattern used throughout the
 * project. All access to this page should begin through the
 * {@code displayAdminRequests} method. </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Daniel Prada
 *
 * @version 1.00        2026-04-20 Initial version
 */

public class ViewAdminRequests {

    /*-*******************************************************************************************

    Attributes

     */

    // Application sizing
    private static final double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static final double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    // Singleton reference
    private static ViewAdminRequests theView;

    // Shared stage / user references
    protected static Stage theStage;
    protected static User theUser;

    // Scene graph
    private static Scene theScene;
    private static BorderPane rootPane;

    // Labels
    protected static Label label_PageTitle = new Label("Admin Request Center");
    protected static Label label_UserDetails = new Label();
    protected static Label label_OpenRequests = new Label("Open Requests");
    protected static Label label_ClosedRequests = new Label("Closed Requests");
    protected static Label label_RequestDetails = new Label("Selected Request Details");
    protected static Label label_ActionHistory = new Label("Action History");
    protected static Label label_RequestType = new Label("Request Type");
    protected static Label label_NewRequestDescription = new Label("New / Updated Description");
    protected static Label label_ActionNote = new Label("Admin Action / Closing Note");

    // Request lists
    protected static ListView<AdminRequest> list_OpenRequests = new ListView<AdminRequest>();
    protected static ListView<AdminRequest> list_ClosedRequests = new ListView<AdminRequest>();
    protected static ListView<AdminRequestAction> list_ActionHistory = new ListView<AdminRequestAction>();

    // Text areas
    protected static TextArea textarea_RequestDetails = new TextArea();
    protected static TextArea textarea_NewRequestDescription = new TextArea();
    protected static TextArea textarea_ActionNote = new TextArea();

    // Input controls
    protected static ComboBox<String> combobox_RequestType = new ComboBox<String>();

    // Buttons
    protected static Button button_CreateRequest = new Button("Create Request");
    protected static Button button_ReopenRequest = new Button("Reopen Selected Request");
    protected static Button button_AddAction = new Button("Add Action Note");
    protected static Button button_CloseRequest = new Button("Close Selected Request");
    protected static Button button_ViewOriginal = new Button("View Original Closed Request");
    protected static Button button_Refresh = new Button("Refresh");
    protected static Button button_Return = new Button("Return");

    // Current selection
    private static AdminRequest selectedRequest;

    /*-*******************************************************************************************

    Constructors

     */

    /**********
     * <p> Method: displayAdminRequests(Stage ps, User user) </p>
     *
     * <p> Description: This method is the single entry point from outside this
     * package to cause the Admin Request Center page to be displayed.</p>
     *
     * @param ps specifies the JavaFX Stage to be used for this GUI and its methods
     *
     * @param user specifies the User for this GUI and its methods
     *
     */
    public static void displayAdminRequests(Stage ps, User user) {
        theStage = ps;
        theUser = user;

        if (theView == null) theView = new ViewAdminRequests();

        refreshView();
        theStage.setTitle("CSE 360 Foundations: Admin Request Center");
        theStage.setScene(theScene);
        theStage.show();
    }

    /**********
     * <p> Method: ViewAdminRequests() </p>
     *
     * <p> Description: This constructor initializes all static elements of the
     * graphical user interface. It configures widget positions, styling, sizing,
     * and event handlers. Dynamic content is refreshed when
     * {@code displayAdminRequests} is invoked.</p>
     *
     */
    private ViewAdminRequests() {
        rootPane = new BorderPane();
        rootPane.setPadding(new Insets(12));
        theScene = new Scene(rootPane, width, height);

        styleHeader(label_PageTitle, 26);
        styleHeader(label_RequestDetails, 18);
        styleHeader(label_ActionHistory, 18);
        styleHeader(label_OpenRequests, 18);
        styleHeader(label_ClosedRequests, 18);

        textarea_RequestDetails.setEditable(false);
        textarea_RequestDetails.setWrapText(true);
        textarea_NewRequestDescription.setWrapText(true);
        textarea_ActionNote.setWrapText(true);

        list_OpenRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                list_ClosedRequests.getSelectionModel().clearSelection();
                selectedRequest = newValue;
                updateSelectedRequest();
            }
        });

        list_ClosedRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                list_OpenRequests.getSelectionModel().clearSelection();
                selectedRequest = newValue;
                updateSelectedRequest();
            }
        });

        button_CreateRequest.setOnAction((_) -> { ControllerAdminRequests.performCreateRequest(); });
        button_ReopenRequest.setOnAction((_) -> { ControllerAdminRequests.performReopenRequest(); });
        button_AddAction.setOnAction((_) -> { ControllerAdminRequests.performAddAction(); });
        button_CloseRequest.setOnAction((_) -> { ControllerAdminRequests.performCloseRequest(); });
        button_ViewOriginal.setOnAction((_) -> { ControllerAdminRequests.performViewOriginalRequest(); });
        button_Refresh.setOnAction((_) -> { ControllerAdminRequests.performRefresh(); });
        button_Return.setOnAction((_) -> { ControllerAdminRequests.performReturn(); });

        VBox leftPanel = new VBox(10,
                label_OpenRequests,
                list_OpenRequests,
                label_ClosedRequests,
                list_ClosedRequests);
        leftPanel.setPadding(new Insets(0, 12, 0, 0));
        leftPanel.setPrefWidth(360);
        VBox.setVgrow(list_OpenRequests, Priority.ALWAYS);
        VBox.setVgrow(list_ClosedRequests, Priority.ALWAYS);

        VBox rightPanel = new VBox(10,
                label_RequestDetails,
                textarea_RequestDetails,
                label_ActionHistory,
                list_ActionHistory,
                label_RequestType,
                combobox_RequestType,
                label_NewRequestDescription,
                textarea_NewRequestDescription,
                label_ActionNote,
                textarea_ActionNote,
                buildButtonRow());

        VBox.setVgrow(textarea_RequestDetails, Priority.ALWAYS);
        VBox.setVgrow(list_ActionHistory, Priority.ALWAYS);
        VBox.setVgrow(textarea_NewRequestDescription, Priority.SOMETIMES);
        VBox.setVgrow(textarea_ActionNote, Priority.SOMETIMES);

        HBox mainContent = new HBox(12, leftPanel, rightPanel);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        VBox topPanel = new VBox(6, label_PageTitle, label_UserDetails);
        topPanel.setAlignment(Pos.CENTER_LEFT);

        rootPane.setTop(topPanel);
        rootPane.setCenter(mainContent);
    }

    /*-*******************************************************************************************

    Public / protected methods used by the controller

     */

    /**********
     * <p> Method: refreshView() </p>
     *
     * <p> Description: Refreshes all visible request-center elements, including
     * the open and closed request lists, user role visibility, and current
     * selection details.</p>
     *
     */
    protected static void refreshView() {
        label_UserDetails.setText("User: " + theUser.getUserName() + roleSuffix(theUser));

        combobox_RequestType.setItems(FXCollections.observableArrayList(ModelAdminRequests.getRequestTypes()));
        if (combobox_RequestType.getSelectionModel().isEmpty()) {
            combobox_RequestType.getSelectionModel().selectLast();
        }

        list_OpenRequests.setItems(FXCollections.observableArrayList(ModelAdminRequests.getOpenRequests()));
        list_ClosedRequests.setItems(FXCollections.observableArrayList(ModelAdminRequests.getClosedRequests()));

        boolean isStaff = theUser.getNewStaff();
        boolean isAdmin = theUser.getAdminRole();

        button_CreateRequest.setVisible(isStaff);
        button_CreateRequest.setManaged(isStaff);

        button_ReopenRequest.setVisible(isStaff);
        button_ReopenRequest.setManaged(isStaff);

        button_AddAction.setVisible(isAdmin);
        button_AddAction.setManaged(isAdmin);

        button_CloseRequest.setVisible(isAdmin);
        button_CloseRequest.setManaged(isAdmin);

        updateSelectedRequest();
    }

    /**********
     * <p> Method: refreshSelectedRequest() </p>
     *
     * <p> Description: Refreshes the details and action history for the currently
     * selected request.</p>
     *
     */
    protected static void refreshSelectedRequest() {
        updateSelectedRequest();
        list_OpenRequests.refresh();
        list_ClosedRequests.refresh();
        list_ActionHistory.refresh();
    }

    /**********
     * <p> Method: getSelectedRequest() </p>
     *
     * <p> Description: Returns the request currently selected by the user.</p>
     *
     * @return the currently selected request, or {@code null} if none is selected
     */
    protected static AdminRequest getSelectedRequest() {
        return selectedRequest;
    }

    /**********
     * <p> Method: focusClosedRequest(int requestId) </p>
     *
     * <p> Description: Moves focus to the specified closed request in the closed
     * request list and refreshes the details panel.</p>
     *
     * @param requestId the ID of the closed request to focus
     */
    protected static void focusClosedRequest(int requestId) {
        for (AdminRequest request : list_ClosedRequests.getItems()) {
            if (request.getId() == requestId) {
                list_ClosedRequests.getSelectionModel().select(request);
                list_ClosedRequests.scrollTo(request);
                selectedRequest = request;
                updateSelectedRequest();
                return;
            }
        }
    }

    /*-*******************************************************************************************

    Helper methods

     */

    /**********
     * <p> Method: updateSelectedRequest() </p>
     *
     * <p> Description: Updates the request details panel and action-history list
     * using the currently selected request.</p>
     *
     */
    private static void updateSelectedRequest() {
        if (selectedRequest == null) {
            textarea_RequestDetails.setText("Select an open or closed request to inspect its details.");
            list_ActionHistory.setItems(FXCollections.<AdminRequestAction>observableArrayList());
            button_ViewOriginal.setDisable(true);
            return;
        }

        AdminRequest refreshedRequest =
                applicationMain.FoundationsMain.database.getAdminRequestById(selectedRequest.getId());

        if (refreshedRequest != null) {
            selectedRequest = refreshedRequest;
        }

        textarea_RequestDetails.setText(buildRequestDetails(selectedRequest));
        List<AdminRequestAction> actions = ModelAdminRequests.getActions(selectedRequest);
        list_ActionHistory.setItems(FXCollections.observableArrayList(actions));
        button_ViewOriginal.setDisable(selectedRequest.getReopenedFromId() == null);
    }

    /**********
     * <p> Method: buildButtonRow() </p>
     *
     * <p> Description: Builds the bottom button row for the request center.</p>
     *
     * @return the configured button row
     */
    private static HBox buildButtonRow() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return new HBox(10,
                button_CreateRequest,
                button_ReopenRequest,
                button_AddAction,
                button_CloseRequest,
                button_ViewOriginal,
                spacer,
                button_Refresh,
                button_Return);
    }

    /**********
     * <p> Method: styleHeader(Label label, double fontSize) </p>
     *
     * <p> Description: Applies consistent bold header styling to a label.</p>
     *
     * @param label the label to style
     * @param fontSize the font size to apply
     */
    private static void styleHeader(Label label, double fontSize) {
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: bold;");
    }

    /**********
     * <p> Method: buildRequestDetails(AdminRequest request) </p>
     *
     * <p> Description: Builds the details text shown in the selected-request
     * details area.</p>
     *
     * @param request the request whose data should be displayed
     *
     * @return a formatted details string
     */
    private static String buildRequestDetails(AdminRequest request) {
        StringBuilder builder = new StringBuilder();

        builder.append("Request ID: #").append(request.getId())
               .append("\nType: ").append(request.getRequestType())
               .append("\nRequested By: ").append(request.getRequestedBy())
               .append("\nStatus: ").append(request.getStatus())
               .append("\nCreated: ").append(request.getCreatedAt())
               .append("\nUpdated: ").append(request.getUpdatedAt());

        if (request.getClosedBy() != null) {
            builder.append("\nClosed By: ").append(request.getClosedBy());
        }

        if (request.getClosedAt() != null) {
            builder.append("\nClosed At: ").append(request.getClosedAt());
        }

        if (request.getReopenedFromId() != null) {
            builder.append("\nReopened From Request: #").append(request.getReopenedFromId());
        }

        builder.append("\n\nDescription:\n").append(request.getDescription());
        return builder.toString();
    }

    /**********
     * <p> Method: roleSuffix(User user) </p>
     *
     * <p> Description: Builds a short role suffix used in the page header.</p>
     *
     * @param user the current user
     *
     * @return a formatted role suffix string
     */
    private static String roleSuffix(User user) {
        if (user.getAdminRole() && user.getNewStaff()) return " (Admin + Staff)";
        if (user.getAdminRole()) return " (Admin)";
        if (user.getNewStaff()) return " (Staff)";
        return "";
    }
}