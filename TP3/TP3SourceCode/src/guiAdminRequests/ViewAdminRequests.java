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

/**
 * Shared request center used by staff and admins.
 */
public class ViewAdminRequests {
    private static final double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static final double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static ViewAdminRequests theView;
    protected static Stage theStage;
    protected static User theUser;

    private static Scene theScene;
    private static BorderPane rootPane;

    protected static Label label_PageTitle = new Label("Admin Request Center");
    protected static Label label_UserDetails = new Label();
    protected static Label label_OpenRequests = new Label("Open Requests");
    protected static Label label_ClosedRequests = new Label("Closed Requests");
    protected static Label label_RequestDetails = new Label("Selected Request Details");
    protected static Label label_ActionHistory = new Label("Action History");
    protected static Label label_RequestType = new Label("Request Type");
    protected static Label label_NewRequestDescription = new Label("New / Updated Description");
    protected static Label label_ActionNote = new Label("Admin Action / Closing Note");

    protected static ListView<AdminRequest> list_OpenRequests = new ListView<AdminRequest>();
    protected static ListView<AdminRequest> list_ClosedRequests = new ListView<AdminRequest>();
    protected static ListView<AdminRequestAction> list_ActionHistory = new ListView<AdminRequestAction>();

    protected static TextArea textarea_RequestDetails = new TextArea();
    protected static TextArea textarea_NewRequestDescription = new TextArea();
    protected static TextArea textarea_ActionNote = new TextArea();

    protected static ComboBox<String> combobox_RequestType = new ComboBox<String>();

    protected static Button button_CreateRequest = new Button("Create Request");
    protected static Button button_ReopenRequest = new Button("Reopen Selected Request");
    protected static Button button_AddAction = new Button("Add Action Note");
    protected static Button button_CloseRequest = new Button("Close Selected Request");
    protected static Button button_ViewOriginal = new Button("View Original Closed Request");
    protected static Button button_Refresh = new Button("Refresh");
    protected static Button button_Return = new Button("Return");

    private static AdminRequest selectedRequest;

    public static void displayAdminRequests(Stage ps, User user) {
        theStage = ps;
        theUser = user;

        if (theView == null) theView = new ViewAdminRequests();

        refreshView();
        theStage.setTitle("CSE 360 Foundations: Admin Request Center");
        theStage.setScene(theScene);
        theStage.show();
    }

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

        button_CreateRequest.setOnAction(event -> ControllerAdminRequests.performCreateRequest());
        button_ReopenRequest.setOnAction(event -> ControllerAdminRequests.performReopenRequest());
        button_AddAction.setOnAction(event -> ControllerAdminRequests.performAddAction());
        button_CloseRequest.setOnAction(event -> ControllerAdminRequests.performCloseRequest());
        button_ViewOriginal.setOnAction(event -> ControllerAdminRequests.performViewOriginalRequest());
        button_Refresh.setOnAction(event -> ControllerAdminRequests.performRefresh());
        button_Return.setOnAction(event -> ControllerAdminRequests.performReturn());

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

    protected static void refreshView() {
        label_UserDetails.setText("User: " + theUser.getUserName() + roleSuffix(theUser));
        combobox_RequestType.setItems(FXCollections.observableArrayList(ModelAdminRequests.getRequestTypes()));
        if (combobox_RequestType.getSelectionModel().isEmpty()) {
            combobox_RequestType.getSelectionModel().selectLast();
        }

        list_OpenRequests.setItems(FXCollections.observableArrayList(ModelAdminRequests.getOpenRequests()));
        list_ClosedRequests.setItems(FXCollections.observableArrayList(ModelAdminRequests.getClosedRequests()));

        button_CreateRequest.setVisible(theUser.getNewStaff());
        button_CreateRequest.setManaged(theUser.getNewStaff());
        button_ReopenRequest.setVisible(theUser.getNewStaff());
        button_ReopenRequest.setManaged(theUser.getNewStaff());
        button_AddAction.setVisible(theUser.getAdminRole());
        button_AddAction.setManaged(theUser.getAdminRole());
        button_CloseRequest.setVisible(theUser.getAdminRole());
        button_CloseRequest.setManaged(theUser.getAdminRole());

        updateSelectedRequest();
    }

    protected static void refreshSelectedRequest() {
        updateSelectedRequest();
        list_OpenRequests.refresh();
        list_ClosedRequests.refresh();
        list_ActionHistory.refresh();
    }

    protected static AdminRequest getSelectedRequest() {
        return selectedRequest;
    }

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

    private static void updateSelectedRequest() {
        if (selectedRequest == null) {
            textarea_RequestDetails.setText("Select an open or closed request to inspect its details.");
            list_ActionHistory.setItems(FXCollections.<AdminRequestAction>observableArrayList());
            button_ViewOriginal.setDisable(true);
            return;
        }

        AdminRequest refreshedRequest = applicationMain.FoundationsMain.database
                .getAdminRequestById(selectedRequest.getId());
        if (refreshedRequest != null) {
            selectedRequest = refreshedRequest;
        }

        textarea_RequestDetails.setText(buildRequestDetails(selectedRequest));
        List<AdminRequestAction> actions = ModelAdminRequests.getActions(selectedRequest);
        list_ActionHistory.setItems(FXCollections.observableArrayList(actions));
        button_ViewOriginal.setDisable(selectedRequest.getReopenedFromId() == null);
    }

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

    private static void styleHeader(Label label, double fontSize) {
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: bold;");
    }

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

    private static String roleSuffix(User user) {
        if (user.getAdminRole() && user.getNewStaff()) return " (Admin + Staff)";
        if (user.getAdminRole()) return " (Admin)";
        if (user.getNewStaff()) return " (Staff)";
        return "";
    }
}