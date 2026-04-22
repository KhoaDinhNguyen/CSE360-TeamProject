package guiMessage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * {@code ModelMessageTest} contains all test cases related to message conversation
 */
public class ModelMessageTest {
	/**
	 * The constructor of the class but it will not be used
	 */
	public ModelMessageTest() {}
	
	/**
	 * Verifies that sender could not be null
	 */
	@Test
	public void shouldReturnUserDoesNotExist_whenSenderIsNull() {
		// Given
		String content = "Hello";
		String sender = null;
		String receiver = "Staff";
		String expected = "Invalid sender: User does not exist";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies that sender must be existed in the database
	 */
	@Test
	public void shouldReturnUserDoesNotExist_whenSenderDoesNotExist() {
		// Given
		String content = "Hello";
		String sender = "fake user";
		String receiver = "Staff";
		String expected = "Invalid sender: User does not exist";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies that sender must be student of staff
	 */
	@Test
	public void shouldAccessDenied_whenSenderIsAdmin() {
		// Given
		String content = "Hello";
		String sender = "Admin";
		String receiver = "Staff";
		String expected = "Invalid sender: Access denied";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies that receiver cannot be null
	 */
	@Test
	public void shouldReturnUserDoesNotExist_whenReceiverIsNull() {
		// Given
		String content = "Hello";
		String sender = "Student";
		String receiver = null;
		String expected = "Invalid receiver: User does not exist";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies that receiver must be existed in the database
	 */
	@Test
	public void shouldReturnUserDoesNotExist_whenReceiverDoesNotExist() {
		// Given
		String content = "Hello";
		String sender = "Student";
		String receiver = "fake";
		String expected = "Invalid receiver: User does not exist";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}


	/**
	 * Verifies that receiver must be a student or staff
	 */
	@Test
	public void shouldReturnAccessDenied_whenReceiverIsAdmin() {
		// Given
		String content = "Hello";
		String sender = "Student";
		String receiver = "Admin";
		String expected = "Invalid receiver: Access denied";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies the content cannot be null
	 */
	@Test
	public void shouldReturnInvalidContent_whenContentIsNull() {
		// Given
		String content = null;
		String sender = "Student";
		String receiver = "Staff";
		String expected = "Content cannot be null";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies the content cannot be empty
	 */
	@Test
	public void shouldReturnInvalidContent_whenContentIsEmpty() {
		// Given
		String content = "";
		String sender = "Student";
		String receiver = "Staff";
		String expected = "Content cannot be empty";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}
	
	/**
	 * Verifies that sending message is successful when all parameters are valid
	 */
	@Test
	public void shouldReturnNoError_whenAllAreValid() {
		// Given
		String content = "Hello class";
		String sender = "Student";
		String receiver = "Staff";
		String expected = "";
		
		// When
		String actual = ModelMessage.addMessage(content, sender, receiver);
		
		// Then
		assertEquals(expected, actual);
	}

}
