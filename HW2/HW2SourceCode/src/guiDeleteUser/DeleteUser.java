package guiDeleteUser;

import database.Database;

public class DeleteUser {
	private static final Database db = applicationMain.FoundationsMain.database;
	
	public static boolean deleteUser(String username) {	
		username = username.trim();
		
		if (username.isEmpty()) return false;
		if (!db.doesUserExist(username)) return false;

		return db.deleteUserByUsername(username);
	}
}