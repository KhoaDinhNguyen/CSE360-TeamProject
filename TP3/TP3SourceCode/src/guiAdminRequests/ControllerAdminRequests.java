package guiAdminRequests;

import entityClasses.AdminRequest;
import guiAdminHome.ViewAdminHome;
import guiStaffHome.ViewStaffHome;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller actions for the request center page.
 */
public class ControllerAdminRequests {
    public ControllerAdminRequests() {
    }

    protected static void performCreateRequest() {
        String result = ModelAdminRequests.submitStaffRequest(
                ViewAdminRequests.theUser,
                ViewAdminRequests.combobox_RequestType.getValue(),
                ViewAdminRequests.textarea_NewRequestDescription.getText());

        if (!result.isEmpty()) {
            showAlert(AlertType.ERROR, "Create Request", "Could not create request", result);
            return;
        }

        ViewAdminRequests.textarea_NewRequestDescription.clear();
        ViewAdminRequests.refreshView();
        showAlert(AlertType.INFORMATION, "Create Request", "Request created",
                "Your request has been added to the open request list.");
    }

    protected static void performAddAction() {
        String result = ModelAdminRequests.addAdminAction(
                ViewAdminRequests.theUser,
                ViewAdminRequests.getSelectedRequest(),
                ViewAdminRequests.textarea_ActionNote.getText());

        if (!result.isEmpty()) {
            showAlert(AlertType.ERROR, "Add Action Note", "Could not add action", result);
            return;
        }

        ViewAdminRequests.textarea_ActionNote.clear();
        ViewAdminRequests.refreshSelectedRequest();
        showAlert(AlertType.INFORMATION, "Add Action Note", "Action saved",
                "The action note has been added to the request history.");
    }

    protected static void performCloseRequest() {
        String result = ModelAdminRequests.closeRequest(
                ViewAdminRequests.theUser,
                ViewAdminRequests.getSelectedRequest(),
                ViewAdminRequests.textarea_ActionNote.getText());

        if (!result.isEmpty()) {
            showAlert(AlertType.ERROR, "Close Request", "Could not close request", result);
            return;
        }

        ViewAdminRequests.textarea_ActionNote.clear();
        ViewAdminRequests.refreshView();
        showAlert(AlertType.INFORMATION, "Close Request", "Request closed",
                "The request was moved to the closed request list.");
    }

    protected static void performReopenRequest() {
        String result = ModelAdminRequests.reopenRequest(
                ViewAdminRequests.theUser,
                ViewAdminRequests.getSelectedRequest(),
                ViewAdminRequests.textarea_NewRequestDescription.getText());

        if (!result.isEmpty()) {
            showAlert(AlertType.ERROR, "Reopen Request", "Could not reopen request", result);
            return;
        }

        ViewAdminRequests.textarea_NewRequestDescription.clear();
        ViewAdminRequests.refreshView();
        showAlert(AlertType.INFORMATION, "Reopen Request", "Request reopened",
                "A new open request was created and linked back to the original closed request.");
    }

    protected static void performViewOriginalRequest() {
        AdminRequest selectedRequest = ViewAdminRequests.getSelectedRequest();
        AdminRequest originalRequest = ModelAdminRequests.getOriginalClosedRequest(selectedRequest);

        if (originalRequest == null) {
            showAlert(AlertType.INFORMATION, "View Original Request", "No linked request",
                    "The selected request was not reopened from an earlier closed request.");
            return;
        }

        ViewAdminRequests.focusClosedRequest(originalRequest.getId());
    }

    protected static void performRefresh() {
        ViewAdminRequests.refreshView();
    }

    protected static void performReturn() {
        if (applicationMain.FoundationsMain.activeHomePage == 1) {
            ViewAdminHome.displayAdminHome(ViewAdminRequests.theStage, ViewAdminRequests.theUser);
        }
        else {
            ViewStaffHome.displayStaffHome(ViewAdminRequests.theStage, ViewAdminRequests.theUser);
        }
    }

    private static void showAlert(AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}