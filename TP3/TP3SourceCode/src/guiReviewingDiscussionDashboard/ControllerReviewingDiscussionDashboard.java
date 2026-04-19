package guiReviewingDiscussionDashboard;

import java.util.ArrayList;

public class ControllerReviewingDiscussionDashboard {

    private static String selectedStudent = "";
    private static ArrayList<String> selectedMetrics = new ArrayList<>();

    protected static void performStudentFilter(String selectedName) {
        if ("All Students".equals(selectedName)) {
            selectedStudent = "";
        } else {
            selectedStudent = selectedName;
        }

        ViewerReviewingDiscussionDashboard.refreshCards(selectedMetrics, selectedStudent);
    }

    protected static void performApplyMetrics(ArrayList<String> metrics) {
        selectedMetrics.clear();
        selectedMetrics.addAll(metrics);

        ViewerReviewingDiscussionDashboard.refreshCards(selectedMetrics, selectedStudent);
    }

    protected static String getSelectedStudent() {
        return selectedStudent;
    }

    protected static ArrayList<String> getSelectedMetrics() {
        return selectedMetrics;
    }
}