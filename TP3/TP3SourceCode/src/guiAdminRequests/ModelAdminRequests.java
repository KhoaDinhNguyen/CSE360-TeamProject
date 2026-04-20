package guiAdminRequests;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import entityClasses.AdminRequest;
import entityClasses.AdminRequestAction;
import entityClasses.User;

/**
 * Business logic for the staff/admin request center.
 */
public class ModelAdminRequests {
    private static final List<String> REQUEST_TYPES = List.of(
            "SEND_INVITATION",
            "MANAGE_INVITATION",
            "SET_ONE_TIME_PASSWORD",
            "DELETE_USER",
            "LIST_USERS",
            "ADD_REMOVE_ROLES",
            "OTHER"
    );

    public ModelAdminRequests() {
    }

    private static Database getDatabase() {
        return applicationMain.FoundationsMain.database;
    }

    public static List<String> getRequestTypes() {
        return new ArrayList<String>(REQUEST_TYPES);
    }

    public static List<AdminRequest> getOpenRequests() {
        return getDatabase().getOpenAdminRequests();
    }

    public static List<AdminRequest> getClosedRequests() {
        return getDatabase().getClosedAdminRequests();
    }

    public static List<AdminRequestAction> getActions(AdminRequest request) {
        if (request == null) return new ArrayList<AdminRequestAction>();
        return getDatabase().getAdminRequestActions(request.getId());
    }

    public static AdminRequest getOriginalClosedRequest(AdminRequest request) {
        if (request == null || request.getReopenedFromId() == null) return null;
        return getDatabase().getAdminRequestById(request.getReopenedFromId());
    }

    public static String submitStaffRequest(User user, String requestType, String description) {
        if (!isStaff(user)) return "Only staff members can submit admin requests";

        String validationMessage = validateRequest(requestType, description);
        if (!validationMessage.isEmpty()) return validationMessage;

        int requestId = getDatabase().createAdminRequest(requestType, description.trim(), user.getUserName(), null);
        if (requestId < 0) return "The request could not be saved";

        getDatabase().addAdminRequestAction(requestId, user.getUserName(), "CREATED",
                "Staff request created");
        return "";
    }

    public static String addAdminAction(User user, AdminRequest request, String note) {
        if (!isAdmin(user)) return "Only admins can document request actions";
        if (request == null) return "Select a request first";
        if (!request.isOpen()) return "Only open requests can receive new admin action notes";
        if (note == null || note.isBlank()) return "Action note could not be empty";

        getDatabase().addAdminRequestAction(request.getId(), user.getUserName(), "NOTE", note.trim());
        return "";
    }

    public static String closeRequest(User user, AdminRequest request, String closingNote) {
        if (!isAdmin(user)) return "Only admins can close requests";
        if (request == null) return "Select a request first";
        if (!request.isOpen()) return "Only open requests can be closed";
        if (closingNote == null || closingNote.isBlank()) return "Closing note could not be empty";

        getDatabase().closeAdminRequest(request.getId(), user.getUserName(), closingNote.trim());
        return "";
    }

    public static String reopenRequest(User user, AdminRequest request, String updatedDescription) {
        if (!isStaff(user)) return "Only staff members can reopen requests";
        if (request == null) return "Select a request first";
        if (!request.isClosed()) return "Only closed requests can be reopened";
        if (updatedDescription == null || updatedDescription.isBlank()) {
            return "Updated description could not be empty";
        }

        int newRequestId = getDatabase().reopenAdminRequest(request.getId(), user.getUserName(),
                updatedDescription.trim());
        if (newRequestId < 0) return "The request could not be reopened";
        return "";
    }

    private static String validateRequest(String requestType, String description) {
        StringBuilder errorMessage = new StringBuilder();

        if (requestType == null || requestType.isBlank() || !REQUEST_TYPES.contains(requestType)) {
            errorMessage.append("Select a valid request type");
        }

        if (description == null || description.isBlank()) {
            if (errorMessage.length() > 0) errorMessage.append("\n");
            errorMessage.append("Request description could not be empty");
        }

        return errorMessage.toString();
    }

    private static boolean isAdmin(User user) {
        return user != null && user.getAdminRole();
    }

    private static boolean isStaff(User user) {
        return user != null && user.getNewStaff();
    }
}