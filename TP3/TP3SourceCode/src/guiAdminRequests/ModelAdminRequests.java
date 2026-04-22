package guiAdminRequests;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import entityClasses.AdminRequest;
import entityClasses.AdminRequestAction;
import entityClasses.User;

/**
 * Provides the business logic for the admin request center.
 *
 * <p>This model enforces role-based behavior for the request workflow:
 * staff users may create and reopen requests, while admin users may
 * document work and close requests.</p>
 *
 * @author Daniel Prada
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

    /**
     * Creates a model object for the admin request workflow.
     */
    public ModelAdminRequests() {
    }

    /**
     * Returns the shared database instance used by the application.
     *
     * @return the current database instance
     */
    private static Database getDatabase() {
        return applicationMain.FoundationsMain.database;
    }

    /**
     * Returns the list of valid request types that a staff user may select.
     *
     * @return a copy of the supported request types
     */
    public static List<String> getRequestTypes() {
        return new ArrayList<String>(REQUEST_TYPES);
    }

    /**
     * Returns all open admin requests.
     *
     * @return a list of open requests
     */
    public static List<AdminRequest> getOpenRequests() {
        return getDatabase().getOpenAdminRequests();
    }

    /**
     * Returns all closed admin requests.
     *
     * @return a list of closed requests
     */
    public static List<AdminRequest> getClosedRequests() {
        return getDatabase().getClosedAdminRequests();
    }

    /**
     * Returns the recorded action history for a request.
     *
     * @param request the selected request
     * @return the list of actions for that request, or an empty list if the request is null
     */
    public static List<AdminRequestAction> getActions(AdminRequest request) {
        if (request == null) return new ArrayList<AdminRequestAction>();
        return getDatabase().getAdminRequestActions(request.getId());
    }

    /**
     * Returns the original closed request linked to a reopened request.
     *
     * @param request the reopened request
     * @return the original closed request, or {@code null} if none exists
     */
    public static AdminRequest getOriginalClosedRequest(AdminRequest request) {
        if (request == null || request.getReopenedFromId() == null) return null;
        return getDatabase().getAdminRequestById(request.getReopenedFromId());
    }

    /**
     * Validates and submits a new admin request on behalf of a staff user.
     *
     * @param user the currently logged-in user
     * @param requestType the selected request type
     * @param description the request description
     * @return an empty string if successful, otherwise an error message
     */
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

    /**
     * Adds an admin action note to an existing open request.
     *
     * @param user the current user
     * @param request the selected request
     * @param note the note entered by the admin
     * @return an empty string if successful, otherwise an error message
     */
    public static String addAdminAction(User user, AdminRequest request, String note) {
        if (!isAdmin(user)) return "Only admins can document request actions";
        if (request == null) return "Select a request first";
        if (!request.isOpen()) return "Only open requests can receive new admin action notes";
        if (note == null || note.isBlank()) return "Action note could not be empty";

        getDatabase().addAdminRequestAction(request.getId(), user.getUserName(), "NOTE", note.trim());
        return "";
    }

    /**
     * Closes an open request and records a closing note.
     *
     * @param user the current user
     * @param request the selected request
     * @param closingNote the closing note entered by the admin
     * @return an empty string if successful, otherwise an error message
     */
    public static String closeRequest(User user, AdminRequest request, String closingNote) {
        if (!isAdmin(user)) return "Only admins can close requests";
        if (request == null) return "Select a request first";
        if (!request.isOpen()) return "Only open requests can be closed";
        if (closingNote == null || closingNote.isBlank()) return "Closing note could not be empty";

        getDatabase().closeAdminRequest(request.getId(), user.getUserName(), closingNote.trim());
        return "";
    }

    /**
     * Reopens a closed request by creating a new linked open request with an updated description.
     *
     * @param user the current user
     * @param request the closed request being reopened
     * @param updatedDescription the new request description
     * @return an empty string if successful, otherwise an error message
     */
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

    /**
     * Validates request input before a request is submitted.
     *
     * @param requestType the selected request type
     * @param description the request description
     * @return an empty string if valid, otherwise a validation error message
     */
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

    /**
     * Checks whether a user has the admin role.
     *
     * @param user the user to evaluate
     * @return {@code true} if the user is an admin, otherwise {@code false}
     */
    private static boolean isAdmin(User user) {
        return user != null && user.getAdminRole();
    }

    /**
     * Checks whether a user has the staff role.
     *
     * @param user the user to evaluate
     * @return {@code true} if the user is staff, otherwise {@code false}
     */
    private static boolean isStaff(User user) {
        return user != null && user.getNewStaff();
    }
}