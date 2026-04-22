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
    private Database database;
    private User adminUser;
    private User staffUser;
    private User studentUser;

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

    @AfterEach
    public void tearDown() {
        database.closeConnection();
    }

    @Nested
    @DisplayName("Create request tests")
    class CreateRequestTests {
        @Test
        public void shouldRejectBlankDescription_whenStaffCreatesRequest() {
            String actual = ModelAdminRequests.submitStaffRequest(staffUser, "DELETE_USER", "   ");
            assertEquals("Request description could not be empty", actual);
        }

        @Test
        public void shouldRejectNonStaffUser_whenCreatingRequest() {
            String actual = ModelAdminRequests.submitStaffRequest(studentUser, "DELETE_USER", "Need admin help");
            assertEquals("Only staff members can submit admin requests", actual);
        }

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

    @Nested
    @DisplayName("Admin workflow tests")
    class AdminWorkflowTests {
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

    @Nested
    @DisplayName("Reopen request tests")
    class ReopenRequestTests {
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