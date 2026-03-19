package guiForum;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import static guiForum.ModelForum.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import CRUD.Post;
	
public class ModelForumTest {
	private static CRUD.PostStore postStore;
	private static CRUD.ReplyStore replyStore;
	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	@BeforeEach
	void setup() {
		postStore = new CRUD.PostStore();
		replyStore = new CRUD.ReplyStore();
		
		 // Sample POSTS
	    addPost("Welcome", "Welcome to the CSE 360 discussion forum!", "Admin");

	    addPost("Java OOP Question",
	            "Can someone explain inheritance vs composition?",
	            "Alice");

	    addPost("Binary Search Confusion",
	            "Why is binary search O(log n)?",
	            "Bob");

	    addPost("Database Normalization",
	            "What is 3NF and why is it important?",
	            "Charlie");

	    addPost("Git Merge Conflict",
	            "How do you resolve a merge conflict safely?",
	            "David");

	    addPost("Recursion Depth",
	            "Why do I get StackOverflowError in Java?",
	            "Emma");

	    // Sample REPLIES

	    addReply("Inheritance models an 'is-a' relationship.", "Admin", 1);
	    addReply("Composition is usually more flexible.", "Alice", 1);

	    addReply("Each step halves the search space.", "Charlie", 2);
	    addReply("That’s why it grows logarithmically.", "Admin", 2);

	    addReply("3NF removes transitive dependency.", "Emma", 3);

	    addReply("Always pull before pushing changes.", "Bob", 4);
	    addReply("Use git status to inspect conflicts.", "Admin", 4);

	    addReply("Infinite recursion without base case causes it.", "Alice", 5);
	}
	
	@Nested
	class CreatePost {
		@RepeatedTest(3)
		void shouldReturnEmptyTitleErrorMessage_whenPostTitleIsEmpty() {
			// Given
			String title = "";
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Title could not be empty";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@RepeatedTest(3)
		void shouldReturnEmptyContentErrorMessage_whenPostContentIsEmpty() {
			// Given
			String title = generateRandomString(30);
			String content = "";
			String author = generateRandomString(30);
			
			String expected = "Content could not be empty";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@Test
		void shouldReturnEmptyTitleContentErrorMessage_whenPostTitleAndContentAreEmpty() {
			// Given
			String title = "";
			String content = "";
			String author = generateRandomString(30);
			
			String expected = "Title Content could not be empty";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@ParameterizedTest
		@ValueSource(ints = {301, 500, 1000})
		void shouldReturnOverflowTitleErrorMessage_whenPostTitleIsOver300Characters(Integer titleLength) {
			String title = generateRandomString(titleLength);
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Title length can not be longer than 300";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@ParameterizedTest
		@ValueSource(ints = {2001, 2050, 4000})
		void shouldReturnOverflowContentErrorMessage_whenPostTitleIsOver2000Characters(Integer contentLength) {
			String title = generateRandomString(30);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Content length can not be longer than 2000";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		@ParameterizedTest(name = "titleLength={0}, contentLength={1}")
		@CsvSource(value= {"302, 2002", "600, 3000", "1002, 5000"})
		void shouldReturnOverflowTitleAndContentErrorMessage_WhenPostTitleAndContentAreOverflow(Integer titleLength, Integer contentLength) {
			String title = generateRandomString(titleLength);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Title Content exceed character limitions (title: 300, content: 2000)";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
			
		}
		
		@Test
		void shouldReturnAuthorErrorMessage_whenPostAuthorIsNull() {
			String title = generateRandomString(30);
			String content = generateRandomString(30);
			String author = null;
			
			String expected = "Author can’t be null";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		@ParameterizedTest(name = "titleLength={0}, contentLength={1}")
		@CsvSource(value= {"10, 200", "50, 100", "200, 3000"})
		void shouldReturnNoErrorMessage_whenPostTitleContentAuthorAreValid(Integer titleLength, Integer contentLength) {
			String title = generateRandomString(titleLength);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "";
			
			// When
			String actual = addPost(title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
//		void shouldReturnPostInOrder_whenPostCreateAreSuccess() {
//			String title = generateRandomString(30);
//			String content = generateRandomString(30);
//			String author = generateRandomString(30);
//			
//			addPost(title, content, author);
//			ArrayList<Post> expectedArray = 
//		}
	}
	
	private static String generateRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		
		Random random = new Random();
		StringBuilder builder = new StringBuilder(length);
		
		for (int i = 0; i < length; ++i) {
			int randomIndex = random.nextInt(ALPHANUMERIC.length());
			builder.append(ALPHANUMERIC.charAt(randomIndex));
		}
		
		return builder.toString();
	}
}