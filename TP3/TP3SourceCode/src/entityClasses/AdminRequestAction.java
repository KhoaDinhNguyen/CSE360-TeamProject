package entityClasses;

import java.time.LocalDateTime;

/**
 * Represents an action, note, or lifecycle event attached to an admin request.
 *
 * <p>Examples include request creation, admin notes, closure, and reopening.
 * These records are used to preserve the history of work performed on a request.</p>
 *
 * @author Daniel Prada
 */
public class AdminRequestAction {
    private final int id;
    private final int requestId;
    private final String actorUsername;
    private final String actionType;
    private final String note;
    private final LocalDateTime createdAt;

    /**
     * Creates a new request action record.
     *
     * @param id the unique action ID
     * @param requestId the ID of the request this action belongs to
     * @param actorUsername the username of the user who performed the action
     * @param actionType the kind of action performed
     * @param note the text note associated with the action
     * @param createdAt the date and time the action was recorded
     */
    public AdminRequestAction(int id, int requestId, String actorUsername, String actionType,
            String note, LocalDateTime createdAt) {
        this.id = id;
        this.requestId = requestId;
        this.actorUsername = actorUsername;
        this.actionType = actionType;
        this.note = note;
        this.createdAt = createdAt;
    }

    /**
     * Returns the action ID.
     *
     * @return the unique action ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the request ID this action belongs to.
     *
     * @return the request ID
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Returns the username of the actor who performed the action.
     *
     * @return the actor username
     */
    public String getActorUsername() {
        return actorUsername;
    }

    /**
     * Returns the action type.
     *
     * @return the action type
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Returns the note attached to the action.
     *
     * @return the action note
     */
    public String getNote() {
        return note;
    }

    /**
     * Returns the time the action was created.
     *
     * @return the action timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns a short summary string for display in the action history list.
     *
     * @return a human-readable action summary
     */
    @Override
    public String toString() {
        return createdAt + " | " + actionType + " | " + actorUsername + " | " + note;
    }
}