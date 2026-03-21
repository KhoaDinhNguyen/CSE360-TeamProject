package guiForum;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import static guiForum.ModelForum.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import CRUD.Post;
import entityClasses.*;

/**
 * <p>ModalForumTest class is used to contain all test cases related to Post, Reply, and Thread on CRUD operations and test them</p>
 */
public class ModelForumTest {
	private CRUD.PostStore postStore;
	private CRUD.ReplyStore replyStore;
	private ThreadStore threadStore;
	
	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	/**
	 * <p>The class constructor but it will not be used in this project</p>
	 */
	public ModelForumTest() {
		
	}
	
	/**
	 * <p>Sets up store management for post, reply, and thread before each test case</p>
	 */
	@BeforeEach
	public void setUp() {
		// By default, Model Forum creates 6 threads, 6 posts, and 8 replies
		postStore = getPostStore();
		replyStore = getReplyStore();
		threadStore = getThreadStore();
	}
	
	/**
	 * <p>Resets store management to the default state after each test case </p>
	 */
	@AfterEach
	public void tearDown() {
		hardReset();
		setUpDefaultForum();
	}
	
	/**
	 * <p>PostCreateTest class is used to contain all test cases related to CREATE operation on Post, called by {@code addPost}
	 */
	@Nested
	@DisplayName("Post CREATE test cases")
	public class PostCreateTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public PostCreateTest() {
			
		}
		
		/**
		 * <p>Verifies that post title could not be empty</p>
		 */
		@Test
		public void shouldReturnEmptyTitleErrorMessage_whenPostTitleIsEmpty() {
			// Given
			String thread = "General";
			String title = "";
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Title could not be empty";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyContentErrorMessage_whenPostContentIsEmpty() {
			// Given
			String thread = "Lectures";
			String title = generateRandomString(30);
			String content = "";
			String author = generateRandomString(30);
			
			String expected = "Content could not be empty";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title and content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyTitleContentErrorMessage_whenPostTitleAndContentAreEmpty() {
			// Given
			String thread = "Social";
			String title = "";
			String content = "";
			String author = generateRandomString(30);
			
			String expected = "Title Content could not be empty";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title's max length is 300</p>
		 * @param titleLength is an integer value greater than 300
		 */
		@ParameterizedTest
		@ValueSource(ints = {301, 500, 1000})
		public void shouldReturnOverflowTitleErrorMessage_whenPostTitleIsOver300Characters(Integer titleLength) {
			String thread = "General";
			String title = generateRandomString(titleLength);
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Title length can not be longer than 300";
			
			// When	
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post content's max length is 2000</p>
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest
		@ValueSource(ints = {2001, 2050, 4000})
		public void shouldReturnOverflowContentErrorMessage_whenPostTitleIsOver2000Characters(Integer contentLength) {
			String thread = "Problem Sets";
			String title = generateRandomString(30);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Content length can not be longer than 2000";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title and content's max length are 300 and 2000, respectively</p>
		 * @param titleLength is an integer value greater than 300
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest(name = "titleLength={0}, contentLength={1}")
		@CsvSource(value= {"302, 2002", "600, 3000", "1002, 5000"})
		public void shouldReturnOverflowTitleAndContentErrorMessage_WhenPostTitleAndContentAreOverflow(Integer titleLength, Integer contentLength) {
			String thread = "Assignments";
			String title = generateRandomString(titleLength);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Title Content exceed character limitions (title: 300, content: 2000)";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
			
		}
		
		/**
		 * <p>Verifies that post's author could not be null</p>
		 */
		@Test
		public void shouldReturnAuthorErrorMessage_whenPostAuthorIsNull() {
			String thread = "General";
			String title = generateRandomString(30);
			String content = generateRandomString(30);
			String author = null;
			
			String expected = "Author can’t be null";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post's thread must be existed in the database</p>
		 * @param threadName is a String that represents thead's names do not exist in the database
		 */
		@ParameterizedTest
		@ValueSource(strings = {"Ideas", "Notifications", "Clubs"})
		public void shouldReturnNonExistedThreadErrorMessage_whenPostThreadDoesNotExist(String threadName) {
			// Given
			String thread = threadName;
			String title = generateRandomString(30);
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Thread does not exist in the database";
			
			// When 
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that default post's thread is <em>General</em></p>
		 */
		@Test
		public void shouldReturnGeneralThread_whenPostThreadIsEmpty() {
			// Given
			String thread = "";
			String title = generateRandomString(30);
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "";
			Post expectedPost = new Post(-1, "General", title, content, author);
			
			// When 
			String actual = addPost(thread, title, content, author);
			Post actualPost = postStore.getPostList().getLast();
			
			// Then
			assertAll(
					() -> assertEquals(expected, actual),
					() -> assertEquals(expectedPost, actualPost));
		}
		
		/**
		 * Verifies that {@code addPost} successfully processes requests when all parameters meet the required validation criteria.
		 * @param threadName is a String that represents thread's names exist in the database
		 * @param titleLength is an integer between 1 and 300
		 * @param contentLength is an integer between 1 and 2000
		 */
		@ParameterizedTest(name = "thread={0}, titleLength={1}, contentLength={2}")
		@CsvSource(value= {"General, 10, 200", "Lectures, 50, 100", "Problem Sets, 200, 3000"})
		public void shouldReturnNoErrorMessage_whenPostThreadTitleContentAuthorAreValid(String threadName, Integer titleLength, Integer contentLength) {
			String thread = threadName;
			String title = generateRandomString(titleLength);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "";
			
			// When
			String actual = addPost(thread, title, content, author);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>PostUpdateTest class is used to contain all test cases related to UPDATE operation on Post, called by {@code editPost}
	 */
	@Nested
	@DisplayName("Post UPDATE test cases")
	public class PostUpdateTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public PostUpdateTest() {
			
		}
		
		/**
		 * <p>Verifies that post must be existed before being edited</p>
		 * @param postId is an integer represents post id does not exist in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {-1, 6, 1000})
		public void shouldReturnNonExistPostErrorMessage_whenPostDoesNotExist(int postId) {
			// Given
			int id = postId;
			String newThread = "Problem Sets";
			String newTitle = generateRandomString(30);
			String newContent = generateRandomString(30);
			String author = "student";
			
			String expected = "Post doesn't exist";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyTitleErrorMessage_whenPostTitleIsEmpty() {
			// Given
			int id = 1;
			Post post = postStore.retrieve(id);
			String newThread = "Social";
			String newTitle = "";
			String newContent = generateRandomString(30);
			String author = post.getAuthor();
			
			String expected = "Title could not be empty";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyContentErrorMessage_whenPostContentIsEmpty() {
			// Given
			int id = 2;
			Post post = postStore.retrieve(id);
			String newThread = "Problem Sets";
			String newTitle = generateRandomString(30);
			String newContent = "";
			String author = post.getAuthor();
			
			String expected = "Content could not be empty";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title and content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyTitleContentErrorMessage_whenPostTitleAndContentAreEmpty() {
			// Given
			int id = 3;
			Post post = postStore.retrieve(id);
			String newThread = "General";
			String newTitle = "";
			String newContent = "";
			String author = post.getAuthor();
			
			String expected = "Title Content could not be empty";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title's max length is 300</p>
		 * @param titleLength is an integer value greater than 300
		 */
		@ParameterizedTest
		@ValueSource(ints = {303, 403, 1400})
		public void shouldReturnOverflowTitleErrorMessage_whenPostTitleIsOver300Characters(Integer titleLength) {
			int id = 3;
			Post post = postStore.retrieve(id);
			String newThread = "Assignments";
			String newTitle = generateRandomString(titleLength);
			String newContent = generateRandomString(30);
			String author = post.getAuthor();
			
			String expected = "Title length can not be longer than 300";
			
			// When	
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post content's max length is 2000</p>
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest
		@ValueSource(ints = {2003, 2500, 3000})
		public void shouldReturnOverflowContentErrorMessage_whenPostTitleIsOver2000Characters(Integer contentLength) {
			int id = 4;
			Post post = postStore.retrieve(id);
			String newThread = "Lectures";
			String newTitle = generateRandomString(30);
			String newContent = generateRandomString(contentLength);
			String author = post.getAuthor();
			
			String expected = "Content length can not be longer than 2000";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post title and content's max length are 300 and 2000, respectively</p>
		 * @param titleLength is an integer value greater than 300
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest(name = "titleLength={0}, contentLength={1}")
		@CsvSource(value= {"305, 2070", "670, 3021", "1700, 3000"})
		public void shouldReturnOverflowTitleAndContentErrorMessage_WhenPostTitleAndContentAreOverflow(Integer titleLength, Integer contentLength) {		
			int id = 5;
			Post post = postStore.retrieve(id);
			String newThread = "Generals";
			String newTitle = generateRandomString(titleLength);
			String newContent = generateRandomString(contentLength);
			String author = post.getAuthor();
			
			String expected = "Title Content exceed character limitions (title: 300, content: 2000)";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
			
		}
		
		/**
		 * <p>Verifies that only post's author can edit their post</p>
		 */
		@Test
		public void shouldReturnAuthorErrorMessage_whenNotPostAuthorEditTheirPost() {
			int id = 1;
			String newThread = "Generals";
			String newTitle = generateRandomString(30);
			String newContent = generateRandomString(30);
			String author = generateRandomString(3);
			
			String expected = "Can't edit other's user post";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post's thread must be existed in the database</p>
		 * @param threadName is a String that represents thead's names do not exist in the database
		 */
		@ParameterizedTest
		@ValueSource(strings = {"Ideas", "Notifications", "Clubs"})
		public void shouldReturnNonExistedThreadErrorMessage_whenPostThreadDoesNotExist(String threadName) {
			// Given
			int id = 2;
			Post post = postStore.retrieve(id);
			String newThread = "Generals";
			String newTitle = generateRandomString(30);
			String newContent = generateRandomString(30);
			String author = post.getAuthor();
			
			String expected = "Thread does not exist in the database";
			
			// When 
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that {@code editPost} successfully processes requests when all parameters meet the required validation criteria.
		 * @param postId is an integer that represents post id exists in the database
		 * @param threadName is a String that represents thread's names exist in the database
		 * @param titleLength is an integer between 1 and 300
		 * @param contentLength is an integer between 1 and 2000
		 */
		@ParameterizedTest(name = "id={0}, thread={1}, titleLength={2}, contentLength={3}")
		@CsvSource(value= {"2, General, 10, 200", "1, Lectures, 50, 100", "3, Problem Sets, 200, 3000"})
		public void shouldReturnNoErrorMessage_whenPostIdThreadTitleContentAuthorAreValid(int postId, String threadName, Integer titleLength, Integer contentLength) {
			int id = postId;
			Post post = postStore.retrieve(id);
			String newThread = threadName;
			String newTitle = generateRandomString(titleLength);
			String newContent = generateRandomString(contentLength);
			String author = post.getAuthor();

			String expected = "";
			
			// When
			String actual = editPost(id, newThread, author, newTitle, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
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