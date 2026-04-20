package guiReviewingDiscussionDashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ReplyStore;
import guiForum.ModelForum;

/**
 * Provides unit tests for the reviewing discussion dashboard model.
 *
 * <p>This test class verifies the calculation methods used by
 * {@link ModelReviewingDiscussionDashboard}, including averages and totals
 * for posts and replies.</p>
 */
class TestingReviewingDiscussionDashboard {

    /**
     * The post store used for test setup.
     */
    private PostStore postStore;

    /**
     * The reply store used for test setup.
     */
    private ReplyStore replyStore;

    /**
     * The list of users used in the test cases.
     */
    private ArrayList<String> users;

    /**
     * Initializes the test data before each test case.
     *
     * <p>This method clears the existing post and reply data, then creates
     * a fixed set of posts and replies for use in the assertions below.</p>
     */
    @BeforeEach
    void setUp() {
        postStore = ModelForum.getPostStore();
        replyStore = ModelForum.getReplyStore();

        users = new ArrayList<>();
        users.add("All Students");
        users.add("Alice");
        users.add("Bob");
        users.add("Charlie");
        users.add("David");

        postStore.getPostList().clear();
        replyStore.getReplyList().clear();

        Post p1 = new Post(1, "General", "Post 1", "Hello everyone", "Alice");
        p1.getReadUsers().add("Bob");
        p1.getReadUsers().add("Charlie");

        Post p2 = new Post(2, "Lectures", "Post 2", "Java OOP basics", "Bob");
        p2.getReadUsers().add("Alice");

        Post p3 = new Post(3, "Sections", "Post 3", "Binary search is fast", "Alice");
        p3.getReadUsers().add("Bob");
        p3.getReadUsers().add("Charlie");
        p3.getReadUsers().add("David");

        Post p4 = new Post(4, "Assignments", "Post 4", "Data normalization rules", "Charlie");
        p4.getReadUsers().add("Alice");
        p4.getReadUsers().add("Bob");

        postStore.getPostList().add(p1);
        postStore.getPostList().add(p2);
        postStore.getPostList().add(p3);
        postStore.getPostList().add(p4);

        Reply r1 = new Reply(1, "Yes, exactly", "Alice", 1);
        r1.getReadUsers().add("Bob");
        r1.getReadUsers().add("Charlie");

        Reply r2 = new Reply(2, "Try comparing steps", "Bob", 1);
        r2.getReadUsers().add("Alice");
        r2.getReadUsers().add("Charlie");

        Reply r3 = new Reply(3, "That helps", "Charlie", 2);
        r3.getReadUsers().add("Alice");

        Reply r4 = new Reply(4, "Use recursion carefully", "David", 3);
        r4.getReadUsers().add("Alice");
        r4.getReadUsers().add("Bob");
        r4.getReadUsers().add("Charlie");

        Reply r5 = new Reply(5, "Check your base case", "Alice", 3);
        r5.getReadUsers().add("Bob");

        replyStore.getReplyList().add(r1);
        replyStore.getReplyList().add(r2);
        replyStore.getReplyList().add(r3);
        replyStore.getReplyList().add(r4);
        replyStore.getReplyList().add(r5);
    }

    /**
     * Tests the average read-post calculation for all students.
     */
    @Test
    void averageReadPost_allStudents() {
        assertEquals(3.0,
                ModelReviewingDiscussionDashboard.averageReadPost(null, users),
                0.0001);
    }

    /**
     * Tests the average read-post calculation for a single student.
     */
    @Test
    void averageReadPost_singleStudent() {
        assertEquals(4.0,
                ModelReviewingDiscussionDashboard.averageReadPost("Alice", users),
                0.0001);
    }

    /**
     * Tests the average read-replies calculation for all students.
     */
    @Test
    void averageReadReplies_allStudents() {
        assertEquals(3.5,
                ModelReviewingDiscussionDashboard.averageReadReplies(null, users),
                0.0001);
    }

    /**
     * Tests the average read-replies calculation for a single student.
     */
    @Test
    void averageReadReplies_singleStudent() {
        assertEquals(5.0,
                ModelReviewingDiscussionDashboard.averageReadReplies("Alice", users),
                0.0001);
    }

    /**
     * Tests the average post-length calculation for all students.
     */
    @Test
    void averagePostLength_allStudents() {
        assertEquals(18.5,
                ModelReviewingDiscussionDashboard.averagePostLength("", users),
                0.0001);
    }

    /**
     * Tests the average post-length calculation for a single student.
     */
    @Test
    void averagePostLength_singleStudent() {
        assertEquals(35.0,
                ModelReviewingDiscussionDashboard.averagePostLength("Alice", users),
                0.0001);
    }

    /**
     * Tests the average reply-length calculation for all students.
     */
    @Test
    void averageReplyLength_allStudents() {
        assertEquals(21.0,
                ModelReviewingDiscussionDashboard.averageReplyLength(" ", users),
                0.0001);
    }

    /**
     * Tests the average reply-length calculation for a single student.
     */
    @Test
    void averageReplyLength_singleStudent() {
        assertEquals(32.0,
                ModelReviewingDiscussionDashboard.averageReplyLength("Alice", users),
                0.0001);
    }

    /**
     * Tests the total number of created posts for all students.
     */
    @Test
    void numberOfCreatedPost_allStudents() {
        assertEquals(4.0,
                ModelReviewingDiscussionDashboard.numberOfCreatedPost(null, users),
                0.0001);
    }

    /**
     * Tests the total number of created posts for Alice.
     */
    @Test
    void numberOfCreatedPost_alice() {
        assertEquals(2.0,
                ModelReviewingDiscussionDashboard.numberOfCreatedPost("Alice", users),
                0.0001);
    }

    /**
     * Tests the total number of created replies for all students.
     */
    @Test
    void numberOfCreatedReply_allStudents() {
        assertEquals(5.0,
                ModelReviewingDiscussionDashboard.numberOfCreatedReply(null, users),
                0.0001);
    }

    /**
     * Tests the total number of created replies for Alice.
     */
    @Test
    void numberOfCreatedReply_alice() {
        assertEquals(2.0,
                ModelReviewingDiscussionDashboard.numberOfCreatedReply("Alice", users),
                0.0001);
    }
}