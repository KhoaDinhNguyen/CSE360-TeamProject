package guiReviewingDiscussionDashboard;

import java.util.ArrayList;

/**
 * Controls user interactions for the reviewing discussion dashboard.
 *
 * <p>This controller manages student filtering and metric selection,
 * and coordinates updates to the dashboard view based on user actions.</p>
 */
public class ControllerReviewingDiscussionDashboard {

	/**
	 * The constructor of the class but it will not be used
	 */
	public ControllerReviewingDiscussionDashboard() {}
    /**
     * The currently selected student for filtering.
     *
     * <p>An empty string represents "All Students".</p>
     */
    private static String selectedStudent = "";

    /**
     * The list of currently selected dashboard metrics.
     */
    private static ArrayList<String> selectedMetrics = new ArrayList<>();

    /**
     * Handles the student selection change event.
     *
     * <p>This method updates the selected student filter and refreshes
     * the dashboard cards accordingly.</p>
     *
     * @param selectedName the selected student name from the UI
     */
    protected static void performStudentFilter(String selectedName) {
        if ("All Students".equals(selectedName)) {
            selectedStudent = "";
        } else {
            selectedStudent = selectedName;
        }

        ViewerReviewingDiscussionDashboard.refreshCards(selectedMetrics, selectedStudent);
    }

    /**
     * Applies the selected metrics to the dashboard.
     *
     * <p>This method updates the internal metric list and refreshes
     * the dashboard cards to reflect the new selections.</p>
     *
     * @param metrics the list of selected metrics
     */
    protected static void performApplyMetrics(ArrayList<String> metrics) {
        selectedMetrics.clear();
        selectedMetrics.addAll(metrics);

        ViewerReviewingDiscussionDashboard.refreshCards(selectedMetrics, selectedStudent);
    }

    /**
     * Returns the currently selected student filter.
     *
     * @return the selected student, or an empty string if all students are selected
     */
    protected static String getSelectedStudent() {
        return selectedStudent;
    }

    /**
     * Returns the list of currently selected dashboard metrics.
     *
     * @return the list of selected metrics
     */
    protected static ArrayList<String> getSelectedMetrics() {
        return selectedMetrics;
    }
}