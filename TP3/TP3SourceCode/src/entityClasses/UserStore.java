package entityClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code UserStore} is used to contain database operation related to User object
 */
public class UserStore {
	private	ArrayList<User> UserList;
	
	/**
	 * The constructor of the class
	 */
	public UserStore() {
		UserList = new ArrayList<User>();
		
		UserList.addAll(List.of(
			new User("Admin", "", "Admin", "", "", "", "", true, false, false),
			new User("Alice", "", "Alice", "", "", "", "", false, true, false),
			new User("Bob", "", "Bob", "", "", "", "", false, true, false),
			new User("Charlie", "", "Charlie", "", "", "", "", false, true, false),
			new User("David", "", "David", "", "", "", "", false, true, false),
			new User("Emma", "", "Emma", "", "", "", "", false, true, false),
			new User("Franklin", "", "Franklin", "", "", "", "", false, true, false),
			new User("Miller", "", "Miller", "", "", "", "", false, true, false),
			new User("Naomi", "", "Naomi", "", "", "", "", false, true, false),
			new User("Patrik", "", "Patrik", "", "", "", "", false, true, false),
			new User("Resse", "", "Resse", "", "", "", "", false, true, false),
			new User("Staff", "", "Staff", "", "", "", "", false, false, true),
			new User("Student", "", "Student", "", "", "", "", false, true, false)
				));
	}
	
	/**
	 * Get the User object from the database given unique username
	 * @param username is an unique String representing the user in the database
	 * @return an User object, null if the user does not exist
	 */
	public User retrieve(String username) {
		if (username == null) return null;
		
		for (int i = 0; i < UserList.size(); ++i) {
			User user = UserList.get(i);
			if (user.getUserName().compareTo(username) == 0) {
				return user;
			}
		}
		
		return null;
	}
	
	/**
	 * Get all the User that are students and staff
	 * @return a list of User object
	 */
	public ArrayList<User> getStudentAndStaff() {
		ArrayList<User> result = new ArrayList<>();
		
		for (int i = 0; i < UserList.size(); ++i) {
			User user = UserList.get(i);
			
			if (user.getStaff() || user.getStudent()) {
				result.add(user);
			}
		}
		
		return result;
	}
	
	
	/**
	 * Verifies that whether the user is a staff
	 * @param username is a unique String representing user from the database
	 * @return a String that display error message, empty if the user exists and is a staff
	 */
	public String isUserStaff(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStaff()) return "Access denied";
		
		return "";
	}
	
	/**
	 * Verifies that whether the user is a student
	 * @param username is a unique String representing user from the database
	 * @return a String that display error message, empty if the user exists and is a student
	 */
	public String isUserStudent(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStudent()) return "Access denied";
		
		return "";
	}
	
	/**
	 * Verifies that whether the user is a student or a staff
	 * @param username is a unique String representing user from the database
	 * @return a String that display error message, empty if the user exists and is a student or a staff
	 */
	public String isUserStaffAndStudent(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStudent() && !user.getStaff()) return "Access denied";
		
		return "";
	}
	
	/**
	 * Add user to the database
	 * @param user is User object that represents new user
	 */
	public void addUser(User user) {
		UserList.add(user);
	}
}