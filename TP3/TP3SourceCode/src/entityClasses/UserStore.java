package entityClasses;

import java.util.ArrayList;
import java.util.List;

public class UserStore {
	private	ArrayList<User> UserList;
	
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
	
	
	public String isUserStaff(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStaff()) return "Access denied";
		
		return "";
	}
	
	public String isUserStudent(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStudent()) return "Access denied";
		
		return "";
	}
	
	public String isUserStaffAndStudent(String username) {
		User user = retrieve(username);
		
		if (user == null) return "User does not exist";
		
		if (!user.getStudent() && !user.getStaff()) return "Access denied";
		
		return "";
	}
	
	public void addUser(User user) {
		UserList.add(user);
	}
}