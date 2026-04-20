package guiForum;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Provides automated tests for the forum model.
 *
 * <p>This class runs a sequence of test cases that validate the behavior of
 * post and reply creation, editing, deletion, filtering, and retrieval in
 * {@link ModelGradingSystem}.</p>
 */
public class ModelForumTestingAutomation {

    static int numPassed = 0;
    static int numFailed = 0;

    /**
     * Runs the full automated test suite for the forum model.
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        System.out.println("______________________________________");
        System.out.println("\nModel Forum — Automated Test Run\n");

        for (int t = 1; t <= 30; t++) {
            performTestCase(t);
        }

        System.out.println("____________________________________________________________________________");
        System.out.println();
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    /**
     * Resets the forum model's in-memory post and reply stores using reflection.
     *
     * <p>This method is used so that each test case can run from a clean state.</p>
     *
     * @throws Exception if reflection fails or the stores cannot be reset
     */
    private static void resetModelForumStores() throws Exception {
        Class<?> mf = Class.forName("guiForum.ModelForum");
        Field postStoreField = mf.getDeclaredField("postStore");
        Field replyStoreField = mf.getDeclaredField("replyStore");
        postStoreField.setAccessible(true);
        replyStoreField.setAccessible(true);
        postStoreField.set(null, new entityClasses.PostStore());
        replyStoreField.set(null, new entityClasses.ReplyStore());
    }

    /**
     * Reports a successful test case and increments the pass counter.
     *
     * @param testNo the number of the test case that passed
     */
    private static void reportSuccess(int testNo) {
        System.out.println("Test " + testNo + " — PASS\n");
        numPassed++;
    }

    /**
     * Reports a failed test case, including the expected and actual results,
     * and increments the failure counter.
     *
     * @param testNo the number of the test case that failed
     * @param expected the expected result
     * @param actual the actual result produced by the test
     */
    private static void reportFailure(int testNo, String expected, String actual) {
        System.out.println("Test " + testNo + " — FAIL");
        System.out.println("  Expected: " + expected);
        System.out.println("  Actual:   " + actual + "\n");
        numFailed++;
    }

    /**
     * Executes a single automated test case for the forum model.
     *
     * @param testNo the number of the test case to execute
     */
    private static void performTestCase(int testNo) {
        System.out.println("____________________________________________________________________________\n");
        System.out.println("Test " + testNo);

        try {
            resetModelForumStores();
        } catch (Exception e) {
            System.out.println("Could not reset ModelGradingSystem stores: " + e);
            reportFailure(testNo, "reset succeeds", "reset failed: " + e);
            return;
        }

        String expected = "";
        String actual = "";
        boolean passed = false;

        try {
            switch (testNo) {
                case 1:
                    expected = "Title could not be empty";
                    actual = guiForum.ModelForum.addPost("", "C", "a");
                    passed = expected.equals(actual);
                    break;
                case 2:
                    expected = "Content could not be empty";
                    actual = guiForum.ModelForum.addPost("T", "", "a");
                    passed = expected.equals(actual);
                    break;
                case 3:
                    expected = "Title Content could not be empty";
                    actual = guiForum.ModelForum.addPost("", "", "a");
                    passed = expected.equals(actual);
                    break;
                case 4:
                    expected = "Title could not be empty";
                    try {
                        actual = guiForum.ModelForum.addPost(null, "C", "a");
                    } catch (Throwable ex) {
                        actual = "Exception: " + ex.getClass().getSimpleName() + (ex.getMessage() == null ? "" : ": " + ex.getMessage());
                    }
                    passed = expected.equals(actual);
                    break;
                case 5:
                    expected = "Author can’t be null";
                    actual = guiForum.ModelForum.addPost("T", "C", null);
                    passed = expected.equals(actual);
                    break;
                case 6:
                    expected = "Title could not be empty";
                    actual = guiForum.ModelForum.addPost(" ", "C", "DucP");
                    passed = expected.equals(actual);
                    break;
                case 7:
                    expected = "";
                    actual = guiForum.ModelForum.addPost("Welcome", "First post", "Duc");
                    passed = expected.equals(actual);
                    break;
                case 8:
                    expected = "Post doesn't exist";
                    actual = guiForum.ModelForum.editPost(9999, "Duc", "Hello", "My name is Duc");
                    passed = expected.equals(actual);
                    break;
                case 9:
                    // create id 0 with author "someone" so edit on id=0 by "Duc" is unauthorized
                    guiForum.ModelForum.addPost("A", "B", "someone"); // id 0
                    expected = "Can't edit other's user post";
                    actual = guiForum.ModelForum.editPost(0, "Duc", "Hello", "My name is Duc");
                    passed = expected.equals(actual);
                    break;
                case 10:
                    // create id0 then id1 owned by Duc
                    guiForum.ModelForum.addPost("P0", "C0", "someone"); // id 0
                    guiForum.ModelForum.addPost("Init", "X", "Duc");   // id 1
                    expected = "";
                    actual = guiForum.ModelForum.editPost(1, "Duc", "Hello", "My name is Duc");
                    passed = expected.equals(actual);
                    break;
                case 11:
                    guiForum.ModelForum.addPost("P0", "C0", "someone");
                    guiForum.ModelForum.addPost("Init", "X", "Duc"); // id1
                    expected = "Title could not be empty";
                    actual = guiForum.ModelForum.editPost(1, "Duc", "", "My name is Duc");
                    passed = expected.equals(actual);
                    break;
                case 12:
                    guiForum.ModelForum.addPost("P0", "C0", "someone");
                    guiForum.ModelForum.addPost("Init", "X", "Duc"); // id1
                    expected = "Title could not be empty";
                    try {
                        actual = guiForum.ModelForum.editPost(1, "Duc", null, "My name is Duc");
                    } catch (Throwable ex) {
                        actual = "Exception: " + ex.getClass().getSimpleName() + (ex.getMessage() == null ? "" : ": " + ex.getMessage());
                    }
                    passed = expected.equals(actual);
                    break;
                case 13:
                    guiForum.ModelForum.addPost("P0", "C0", "someone");
                    guiForum.ModelForum.addPost("Init", "X", "Duc"); // id1
                    expected = "Content could not be empty";
                    actual = guiForum.ModelForum.editPost(1, "Duc", "Hello", "");
                    passed = expected.equals(actual);
                    break;
                case 14:
                    guiForum.ModelForum.addPost("P0", "C0", "someone");
                    guiForum.ModelForum.addPost("Init", "X", "Duc"); // id1
                    expected = "Title Content could not be empty";
                    actual = guiForum.ModelForum.editPost(1, "Duc", "", "");
                    passed = expected.equals(actual);
                    break;
                case 15:
                    expected = "Post doesn't exist";
                    actual = guiForum.ModelForum.deletePost(9999, "a");
                    passed = expected.equals(actual);
                    break;
                case 16:
                    guiForum.ModelForum.addPost("P", "C", "Duc"); // id0
                    expected = "Can't delete other's user post";
                    actual = guiForum.ModelForum.deletePost(0, "DucP");
                    passed = expected.equals(actual);
                    break;
                case 17:
                    guiForum.ModelForum.addPost("P0", "C0", "someone"); // id0
                    guiForum.ModelForum.addPost("P1", "C1", "Duc");     // id1
                    expected = "";
                    actual = guiForum.ModelForum.deletePost(1, "Duc");
                    passed = expected.equals(actual);
                    break;
                case 18:
                    guiForum.ModelForum.addPost("Topic", "T", "owner"); // id0
                    expected = "Content could not be empty";
                    actual = guiForum.ModelForum.addReply("", "a", 0);
                    passed = expected.equals(actual);
                    break;
                case 19:
                    guiForum.ModelForum.addPost("Topic", "T", "owner"); // id0
                    expected = "Content could not be empty";
                    actual = guiForum.ModelForum.addReply(null, "a", 0);
                    passed = expected.equals(actual);
                    break;
                case 20:
                    expected = "Parent post not found";
                    actual = guiForum.ModelForum.addReply("R", "u", 99);
                    passed = expected.equals(actual);
                    break;
                case 21:
                    guiForum.ModelForum.addPost("Topic", "T", "owner"); // id0
                    expected = "";
                    actual = guiForum.ModelForum.addReply("Reply text", "bob", 0);
                    passed = expected.equals(actual);
                    break;
                case 22:
                    expected = "Post doesn't exist";
                    actual = guiForum.ModelForum.deleteReply(99999, "a");
                    passed = expected.equals(actual);
                    break;
                case 23:
                    guiForum.ModelForum.addPost("P", "C", "owner"); // id0
                    guiForum.ModelForum.addReply("r", "owner", 0); // reply id 0
                    expected = "Can't delete other's user post";
                    actual = guiForum.ModelForum.deleteReply(0, "alice");
                    passed = expected.equals(actual);
                    break;
                case 24:
                    guiForum.ModelForum.addPost("P", "C", "owner"); // id0
                    guiForum.ModelForum.addReply("r", "bob", 0);    // reply id 0
                    expected = "";
                    actual = guiForum.ModelForum.deleteReply(0, "bob");
                    passed = expected.equals(actual);
                    break;
                case 25:
                    expected = "Post doesn't exist";
                    actual = guiForum.ModelForum.editReply(9999, "a", "X");
                    passed = expected.equals(actual);
                    break;
                case 26:
                    guiForum.ModelForum.addPost("P", "C", "owner"); // id0
                    guiForum.ModelForum.addReply("r", "bob", 0);    // reply id 0
                    expected = "Can't edit other's user post";
                    actual = guiForum.ModelForum.editReply(0, "alice", "Updated");
                    passed = expected.equals(actual);
                    break;
                case 27:
                    guiForum.ModelForum.addPost("P", "C", "owner"); // id0
                    guiForum.ModelForum.addReply("r", "bob", 0);    // reply id 0
                    expected = "";
                    actual = guiForum.ModelForum.editReply(0, "bob", "Updated");
                    passed = expected.equals(actual);
                    break;
                case 28:
                    expected = "[]";
                    List<?> l28 = guiForum.ModelForum.getRepliesByPostId(9999);
                    actual = l28 == null ? "null" : l28.toString();
                    passed = (l28 != null && l28.size() == 0);
                    break;
                case 29:
                    guiForum.ModelForum.addPost("Topic", "T", "owner"); // id0
                    guiForum.ModelForum.addReply("r1", "a", 0); // id0
                    guiForum.ModelForum.addReply("r2", "b", 0); // id1
                    List<?> l29 = guiForum.ModelForum.getRepliesByPostId(0);
                    passed = (l29 != null && l29.size() == 2);
                    actual = l29 == null ? "null" : l29.toString();
                    expected = "[reply id 0, reply id 1]";
                    break;
                case 30:
                    guiForum.ModelForum.addPost("P", "C", "owner"); // id0
                    List<?> posts = guiForum.ModelForum.getPostList();
                    passed = (posts != null && posts.size() >= 1);
                    actual = posts == null ? "null" : posts.toString();
                    expected = "List of Post objects (size>=1)";
                    break;
                default:
                    // skip tests beyond defined
                    System.out.println("Test " + testNo + " - not defined, skipping.");
                    return;
            }

            if (passed) {
                reportSuccess(testNo);
            } else {
                // When not passed, show expected & actual. If expected empty, show that.
                String expToShow = expected == null ? "null" : expected;
                String actToShow = actual == null ? "null" : actual;
                reportFailure(testNo, expToShow, actToShow);
            }

        } catch (Throwable ex) {
            String act = "Exception: " + ex.getClass().getSimpleName() + (ex.getMessage() == null ? "" : ": " + ex.getMessage());
            reportFailure(testNo, expected == null ? "null" : expected, act);
        }
    }

    /**
     * Removes a reply from the reply store by ID using reflection.
     *
     * <p>This helper is used by tests that need to simulate inconsistent or
     * corrupted internal state.</p>
     *
     * @param id the ID of the reply to remove
     * @throws Exception if reflection fails or the reply cannot be removed
     */
    private static void removeReplyFromStoreById(int id) throws Exception {
        Class<?> mf = Class.forName("guiForum.ModelForum");
        Field replyStoreField = mf.getDeclaredField("replyStore");
        replyStoreField.setAccessible(true);
        Object replyStore = replyStoreField.get(null); // static field

        // replyStore has methods: retrieve(int) and remove(Reply)
        java.lang.reflect.Method retrieve = replyStore.getClass().getMethod("retrieve", int.class);
        Object replyObj = retrieve.invoke(replyStore, id);
        if (replyObj != null) {
            java.lang.reflect.Method remove = replyStore.getClass().getMethod("remove", replyObj.getClass());
            remove.invoke(replyStore, replyObj);
        }
    }
}