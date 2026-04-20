package guiReviewingDiscussionDashboard;

import java.util.ArrayList;

import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ReplyStore;

/**
 * Provides data calculations for the discussion dashboard review page.
 *
 * <p>This model class computes summary statistics for posts and replies,
 * including average read counts, average content lengths, and total numbers
 * of created posts and replies.</p>
 */
public class ModelReviewingDiscussionDashboard {

    /**
     * The store containing all forum posts.
     */
    private static PostStore postStore = guiForum.ModelForum.getPostStore();

    /**
     * The store containing all forum replies.
     */
    private static ReplyStore repliesStore = guiForum.ModelForum.getReplyStore();

    /**
     * Creates the reviewing discussion dashboard model.
     */
    public ModelReviewingDiscussionDashboard() {}

    /**
     * Determines whether the provided student value represents all students.
     *
     * @param student the selected student name or blank value
     * @return true if all students should be included; otherwise false
     */
    private static boolean isAllStudents(String student) {
        return student == null || student.isBlank();
    }

    /**
     * Returns the number of students to use in an average calculation.
     *
     * <p>If all students are selected, the method returns the total number of
     * available users excluding the "All Students" option. Otherwise, it returns 1.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the number of students to use for averaging
     */
    private static int getNumberOfStudents(String student, ArrayList<String> users) {
        if (isAllStudents(student)) {
            int totalUsers = users.size() - 1; // remove "All Students"
            return Math.max(totalUsers, 0);
        }
        return 1;
    }

    /**
     * Computes the average number of posts read.
     *
     * <p>If all students are selected, this calculates the total read counts across
     * all posts and divides by the number of students. If a specific student is selected,
     * only posts read by that student are counted.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the average number of posts read
     */
    protected static double averageReadPost(String student, ArrayList<String> users) {
        ArrayList<Post> postList = postStore.getPostList();
        int numberOfStudents = getNumberOfStudents(student, users);

        if (numberOfStudents == 0) return 0;

        double total = 0;

        for (Post post : postList) {
            if (isAllStudents(student)) {
                total += post.getReadUsers().size();
            } else if (post.getReadUsers().contains(student)) {
                total += 1;
            }
        }

        return total / numberOfStudents;
    }

    /**
     * Computes the average number of replies read.
     *
     * <p>If all students are selected, this calculates the total read counts across
     * all replies and divides by the number of students. If a specific student is selected,
     * only replies read by that student are counted.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the average number of replies read
     */
    protected static double averageReadReplies(String student, ArrayList<String> users) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();
        int numberOfStudents = getNumberOfStudents(student, users);

        if (numberOfStudents == 0) return 0;

        double total = 0;

        for (Reply reply : replyList) {
            if (isAllStudents(student)) {
                total += reply.getReadUsers().size();
            } else if (reply.getReadUsers().contains(student)) {
                total += 1;
            }
        }

        return total / numberOfStudents;
    }

    /**
     * Computes the average length of posts written by the selected student or by all students.
     *
     * <p>If all students are selected, this averages the length of all post contents.
     * If one student is selected, only that student's posts are included.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the average post content length
     */
    protected static double averagePostLength(String student, ArrayList<String> users) {
        ArrayList<Post> postList = postStore.getPostList();
        int numberOfStudents = getNumberOfStudents(student, users);

        if (numberOfStudents == 0) return 0;

        double total = 0;

        for (Post post : postList) {
            if (isAllStudents(student)) {
                total += post.getContent().length();
            } else if (student.equals(post.getAuthor())) {
                total += post.getContent().length();
            }
        }

        return total / numberOfStudents;
    }

    /**
     * Computes the average length of replies written by the selected student or by all students.
     *
     * <p>If all students are selected, this averages the length of all reply contents.
     * If one student is selected, only that student's replies are included.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the average reply content length
     */
    protected static double averageReplyLength(String student, ArrayList<String> users) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();
        int numberOfStudents = getNumberOfStudents(student, users);

        if (numberOfStudents == 0) return 0;

        double total = 0;

        for (Reply reply : replyList) {
            if (isAllStudents(student)) {
                total += reply.getContent().length();
            } else if (student.equals(reply.getAuthor())) {
                total += reply.getContent().length();
            }
        }

        return total / numberOfStudents;
    }

    /**
     * Counts the number of posts created by the selected student or by all students.
     *
     * <p>If all students are selected, this returns the total number of posts.
     * Otherwise, it counts only posts authored by the selected student.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the number of created posts
     */
    protected static double numberOfCreatedPost(String student, ArrayList<String> users) {
        ArrayList<Post> postList = postStore.getPostList();

        if (isAllStudents(student)) {
            return postList.size();
        }

        double total = 0;

        for (Post post : postList) {
            if (student.equals(post.getAuthor())) {
                total++;
            }
        }

        return total;
    }

    /**
     * Counts the number of replies created by the selected student or by all students.
     *
     * <p>If all students are selected, this returns the total number of replies.
     * Otherwise, it counts only replies authored by the selected student.</p>
     *
     * @param student the selected student name or blank value
     * @param users the list of available users
     * @return the number of created replies
     */
    protected static double numberOfCreatedReply(String student, ArrayList<String> users) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();

        if (isAllStudents(student)) {
            return replyList.size();
        }

        double total = 0;

        for (Reply reply : replyList) {
            if (student.equals(reply.getAuthor())) {
                total++;
            }
        }

        return total;
    }
}