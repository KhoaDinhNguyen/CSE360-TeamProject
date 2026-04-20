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

class ModelForumManagementTest {

	@AfterEach
	public void tearDown() {
		hardReset();
		setUpDefaultForum();
	}
	
	@Nested
	@DisplayName("Thread CREATE test cases")
	class CreateThread {
		@Test
		void shouldReturnInvalidThread_whenThreadNameIsEmpty() {
			// Given
			String threadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenThreadNameAlreadyExistsInDatabase() {
			// Given
			String threadName = "Social";
			String expected = "Thread already existed in the database";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnNoMessage_whenAllAreValid() {
			// Given
			String threadName = "Projects";
			String expected = "";
			
			// When
			String actual = ModelForumManagement.createThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	@Nested
	@DisplayName("Thread READ test cases")
	class ReadThread {
		@Test
		void shouldReturnAllThread_whenAllThreadIsCalled() {
			// Given
			ArrayList<String> expected = new ArrayList<>(List.of(
					"General", "Lectures", "Sections", "Problem Sets", "Assignments", "Social"));
			
			// When
			ArrayList<String> actual = ModelForumManagement.readAllThread();
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
	}
	
	@Nested
	@DisplayName("Thread UPDATE test cases")
	class UpdateThread {
		@Test
		void shouldReturnInvalidThread_whenNewThreadNameIsEmpty() {
			// Given
			String oldThreadName = "Social";
			String newThreadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenOldThreadNameDoesNotExistInDatabase() {
			// Given
			String oldThreadName = "Homework";
			String newThreadName = "Homeworks";
			String expected = "Thread does not exist in the database";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenNewThreadNameAlreadyExistsInDatabase() {
			// Given
			String oldThreadName = "Social";
			String newThreadName = "Assignments";
			String expected = "Thread already exists in the database";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenThreadNameIsGeneral() {
			// Given
			String oldThreadName = "General";
			String newThreadName = "Default";
			String expected = "Cannot edit General thread";
			
			// When
			String actual = ModelForumManagement.editThread(oldThreadName, newThreadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnNoErrorMessage_whenAllAreValid() {
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
	
	@Nested
	@DisplayName("Thread DELETE test cases")
	class DeleteThread {
		@Test
		void shouldReturnInvalidThread_whenThreadNameIsEmpty() {
			// Given
			String threadName = "";
			String expected = "Thread's name cannot be empty";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenThreadNameDoesNotExistInDatabase() {
			// Given
			String threadName = "Homework";
			String expected = "Thread does not exist in the database";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnInvalidThread_whenThreadNameIsGeneral() {
			// Given
			String threadName = "General";
			String expected = "Cannot delete General thread";
			
			// When
			String actual = ModelForumManagement.deteleThread(threadName);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnNoErrorMessage_whenAllAreValid() {
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
