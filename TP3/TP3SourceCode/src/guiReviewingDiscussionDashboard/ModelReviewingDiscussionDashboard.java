package guiReviewingDiscussionDashboard;

import java.util.ArrayList;

import entityClasses.Post;
import entityClasses.PostStore;
import entityClasses.Reply;
import entityClasses.ReplyStore;

public class ModelReviewingDiscussionDashboard {

    private static PostStore postStore = guiForum.ModelForum.getPostStore();
    private static ReplyStore repliesStore = guiForum.ModelForum.getReplyStore();

    public ModelReviewingDiscussionDashboard() {
    }

    private static boolean isAllStudents(String student) {
        return student == null || student.isBlank();
    }

    private static int getNumberOfStudents(String student) {
        if (isAllStudents(student)) {
            int totalUsers = ViewerReviewingDiscussionDashboard.USERS.size() - 1; // remove "All Students"
            return Math.max(totalUsers, 0);
        }
        return 1;
    }

    protected static double averageReadPost(String student) {
        ArrayList<Post> postList = postStore.getPostList();
        int numberOfStudents = getNumberOfStudents(student);

        if (numberOfStudents == 0) {
            return 0;
        }

        double totalPostReads = 0;

        for (Post post : postList) {
            if (isAllStudents(student)) {
                totalPostReads += post.getReadUsers().size();
            } else if (post.getReadUsers().contains(student)) {
                totalPostReads += 1;
            }
        }

        return totalPostReads / numberOfStudents;
    }

    protected static double averageReadReplies(String student) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();
        int numberOfStudents = getNumberOfStudents(student);

        if (numberOfStudents == 0) {
            return 0;
        }

        double totalReplyReads = 0;

        for (Reply reply : replyList) {
            if (isAllStudents(student)) {
                totalReplyReads += reply.getReadUsers().size();
            } else if (reply.getReadUsers().contains(student)) {
                totalReplyReads += 1;
            }
        }

        return totalReplyReads / numberOfStudents;
    }

    protected static double averagePostLength(String student) {
        ArrayList<Post> postList = postStore.getPostList();
        int numberOfStudents = getNumberOfStudents(student);

        if (numberOfStudents == 0) {
            return 0;
        }

        double totalPostLength = 0;

        for (Post post : postList) {
            if (isAllStudents(student)) {
                totalPostLength += post.getContent().length();
            } else if (student.equals(post.getAuthor())) {
                totalPostLength += post.getContent().length();
            }
        }

        return totalPostLength / numberOfStudents;
    }

    protected static double averageReplyLength(String student) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();
        int numberOfStudents = getNumberOfStudents(student);

        if (numberOfStudents == 0) {
            return 0;
        }

        double totalReplyLength = 0;

        for (Reply reply : replyList) {
            if (isAllStudents(student)) {
                totalReplyLength += reply.getContent().length();
            } else if (student.equals(reply.getAuthor())) {
                totalReplyLength += reply.getContent().length();
            }
        }

        return totalReplyLength / numberOfStudents;
    }

    protected static double numberOfCreatedPost(String student) {
        ArrayList<Post> postList = postStore.getPostList();

        if (isAllStudents(student)) {
            return postList.size();
        }

        double totalPost = 0;

        for (Post post : postList) {
            if (student.equals(post.getAuthor())) {
                totalPost += 1;
            }
        }

        return totalPost;
    }

    protected static double numberOfCreatedReply(String student) {
        ArrayList<Reply> replyList = repliesStore.getReplyList();

        if (isAllStudents(student)) {
            return replyList.size();
        }

        double totalReply = 0;

        for (Reply reply : replyList) {
            if (student.equals(reply.getAuthor())) {
                totalReply += 1;
            }
        }

        return totalReply;
    }
}