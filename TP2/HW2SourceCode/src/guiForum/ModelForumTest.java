package guiForum;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static guiForum.ModelForum.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import entityClasses.*;

/**
 * <p>ModalForumTest class is used to contain all test cases related to Post, Reply, and Thread on CRUD operations and test them</p>
 */
public class ModelForumTest {
	private entityClasses.PostStore postStore;
	private entityClasses.ReplyStore replyStore;
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
	 * <p>ThreadCreateTest class is used to contain all test cases related to CREATE operation on Thread class, called by {@code addThread}
	 */
	@Nested
	@DisplayName("Thread CREATE test cases")
	public class ThreadCreateTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ThreadCreateTest() {
			
		}
		
		/**
		 * <p>Verifies that thread name could not be empty</p>
		 */
		@Test
		public void shouldReturnEmptyThreadErrorMessage_whenThreadNameEmpty() {
			// Given
			String thread = "";
			
			String expected = "Thread name could not be empty";
			
			// When
			String actual = addThread(thread);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that thread name's max length is 100</p>
		 * @param threadNameLength is an integer value greater than 100
		 */
		@ParameterizedTest
		@ValueSource(ints = {101, 200, 3000})
		public void shouldReturnOverflowThreadNameErrorMessage_whenThreadNameIsOver100Characters(Integer threadNameLength) {
			// Given
			String thread = generateRandomString(threadNameLength);
			
			String expected = "Thread name could not be longer than 100 characters";
			
			// When
			String actual = addThread(thread);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that thread can be only added once</p>
		 */
		@Test
		public void shouldReturnDuplicatedThreadErrorMessage_whenCreateExistedThread() {
			// When
			String thread = "General";
			
			String expected = "Thread name could not be duplicated";
			// When
			String actual = addThread(thread);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that {@code addThread} successfully processes requests when all parameters meet the required validation criteria.
		 * @param threadName is a String that represents  new thread's names
		 */
		@ParameterizedTest
		@ValueSource(strings= {"TP2", "Questions", "Exams"})
		public void shouldReturnNoErrorMessage_whenThreadNameIsValid(String threadName) {
			String thread = threadName;
			
			String expected = "";
			
			// When
			String actual = addThread(thread);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>ThreadReadTest class is used to contain all test cases related to READ operation on Thread class, called by many getter functions
	 */
	@Nested
	@DisplayName("Thread READ test cases")
	public class ThreadReadTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ThreadReadTest() {
			
		}
		
		/**
		 * <p>Verifies that all threads are listed when call by {@code getAllThreads}</p>
		 */
		@Test
		public void shouldReturnAllThread_whengetAllThreadsIsCalled() {
			// Given
			ArrayList<String> expected = new ArrayList<>(List.of(
					"General", 
					"Lectures",
					"Sections",
					"Problem Sets",
					"Assignments",
					"Social"
					));
						
			// When
			ArrayList<String> actual = getAllThreads();
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
	}
	/**
	 * <p>PostCreateTest class is used to contain all test cases related to CREATE operation on Post class, called by {@code addPost}
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
			
			String expected = "Title could not be empty\nContent could not be empty";
			
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
			
			String expected = "Title could not be longer than 300 characters";
			
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
		public void shouldReturnOverflowContentErrorMessage_whenPostContentIsOver2000Characters(Integer contentLength) {
			String thread = "Problem Sets";
			String title = generateRandomString(30);
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Content could not be longer than 2000 characters";
			
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
			
			String expected = "Title could not be longer than 300 characters\nContent could not be longer than 2000 characters";
			
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
			
			String expected = "Thread must be an existing thread";
			
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
		@CsvSource(value= {"General, 10, 200", "Lectures, 50, 100", "Problem Sets, 300, 2000"})
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
	 * <p>PostReadTest class is used to contain all test cases related to READ operation on Post class, called by many getter functions</p>
	 */
	@Nested
	@DisplayName("Post READ test cases")
	public class PostReadTest {
		/**
		 * The class constructor but it will not be used in this project
		 */
		public PostReadTest() {
			
		}
		
		/**
		 * Verifies that all post are listed when call by {@code getPostList}
		 */
		@Test
		public void shouldReturnAllPosts_whenGetPostListIsCalled() {
			// Given    
			ArrayList<Post> expected = new ArrayList<Post>(List.of(
					new Post(0, "General", "Welcome", "Welcome to the CSE 360 discussion forum!", "Admin"),
					new Post(1, "General", "Java OOP Question", "Can someone explain inheritance vs composition?", "Alice"),
					new Post(2, "General", "Binary Search Confusion", "Why is binary search O(log n)?", "Bob"),
					new Post(3, "General", "Database Normalization", "What is 3NF and why is it important?", "Charlie"),
					new Post(4, "General", "Git Merge Conflict", "How do you resolve a merge conflict safely?", "David"),
					new Post(5, "General", "Recursion Depth", "Why do I get StackOverflowError in Java?", "Emma")
					));
			
			// When
			ArrayList<Post> actual = postStore.getPostList();
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
		
		/**
		 * Verifies that a specific post is listed call by {@code retrieve}
		 */
		@Test
		public void shouldReturnSpecificPost_whenRetrieveIsCalled() {
			// Given    
			Post expected = new Post(2, "General", "Binary Search Confusion", "Why is binary search O(log n)?", "Bob");
			
			// When
			Post actual = postStore.retrieve(2);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that a subset of  posts is listed call by {@code filterPosts}
		 */
		@Test
		public void shouldReturnSubsetOfPosts_whenFilterPostsIsCalled() {
			// Given      
			ArrayList<Post> expected = new ArrayList<Post>(List.of(
					new Post(2, "General", "Binary Search Confusion", "Why is binary search O(log n)?", "Bob"),
					new Post(3, "General", "Database Normalization", "What is 3NF and why is it important?", "Charlie"),
					new Post(5, "General", "Recursion Depth", "Why do I get StackOverflowError in Java?", "Emma")
					));
			
			// When
			ArrayList<Post> actual = postStore.filterPosts("why", "General");
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
	}
	
	/**
	 * <p>PostUpdateTest class is used to contain all test cases related to UPDATE operation on Post class, called by {@code editPost}
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
			
			String expected = "Title could not be empty\nContent could not be empty";
			
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
			
			String expected = "Title could not be longer than 300 characters";
			
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
		public void shouldReturnOverflowContentErrorMessage_whenPostContentIsOver2000Characters(Integer contentLength) {
			int id = 4;
			Post post = postStore.retrieve(id);
			String newThread = "Lectures";
			String newTitle = generateRandomString(30);
			String newContent = generateRandomString(contentLength);
			String author = post.getAuthor();
			
			String expected = "Content could not be longer than 2000 characters";
			
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
			String newThread = "General";
			String newTitle = generateRandomString(titleLength);
			String newContent = generateRandomString(contentLength);
			String author = post.getAuthor();
			
			String expected = "Title could not be longer than 300 characters\nContent could not be longer than 2000 characters";
			
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
			
			String expected = "Thread must be an existing thread";
			
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
		@CsvSource(value= {"2, General, 10, 200", "1, Lectures, 50, 100", "3, Problem Sets, 300, 2000"})
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
	
	/**
	 * <p>PostDeleteTest class is used to contain all test cases related to DELETE operation on Post class, called by {@code deletePost}
	 */
	@Nested
	@DisplayName("Post DELETE test cases")
	public class PostDeleteTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public PostDeleteTest() {
			
		}
		
		/**
		 * <p>Verifies that post must be existed before being deleted</p>
		 * @param postId is an integer represents post id does not exist in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {-5, 8, 150})
		public void shouldReturnNonExistPostErrorMessage_whenPostDoesNotExist(int postId) {
			// Given
			int id = postId;
			String author = generateRandomString(3);
			
			String expected = "Post doesn't exist";
			
			// When
			String actual = deletePost(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post could only be deleted once</p>
		 * @param postId is an integer represents post id is deleted in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {1, 2, 3})
		public void shouldReturnDeletedPostErrorMessage_whenPostIsDeleted(int postId) {
			// Given
			int id = postId;
			Post post = postStore.retrieve(id);
			String author = post.getAuthor();
			
			deletePost(id, author);
			
			String expected = "Post already deleted";
			
			// When
			String actual = deletePost(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that only post's author can delete their post</p>
		 */
		@Test
		public void shouldReturnAuthorErrorMessage_whenNotPostAuthorDeleteTheirPost() {
			int id = 5;
			String author = generateRandomString(3);
			
			String expected = "Can't delete another user's post";
			
			// When
			String actual = deletePost(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that deleted post does not provide any information about thread, title, content and author</p>
		 */
		@Test
		public void shouldGetDeletedContextInTheadTitleContentAuthor_whenPostIsDeleted() {
			int id = 5;
			Post post = postStore.retrieve(id);
			String author = post.getAuthor();
			
			// When
			deletePost(id, author);
			
			// Then
			assertAll(
					() -> assertEquals(post.getThread(), "[DELETED]"),
					()-> assertEquals(post.getTitle(), "[DELETED]"),
					()-> assertEquals(post.getContent(), "[DELETED]"),
					()-> assertEquals(post.getAuthor(), "[DELETED]")
					);
		}
				
		/**
		 * Verifies that {@code deletePost} successfully processes requests when all parameters meet the required validation criteria.
		 * @param postId is an integer that represents post id exists in the database
		 */
		@ParameterizedTest(name = "id={0},")
		@ValueSource(ints = {1, 2, 4})
		public void shouldReturnNoErrorMessage_whenPostIdThreadTitleContentAuthorAreValid(int postId) {
			int id = postId;
			Post post = postStore.retrieve(id);
			String author = post.getAuthor();

			String expected = "";
			
			// When
			String actual = deletePost(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>ReplyCreateTest class is used to contain all test cases related to CREATE operation on Reply class, called by {@code addReply}
	 */
	@Nested
	@DisplayName("Reply CREATE test cases")
	public class ReplyCreateTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ReplyCreateTest() {
			
		}
		
		/**
		 * <p>Verifies that reply content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyContentErrorMessage_whenReplyContentIsEmpty() {
			// Given
			int id = 0;
			String content = "";
			String author = generateRandomString(30);
			
			String expected = "Content could not be empty";
			
			// When
			String actual = addReply(content, author, id);
			
			// Then
			assertEquals(expected, actual);
		}
	
		
		/**
		 * <p>Verifies that reply content's max length is 2000</p>
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest
		@ValueSource(ints = {2001, 2050, 4000})
		public void shouldReturnOverflowContentErrorMessage_whenReplyContentIsOver2000Characters(Integer contentLength) {
			int id = 1;
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "Content could not be longer than 2000 characters";
			
			// When
			String actual = addReply(content, author, id);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post must be existed before being replied</p>
		 * @param postId is an integer represents post id does not exist in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {-2, 7, 1001})
		public void shouldReturnNonExistPostErrorMessage_whenReplyToPostDoesNotExist(int postId) {
			// Given
			int id = postId;
			String content = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Parent post not found";
			
			// When
			String actual = addReply(content, author, id);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that deleted post cannot be replied</p>
		 */
		@Test
		public void shouldReturnDeletedPostErrorMessage_whenReplyToDeletedPost() {
			// Given
			int id = 0;
			Post post = postStore.retrieve(id);
			String content = generateRandomString(30);
			String author = post.getAuthor();			
			deletePost(id, author);

			String expected = "Cannot reply to a deleted post";
			
			// When
			String actual = addReply(content, author, id);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that {@code addReply} successfully processes requests when all parameters meet the required validation criteria.
		 * @param postId is an integer that represents post id exists in the database
		 * @param contentLength is an integer between 1 and 2000
		 */
		@ParameterizedTest(name = "postId={0}, contentLength={1}")
		@CsvSource(value= {"0, 200", "3, 100", "5, 2000"})
		public void shouldReturnNoErrorMessage_whenReplyPostParentAndContentAreValid(Integer postId, Integer contentLength) {
			int id = postId;
			String content = generateRandomString(contentLength);
			String author = generateRandomString(30);
			
			String expected = "";
			
			// When
			String actual = addReply(content, author, id);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>ReplyReadTest class is used to contain all test cases related to READ operation on Reply class, called by many getter functions</p>
	 */
	@Nested
	@DisplayName("Reply READ test cases")
	public class ReplyReadTest {
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ReplyReadTest() {
			
		}
		/**
		 * <p>Verifies that all replies are listed when call by {@code getReplyList}</p>
		 */
		@Test
		public void shouldReturnAllReplies_whenGetReplyListIsCalled() {
			// Given
			ArrayList<Reply> expected = new ArrayList<>(List.of(
					new Reply(0, "Inheritance models an 'is-a' relationship.", "Admin", 1),
					new Reply(1, "Composition is usually more flexible.", "Alice", 1),
					new Reply(2, "Each step halves the search space.", "Charlie", 2),
					new Reply(3, "That’s why it grows logarithmically.", "Admin", 2),
					new Reply(4, "3NF removes transitive dependency.", "Emma", 3),
					new Reply(5, "Always pull before pushing changes.", "Bob", 4),
					new Reply(6, "Use git status to inspect conflicts.", "Admin", 4),
					new Reply(7, "Infinite recursion without base case causes it.", "Alice", 5)
					));
			
			// When
			ArrayList<Reply> actual = replyStore.getReplyList();
			
			// Then
			assertArrayEquals(expected.toArray(), actual.toArray());
		}
		
		/**
		 * <p>Verifies that the specific reply is listed when called by {@code retrieve}</p>
		 */
		@Test
		public void shouldReturnSpecificReply_whenRetrieveIsCalled() {
			// Given
			Reply expected = new Reply(4, "3NF removes transitive dependency.", "Emma", 3);
			
			// When
			Reply actual = replyStore.retrieve(4);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>ReplyUpdateTest class is used to contain all test cases related to UPDATE operation on Reply class, called by {@code editReply}
	 */
	@Nested
	@DisplayName("Reply UPDATE test cases")
	public class ReplyUpdateTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ReplyUpdateTest() {
			
		}
		
		/**
		 * <p>Verifies that reply content could not be empty </p>
		 */
		@Test
		public void shouldReturnEmptyContentErrorMessage_whenReplyContentIsEmpty() {
			// Given
			int id = 0;
			Reply reply = replyStore.retrieve(id);
			
			String newContent = "";
			String author = reply.getAuthor();
			
			String expected = "Content could not be empty";
			
			// When
			String actual = editReply(id, author, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
	
		
		/**
		 * <p>Verifies that reply content's max length is 2000</p>
		 * @param contentLength is an integer value greater than 2000
		 */
		@ParameterizedTest
		@ValueSource(ints = {2001, 2050, 4000})
		public void shouldReturnOverflowContentErrorMessage_whenReplyContentIsOver2000Characters(Integer contentLength) {
			int id = 1;
			Reply reply = replyStore.retrieve(id);
			
			String newContent = generateRandomString(contentLength);
			String author = reply.getAuthor();
			
			String expected = "Content could not be longer than 2000 characters";
			
			// When
			String actual = editReply(id, author, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that post must be existed before being replied</p>
		 * @param replyId is an integer represents reply id does not exist in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {-3, 10, 2000})
		public void shouldReturnNonExistPostErrorMessage_whenReplyToPostDoesNotExist(int replyId) {
			// Given
			int id = replyId;
			String newContent = generateRandomString(30);
			String author = generateRandomString(30);
			
			String expected = "Reply doesn't exist";
			
			// When
			String actual = editReply(id, author, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that only reply's author can edit their reply</p>
		 */
		@Test
		public void shouldReturnAuthorErrorMessage_whenNotReplyAuthorEditTheirReply() {
			int id = 2;
			String newContent = generateRandomString(30);
			String author = generateRandomString(3);

			String expected = "Can't edit other's user reply";
			
			// When
			String actual = editReply(id, author, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * Verifies that {@code editReply} successfully processes requests when all parameters meet the required validation criteria.
		 * @param replyId is an integer that represents reply id exists in the database
		 * @param contentLength is an integer between 1 and 2000
		 */
		@ParameterizedTest(name = "replyId={0}, contentLength={1}")
		@CsvSource(value= {"0, 200", "3, 100", "5, 2000"})
		public void shouldReturnNoErrorMessage_whenReplyIdAndContentAndAuthorAreValid(Integer replyId, Integer contentLength) {
			int id = replyId;
			Reply reply = replyStore.retrieve(id);
			
			String newContent = generateRandomString(contentLength);
			String author = reply.getAuthor();
			
			String expected = "";
			
			// When
			String actual = editReply(id, author, newContent);
			
			// Then
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * <p>ReplyDeleteTest class is used to contain all test cases related to DELETE operation on Reply class, called by {@code deleteReply}
	 */
	@Nested
	@DisplayName("Reply DELETE test cases")
	public class ReplyDeleteTest {
		
		/**
		 * The class constructor but it will not be used in this project
		 */
		public ReplyDeleteTest() {
			
		}
		
		/**
		 * <p>Verifies that reply must be existed before being deleted</p>
		 * @param replyId is an integer represents reply id does not exist in the database 
		 */
		@ParameterizedTest
		@ValueSource(ints= {-10, 10, 150})
		public void shouldReturnNonExistReplyErrorMessage_whenReplyDoesNotExist(int replyId) {
			// Given
			int id = replyId;
			String author = generateRandomString(30);
			
			String expected = "Reply doesn't exist";
			
			// When
			String actual = deleteReply(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
		
		/**
		 * <p>Verifies that only reply's author can delete their reply</p>
		 */
		@Test
		public void shouldReturnAuthorErrorMessage_whenNotPostAuthorDeleteTheirReply() {
			int id = 2;
			String author = generateRandomString(3);
			
			String expected = "Can't delete other's user reply";
			
			// When
			String actual = deleteReply(id, author);
			
			// Then
			assertEquals(expected, actual);
		}
				
		/**
		 * Verifies that {@code deleteReply} successfully processes requests when all parameters meet the required validation criteria.
		 * @param replyId is an integer that represents reply id exists in the database
		 */
		@ParameterizedTest
		@ValueSource(ints = {1, 2, 4})
		public void shouldReturnNoErrorMessage_whenReplyIdContentAuthorAreValid(int replyId) {
			int id = replyId;
			Reply reply = replyStore.retrieve(id);
			String author = reply.getAuthor();

			String expected = "";
			
			// When
			String actual = deleteReply(id, author);
			
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