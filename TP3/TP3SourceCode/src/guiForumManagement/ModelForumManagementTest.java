package guiForumManagement;

import static guiForum.ModelForum.getPostStore;
import static guiForum.ModelForum.getReplyStore;
import static guiForum.ModelForum.getThreadStore;
import static guiForum.ModelForum.hardReset;
import static guiForum.ModelForum.setUpDefaultForum;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import entityClasses.ThreadStore;

/**
 * {@code ModelForumManagementTest} contains all test cases related to forum management
 */
public class ModelForumManagementTest {
	
	/**
	 * A constructor of the class but it will not be used
	 */
	public ModelForumManagementTest() {}
	
	/**
	 * Resets store management to the default state after each test case
	 */
	@AfterEach
	public void tearDown() {
		hardReset();
		setUpDefaultForum();
	}
	
	/**
	 * {@code CreateThread} contains all test cases related to CREATE operation on thread, called by {@code createThread}
	 */
	@Nested
	@DisplayName("Thread CREATE test cases")
	public class CreateThread {
		/**
		 * A constructor of the class but it will not be used
		 */
		public CreateThread() {}
		
		/**
		 * Verifies that new thread name cannot be empty
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameIsEmpty() {
			// Given
			String threadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that new thread name cannot be duplicated
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameAlreadyExistsInDatabase() {
			// Given
			String threadName = "Social";
			String expected = "Thread already existed in the database";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that if thread name is valid, then new thread is created successfully
		 */
		@Test
		public void shouldReturnNoMessage_whenAllAreValid() {
			// Given
			String threadName = "Projects";
			String expected = "";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * {@code ReadThread} contains all test cases related to READ operation on thread
	 */
	@Nested
	@DisplayName("Thread READ test cases")
	public class ReadThread {
		/**
		 * A constructor of the class but it will not be used
		 */
		public  ReadThread() {}
		
		/**
		 * Verifies that all thread when called by {@code readAllThread}
		 */
		@Test
		public void shouldReturnAllThread_whenAllThreadIsCalled() {
			// Given
			ArrayList<String> expected = new ArrayList<>(List.of(
					"General", "Lectures", "Sections", "Problem Sets", "Assignments", "Social"));
			
			// When
			ArrayList<String> actual = ModelForumManagement.readAllThread();
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
	}
	
	
	/**
	 * {@code UpdateThread} contains all test cases related to UPDATE operation on thread, called by {@code editThread}
	 */
	@Nested
	@DisplayName("Thread UPDATE test cases")
	public class UpdateThread {
		/**
		 * A constructor of the class but it will not be used
		 */
		public UpdateThread() {}
		
		/**
		 * Verifies that new thread name cannot be empty
		 */
		@Test
		public void shouldReturnInvalidThread_whenNewThreadNameIsEmpty() {
			// Given
			String oldThreadName = "Social";
			String newThreadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that old thread name exists in the database
		 */
		@Test
		public void shouldReturnInvalidThread_whenOldThreadNameDoesNotExistInDatabase() {
			// Given
			String oldThreadName = "Homework";
			String newThreadName = "Homeworks";
			String expected = "Thread does not exist in the database";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that new thread name could not be duplicated
		 */
		@Test
		public void shouldReturnInvalidThread_whenNewThreadNameAlreadyExistsInDatabase() {
			// Given
			String oldThreadName = "Social";
			String newThreadName = "Assignments";
			String expected = "Thread already exists in the database";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that General thread cannot be edited
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameIsGeneral() {
			// Given
			String oldThreadName = "General";
			String newThreadName = "Default";
			String expected = "Cannot edit General thread";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that editing thread is successful when all threads are valid
		 */
		@Test
		public void shouldReturnNoErrorMessage_whenAllAreValid() {
			// Given
			String oldThreadName = "Social";
			String newThreadName = "Discussion";
			String expected = "";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * {@code DeleteThread} contains all test cases related to DELETE operation on thread, called by {@code deteleThread}
	 */
	@Nested
	@DisplayName("Thread DELETE test cases")
	public class DeleteThread {
		/**
		 * A constructor of the class but it will not be used
		 */
		public DeleteThread() {}
		
		/**
		 * Verifies that deleted thread cannot be empty
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameIsEmpty() {
			// Given
			String threadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that deleted thread must be existed in the database
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameDoesNotExistInDatabase() {
			// Given
			String threadName = "Homework";
			String expected = "Thread does not exist in the database";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that General could not deleted
		 */
		@Test
		public void shouldReturnInvalidThread_whenThreadNameIsGeneral() {
			// Given
			String threadName = "General";
			String expected = "Cannot delete General thread";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that deleting thread is successful when all threads are valid
		 */
		@Test
		public void shouldReturnNoErrorMessage_whenAllAreValid() {
			// Given
			String threadName = "Social";
			String expected = "";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	

}
