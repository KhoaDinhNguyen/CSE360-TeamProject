package entityClasses;

import java.time.LocalDateTime;

/**
 * Represents a dated action or note attached to an admin request.
 */
public class AdminRequestAction {
    private final int id;
    private final int requestId;
    private final String actorUsername;
    private final String actionType;
    private final String note;
    private final LocalDateTime createdAt;

    public AdminRequestAction(int id, int requestId, String actorUsername, String actionType,
            String note, LocalDateTime createdAt) {
        this.id = id;
        this.requestId = requestId;
        this.actorUsername = actorUsername;
        this.actionType = actionType;
        this.note = note;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getActorUsername() {
        return actorUsername;
    }

    public String getActionType() {
        return actionType;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return createdAt + " | " + actionType + " | " + actorUsername + " | " + note;
    }
}