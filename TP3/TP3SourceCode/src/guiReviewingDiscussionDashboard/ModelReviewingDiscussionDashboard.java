package guiReviewingDiscussionDashboard;

import java.util.ArrayList;

import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ReplyStore;

public class ModelReviewingDiscussionDashboard {

    private static PostStore postStore = guiForum.ModelForum.getPostStore();
    private static ReplyStore repliesStore = guiForum.ModelForum.getReplyStore();

    public ModelReviewingDiscussionDashboard() {}

    // ---------------- HELPERS ----------------

    private static boolean isAllStudents(String student) {
        return student == null || student.isBlank();
    }

    private static int getNumberOfStudents(String student, ArrayList<String> users) {
        if (isAllStudents(student)) {
            int totalUsers = users.size() - 1; // remove "All Students"
            return Math.max(totalUsers, 0);
        }
        return 1;
    }

    // ---------------- AVERAGE READ POST ----------------

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



    // ---------------- AVERAGE READ REPLIES ----------------

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



    // ---------------- AVERAGE POST LENGTH ----------------

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

  

    // ---------------- AVERAGE REPLY LENGTH ----------------

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

    

    // ---------------- COUNT POSTS ----------------

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

    
    // ---------------- COUNT REPLIES ----------------

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