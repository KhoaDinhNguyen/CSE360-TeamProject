package entityClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

public class PostStoreTest {
	
	static PostStore testPostStore;

	public PostStoreTest() {
		
	}
	
	@BeforeEach
	public void setUp() {
		testPostStore = new PostStore();
		
		for (int i = 0; i < 10; i++)
			testPostStore.addPost(new Post(i, "" , "", "" , ""));
	}
	
	@AfterEach 
	public void tearDown() {
		testPostStore.hardReset();
	}
	
	@Test
	public void BoundaryTest1() {
		Post returnObj = testPostStore.getPostList().get(0);

		int expectedID = 0;
		
		assertEquals(expectedID, returnObj.getId());
	}

	@Test
	public void BoundaryTest2() {
		Post returnObj = testPostStore.getPostList().get(1);

		int expectedID = 1;
		
		assertEquals(expectedID, returnObj.getId());
	}

	@Test
	public void BoundaryTest3() {
		Post returnObj = testPostStore.getPostList().get(5);

		int expectedID = 5;
		
		assertEquals(expectedID, returnObj.getId());
	}

	@Test
	public void BoundaryTest4() {
		Post returnObj = testPostStore.getPostList().get(8);

		int expectedID = 8;
		
		assertEquals(expectedID, returnObj.getId());
	}

	@Test
	public void BoundaryTest5() {
		Post returnObj = testPostStore.getPostList().get(9);

		int expectedID = 9;
		
		assertEquals(expectedID, returnObj.getId());
	}

	@Test
	public void BoundaryTest6() {
		try {
			Post returnObj = testPostStore.getPostList().get(-1);
		} catch (Exception e) {
			assertEquals("Index -1 out of bounds for length 10", e.getMessage());
		}
	}
		
	@Test
	public void BoundaryTest7() {
		try {
			Post returnObj = testPostStore.getPostList().get(10);
		} catch (Exception e) {
			assertEquals("Index 10 out of bounds for length 10", e.getMessage());
		}
	}
	
	@Test
	public void DeleteAPost1() {
		testPostStore.deletePost(testPostStore.getPostList().get(0));
		
		Post returnObj = testPostStore.getPostList().get(0);
		
		int expectedID = 1;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test
	public void DeleteAPost2() {
		testPostStore.deletePost(testPostStore.getPostList().get(0));
		
		Post returnObj = testPostStore.getPostList().get(1);
		
		int expectedID = 2;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test
	public void DeleteAPost3() {
		testPostStore.deletePost(testPostStore.getPostList().get(0));
		
		Post returnObj = testPostStore.getPostList().get(5);
		
		int expectedID = 6;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test
	public void DeleteAPost4(){
		testPostStore.deletePost(testPostStore.getPostList().get(0));
		
		Post returnObj = testPostStore.getPostList().get(8);
		
		int expectedID = 9;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test
	public void DeleteAPost5() {
		try {
			testPostStore.deletePost(testPostStore.getPostList().get(0));
		
			Post returnObj = testPostStore.getPostList().get(9);
		} catch (Exception e) {
			assertEquals("Index 9 out of bounds for length 9", e.getMessage());
		}
	}
	
	@Test 
	public void AddAPost1() {
		testPostStore.addPost(new Post(10, "", "", "", ""));
		
		Post returnObj = testPostStore.getPostList().get(10);
		
		int expectedID = 10;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test 
	public void AddAPost2() {
		testPostStore.addPost(new Post(10, "", "", "", ""));
		
		Post returnObj = testPostStore.getPostList().get(9);
		
		int expectedID = 9;
		
		assertEquals(expectedID, returnObj.getId());
	}
	
	@Test 
	public void AddAPost3() {
		testPostStore.addPost(new Post(10, "", "", "", ""));
		
		try {
			Post returnObj = testPostStore.getPostList().get(11);
		} catch (Exception e) {
			assertEquals("Index 11 out of bounds for length 11", e.getMessage());
		}
	}
	
	@Test
	public void UnreadPost1() {
		ArrayList<Post> UnreadList = testPostStore.getUnreadPosts("dummy");
		
		assertEquals(UnreadList.size(), testPostStore.getPostList().size());
	}
	
	@Test 
	public void UnreadPost2() {
		testPostStore.retrieve(0).markAsRead("dummy");
		testPostStore.retrieve(9).markAsRead("dummy");
		ArrayList<Post> UnreadList = testPostStore.getUnreadPosts("dummy");
		
		assertEquals(UnreadList.size(), testPostStore.getPostList().size() - 2);
	}
}
