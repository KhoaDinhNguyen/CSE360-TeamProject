package guiAdminRequests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import applicationMain.FoundationsMain;
import database.Database;
import entityClasses.AdminRequest;
import entityClasses.AdminRequestAction;
import entityClasses.User;

/**
 * Unit tests for the admin request workflow.
 *
 * <p>These tests verify request creation, admin action logging,
 * request closure, and reopening behavior.</p>
 *
 * @author Daniel Prada
 */
public class ModelAdminRequestsTest {
    /** In-memory test database used for each test case. */
    private Database database;

    /** Admin user used to test admin-only request actions. */
    private User adminUser;

    /** Staff user used to test request creation and reopening behavior. */
    private User staffUser;

    /** Non-staff user used to verify role-based access restrictions. */
    private User studentUser;

    /**
     * Creates a fresh in-memory database and test users before each test.
     *
     * <p>This setup ensures each test runs in isolation and does not depend
     * on data left behind by another test.</p>
     *
     * @throws SQLException if the test database cannot be initialized
     */
    @BeforeEach
    public void setUp() throws SQLException {
        database = new Database("jdbc:h2:mem:adminRequests" + System.nanoTime());
        database.connectToDatabase();
        FoundationsMain.database = database;

        adminUser = new User("admin", "pw", "A", "", "Admin", "A", "admin@test.com",
                true, false, false);
        staffUser = new User("staff", "pw", "S", "", "Staff", "S", "staff@test.com",
                false, false, true);
        studentUser = new User("student", "pw", "T", "", "Student", "T", "student@test.com",
                false, true, false);

        database.register(adminUser);
        database.register(staffUser);
        database.register(studentUser);
    }

    /**
     * Closes the in-memory database after each test completes.
     *
     * <p>This cleanup prevents resource leaks and keeps tests isolated.</p>
     */
    @AfterEach
    public void tearDown() {
        database.closeConnection();
    }

    /**
     * Groups tests related to initial request creation.
     */
    @Nested
    @DisplayName("Create request tests")
    public class CreateRequestTests {
    	/**
    	 * Default constructor
    	 */
    	public CreateRequestTests() {
    		
    	}
    	
        /**
         * Verifies that a staff user cannot create a request with a blank description.
         *
         * <p>The model should reject the input and return the expected validation message.</p>
         */
        @Test
        public void shouldRejectBlankDescription_whenStaffCreatesRequest() {
            String actual = ModelAdminRequests.submitStaffRequest(staffUser, "DELETE_USER", "   ");
            assertEquals("Request description could not be empty", actual);
        }

        /**
         * Verifies that a non-staff user cannot submit an admin request.
         *
         * <p>This confirms that request creation is restricted to staff users only.</p>
         */
        @Test
        public void shouldRejectNonStaffUser_whenCreatingRequest() {
            String actual = ModelAdminRequests.submitStaffRequest(studentUser, "DELETE_USER", "Need admin help");
            assertEquals("Only staff members can submit admin requests", actual);
        }

        /**
         * Verifies that a valid staff request is stored as an open request.
         *
         * <p>This test also confirms that request creation writes an initial
         * CREATED action entry to the request history.</p>
         */
        @Test
        public void shouldCreateOpenRequest_whenStaffInputIsValid() {
            String actual = ModelAdminRequests.submitStaffRequest(staffUser, "DELETE_USER",
                    "Please remove the duplicate account for userX");

            assertEquals("", actual);

            List<AdminRequest> openRequests = ModelAdminRequests.getOpenRequests();
            assertEquals(1, openRequests.size());
            assertEquals("DELETE_USER", openRequests.get(0).getRequestType());
            assertEquals("staff", openRequests.get(0).getRequestedBy());
            assertTrue(openRequests.get(0).isOpen());

            List<AdminRequestAction> actions = ModelAdminRequests.getActions(openRequests.get(0));
            assertEquals(1, actions.size());
            assertEquals("CREATED", actions.get(0).getActionType());
        }
    }

    /**
     * Groups tests related to admin-side workflow actions.
     */
    @Nested
    @DisplayName("Admin workflow tests")
    public class AdminWorkflowTests {
        /**
         * Verifies that an admin can add an action note to an existing open request.
         *
         * <p>This confirms that request history is updated when an admin
         * documents work performed on a request.</p>
         */
        @Test
        public void shouldAddActionNote_whenAdminDocumentsOpenRequest() {
            ModelAdminRequests.submitStaffRequest(staffUser, "ADD_REMOVE_ROLES",
                    "Please add the staff role to tempUser");
            AdminRequest request = ModelAdminRequests.getOpenRequests().get(0);

            String actual = ModelAdminRequests.addAdminAction(adminUser, request,
                    "Verified account ownership and role request details.");

            assertEquals("", actual);

            List<AdminRequestAction> actions = ModelAdminRequests.getActions(request);
            assertEquals(2, actions.size());
            assertEquals("NOTE", actions.get(1).getActionType());
            assertEquals("admin", actions.get(1).getActorUsername());
        }

        /**
         * Verifies that an admin can close an open request.
         *
         * <p>This test confirms that the request is removed from the open list,
         * added to the closed list, and given a final CLOSED action entry.</p>
         */
        @Test
        public void shouldMoveRequestToClosedList_whenAdminClosesRequest() {
            ModelAdminRequests.submitStaffRequest(staffUser, "SET_ONE_TIME_PASSWORD",
                    "Reset password for account temp123");
            AdminRequest request = ModelAdminRequests.getOpenRequests().get(0);

            String actual = ModelAdminRequests.closeRequest(adminUser, request,
                    "Password was reset and shared through the approved process.");

            assertEquals("", actual);
            assertEquals(0, ModelAdminRequests.getOpenRequests().size());
            assertEquals(1, ModelAdminRequests.getClosedRequests().size());
            assertTrue(ModelAdminRequests.getClosedRequests().get(0).isClosed());

            List<AdminRequestAction> actions = ModelAdminRequests.getActions(
                    ModelAdminRequests.getClosedRequests().get(0));
            assertEquals("CLOSED", actions.get(actions.size() - 1).getActionType());
        }
    }

    /**
     * Groups tests related to reopening previously closed requests.
     */
    @Nested
    @DisplayName("Reopen request tests")
    public class ReopenRequestTests {
        /**
         * Verifies that reopening a closed request creates a new linked open request.
         *
         * <p>This test confirms that the original request remains closed, the new
         * request is open, and the reopened request preserves a link back to the
         * original closed request.</p>
         */
        @Test
        public void shouldCreateLinkedOpenRequest_whenStaffReopensClosedRequest() {
            ModelAdminRequests.submitStaffRequest(staffUser, "OTHER",
                    "Original request description");
            AdminRequest originalOpenRequest = ModelAdminRequests.getOpenRequests().get(0);
            ModelAdminRequests.closeRequest(adminUser, originalOpenRequest,
                    "Initial work completed.");

            AdminRequest closedRequest = ModelAdminRequests.getClosedRequests().get(0);
            String actual = ModelAdminRequests.reopenRequest(staffUser, closedRequest,
                    "The same issue returned. Please investigate the reopened case.");

            assertEquals("", actual);
            assertEquals(1, ModelAdminRequests.getClosedRequests().size());
            assertEquals(1, ModelAdminRequests.getOpenRequests().size());

            AdminRequest reopenedRequest = ModelAdminRequests.getOpenRequests().get(0);
            assertEquals(closedRequest.getId(), reopenedRequest.getReopenedFromId());
            assertEquals("OTHER", reopenedRequest.getRequestType());
            assertTrue(reopenedRequest.isOpen());

            AdminRequest linkedOriginal = ModelAdminRequests.getOriginalClosedRequest(reopenedRequest);
            assertNotNull(linkedOriginal);
            assertEquals(closedRequest.getId(), linkedOriginal.getId());
        }
    }
}