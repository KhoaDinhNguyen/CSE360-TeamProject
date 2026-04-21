package guiGradingSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import entityClasses.GradingSystem;

/**
 * This class is used to test the grading system
 */
public class TestGradingSystem {
	
	/**
	 * The example GradingSystem for the testcases 
	 */
	static private GradingSystem gradeSys;
	
	/**
	 * Default constructor 
	 */
	public TestGradingSystem() {
		
	}
	
	/**
	 * Before each testcases, set up three assignments
	 */
	@BeforeEach 
	public void setUp() {
		gradeSys = new GradingSystem();
		
		gradeSys.createAssignment("Assignment1", "", 100, 25);
		gradeSys.createAssignment("Assignment2", "", 100, 25);
		gradeSys.createAssignment("Assignment3", "", 100, 50);
	}
	
	/**
	 * Positive case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary1() {
		int expectedGrade = 0;
		gradeSys.setMaxScore(0, expectedGrade);
		
		assertEquals(gradeSys.getMaxScore(0), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary2() {
		int expectedGrade = 1;
		gradeSys.setMaxScore(0, expectedGrade);
		
		assertEquals(gradeSys.getMaxScore(0), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary3() {
		int expectedGrade = 50;
		gradeSys.setMaxScore(0, expectedGrade);
		
		assertEquals(gradeSys.getMaxScore(0), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary4() {
		int expectedGrade = 99;
		gradeSys.setMaxScore(0, expectedGrade);
		
		assertEquals(gradeSys.getMaxScore(0), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary5() {
		int expectedGrade = 100;
		gradeSys.setMaxScore(0, expectedGrade);
		
		assertEquals(gradeSys.getMaxScore(0), expectedGrade);
	}
	
	/**
	 * Negative case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary6() {
		String errMsg = gradeSys.setMaxScore(0, -1);
		String expectedMsg = "Negative max grade are not allowed";
		
		assertEquals(errMsg, expectedMsg);
	}
	
	/**
	 * Negative case: This case test the boundary of max grade 
	 */
	@Test
	public void MaxGradeBoundary7() {
		String errMsg = gradeSys.setMaxScore(0, 101);
		String expectedMsg = "Max grade should not exceed 100";
		
		assertEquals(errMsg, expectedMsg);
	}
	
	/**
	 * Positive case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary1() {
		int expectedGrade = 0;
		gradeSys.setFeedback(0, "me", expectedGrade, "");
		
		assertEquals(gradeSys.getScore(0, "me"), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary2() {
		int expectedGrade = 1;
		gradeSys.setFeedback(0, "me", expectedGrade, "");
		
		assertEquals(gradeSys.getScore(0, "me"), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary3() {
		int expectedGrade = 50;
		gradeSys.setFeedback(0, "me", expectedGrade, "");
		
		assertEquals(gradeSys.getScore(0, "me"), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary4() {
		int expectedGrade = 99;
		gradeSys.setFeedback(0, "me", expectedGrade, "");
		
		assertEquals(gradeSys.getScore(0, "me"), expectedGrade);
	}
	
	/**
	 * Positive case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary5() {
		int expectedGrade = 100;
		gradeSys.setFeedback(0, "me", expectedGrade, "");
		
		assertEquals(gradeSys.getScore(0, "me"), expectedGrade);
	}
	
	/**
	 * Negative case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary6() {
		String errMsg = gradeSys.setFeedback(0, "me", -1, "");
		String expectedMsg = "Negative grade are not allowed";
		
		assertEquals(expectedMsg, errMsg);
	}

	/**
	 * Negative case: This case test the boundary of assign grade 
	 */
	@Test
	public void AssignGradeBoundary7() {
		String errMsg = gradeSys.setFeedback(0, "me", 101, "");
		String expectedMsg = "Assigned grade cannot be exceed the max grade";
		
		assertEquals(expectedMsg, errMsg);
	}
	
	/**
	 * Positive case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary1() {
		int expectedWeight = 0;
		gradeSys.setWeight(0, expectedWeight);
		
		assertEquals(expectedWeight, gradeSys.getWeight(0));
	}

	/**
	 * Positive case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary2() {
		int expectedWeight = 1;
		gradeSys.setWeight(0, expectedWeight);
		
		assertEquals(expectedWeight, gradeSys.getWeight(0));
	}

	/**
	 * Positive case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary3() {
		int expectedWeight = 50;
		gradeSys.setWeight(0, expectedWeight);
		
		assertEquals(expectedWeight, gradeSys.getWeight(0));
	}

	/**
	 * Positive case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary4() {
		int expectedWeight = 99;
		gradeSys.setWeight(0, expectedWeight);
		
		assertEquals(expectedWeight, gradeSys.getWeight(0));
	}

	/**
	 * Positive case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary5() {
		int expectedWeight = 100;
		gradeSys.setWeight(0, expectedWeight);
		
		assertEquals(expectedWeight, gradeSys.getWeight(0));
	}

	/**
	 * Negative case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary6() {
		int expectedWeight = -1;
		String errMsg = gradeSys.setWeight(0, expectedWeight);
		String expectedMsg = "Weight percentage cannot be negative";
		
		assertEquals(expectedMsg, errMsg);
	}

	/**
	 * Negative case: This case test the boundary of weight 
	 */
	@Test
	public void WeightBoundary7() {
		int expectedWeight = 101;
		String errMsg = gradeSys.setWeight(0, expectedWeight);
		String expectedMsg = "Weight percentage cannot exceed 100";
		
		assertEquals(expectedMsg, errMsg);
	}
	
	/**
	 * Positive case: This case check for the total weight which require to be 100
	 */
	@Test
	public void TotalWeight1() {
		// already have three assignments
		assertEquals(100, gradeSys.getTotalWeight());
	}

	/**
	 * Negative case: This case check for the total weight which require to be 100
	 */
	@Test
	public void TotalWeight2() {
		// already have three assignments
		// change the second one to 30 
		String errMsg = gradeSys.setWeight(1, 30);
		String expectedMsg = "The total weight is not 100";

		assertEquals(expectedMsg, errMsg);
	}
	
	/**
	 * <p>Positive case: this case test the calculation of the total grade</p>
	 * 
	 * <p>2 Assignment will be created with max points of 10 and weight of 30 and 70</p>
	 */
	@Test
	public void TotalGrade1() {
		gradeSys = new GradingSystem();
		
		gradeSys.createAssignment("Assn1", "", 10, 30);
		gradeSys.createAssignment("Assn2", "", 10, 70);
		
		gradeSys.setFeedback(0, "me", 5, "");
		gradeSys.setFeedback(1, "me", 7, "");
		
		float expectedGrade = 64;
		float totalGrade = gradeSys.getTotalScore("me");
		
		assertEquals(expectedGrade, totalGrade);
	}

	/**
	 * <p>Positive case: this case test the calculation of the total grade</p>
	 * 
	 * <p>2 Assignment will be created with max points of 10 and weight of 30 and 70</p>
	 */
	@Test
	public void TotalGrade2() {
		gradeSys = new GradingSystem();
		
		gradeSys.createAssignment("Assn1", "", 10, 30);
		gradeSys.createAssignment("Assn2", "", 10, 70);
		
		gradeSys.setFeedback(0, "me", 0, "");
		gradeSys.setFeedback(1, "me", 0, "");
		
		float expectedGrade = 0;
		float totalGrade = gradeSys.getTotalScore("me");
		
		assertEquals(expectedGrade, totalGrade);
	}

	/**
	 * <p>Positive case: this case test the calculation of the total grade</p>
	 * 
	 * <p>2 Assignment will be created with max points of 10 and weight of 30 and 70</p>
	 */
	@Test
	public void TotalGrade3() {
		gradeSys = new GradingSystem();
		
		gradeSys.createAssignment("Assn1", "", 10, 30);
		gradeSys.createAssignment("Assn2", "", 10, 70);
		
		gradeSys.setFeedback(0, "me", 10, "");
		gradeSys.setFeedback(1, "me", 10, "");

		float expectedGrade = 100;
		float totalGrade = gradeSys.getTotalScore("me");
		
		assertEquals(expectedGrade, totalGrade);
	}
}
