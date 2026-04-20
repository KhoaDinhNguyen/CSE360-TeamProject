package entityClasses;

import java.time.LocalDateTime;

/**
 * Represents an admin-facing work request created by a staff user.
 *
 * <p>Requests remain open until an admin closes them. If a closed request needs more work,
 * the system creates a new open request whose {@code reopenedFromId} points back to the
 * original closed request.</p>
 */
public class AdminRequest {
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_CLOSED = "CLOSED";

    private final int id;
    private final String requestType;
    private final String description;
    private final String requestedBy;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String closedBy;
    private final LocalDateTime closedAt;
    private final Integer reopenedFromId;

    public AdminRequest(
            int id,
            String requestType,
            String description,
            String requestedBy,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String closedBy,
            LocalDateTime closedAt,
            Integer reopenedFromId) {
        this.id = id;
        this.requestType = requestType;
        this.description = description;
        this.requestedBy = requestedBy;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedBy = closedBy;
        this.closedAt = closedAt;
        this.reopenedFromId = reopenedFromId;
    }

    public int getId() {
        return id;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public Integer getReopenedFromId() {
        return reopenedFromId;
    }

    public boolean isOpen() {
        return STATUS_OPEN.equals(status);
    }

    public boolean isClosed() {
        return STATUS_CLOSED.equals(status);
    }

    public boolean isReopenedRequest() {
        return reopenedFromId != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("#").append(id)
               .append(" [").append(status).append("] ")
               .append(requestType)
               .append(" - ").append(requestedBy);

        if (reopenedFromId != null) {
            builder.append(" (reopened from #").append(reopenedFromId).append(")");
        }

        return builder.toString();
    }
}