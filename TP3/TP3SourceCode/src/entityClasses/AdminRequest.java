package entityClasses;

import java.time.LocalDateTime;

/**
 * Represents an admin-facing request submitted by a staff user.
 *
 * <p>A request remains open until an admin closes it. If a staff user needs
 * additional work after closure, the system creates a new open request and links
 * it back to the original closed request through {@code reopenedFromId}.</p>
 *
 * <p>This class is used by the request center feature to display request
 * metadata, track lifecycle state, and preserve request history.</p>
 *
 * @author Daniel Prada
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

    /**
     * Creates a new immutable admin request object.
     *
     * @param id the unique request ID
     * @param requestType the category of admin action being requested
     * @param description the request description entered by staff
     * @param requestedBy the username of the staff member who created the request
     * @param status the current status of the request
     * @param createdAt the date and time when the request was created
     * @param updatedAt the date and time when the request was last updated
     * @param closedBy the username of the admin who closed the request, or {@code null}
     * @param closedAt the time the request was closed, or {@code null}
     * @param reopenedFromId the ID of the original closed request if this one was reopened,
     *        or {@code null} if this is an original request
     */
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

    /**
     * Returns the request ID.
     *
     * @return the unique request ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Returns the request description.
     *
     * @return the request description entered by staff
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the username of the staff member who submitted the request.
     *
     * @return the requesting username
     */
    public String getRequestedBy() {
        return requestedBy;
    }

    /**
     * Returns the current request status.
     *
     * @return the request status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the time the request was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the time the request was last updated.
     *
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the admin who closed the request.
     *
     * @return the closing admin username, or {@code null} if the request is still open
     */
    public String getClosedBy() {
        return closedBy;
    }

    /**
     * Returns the time the request was closed.
     *
     * @return the closing timestamp, or {@code null} if the request is still open
     */
    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    /**
     * Returns the original closed request ID if this request was reopened.
     *
     * @return the original closed request ID, or {@code null} if this request was not reopened
     */
    public Integer getReopenedFromId() {
        return reopenedFromId;
    }

    /**
     * Checks whether the request is currently open.
     *
     * @return {@code true} if the request is open, otherwise {@code false}
     */
    public boolean isOpen() {
        return STATUS_OPEN.equals(status);
    }

    /**
     * Checks whether the request is currently closed.
     *
     * @return {@code true} if the request is closed, otherwise {@code false}
     */
    public boolean isClosed() {
        return STATUS_CLOSED.equals(status);
    }

    /**
     * Checks whether this request was created by reopening a previous closed request.
     *
     * @return {@code true} if the request has an original linked request, otherwise {@code false}
     */
    public boolean isReopenedRequest() {
        return reopenedFromId != null;
    }

    /**
     * Returns a short summary string for display in request lists.
     *
     * @return a human-readable summary of the request
     */
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