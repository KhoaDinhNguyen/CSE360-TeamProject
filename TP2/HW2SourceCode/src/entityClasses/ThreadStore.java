package entityClasses;

import java.util.ArrayList;

/**
 * <p> This ThreadStore class manages the thread CRUD operation in the database</p>
 */
public class ThreadStore {
	private ArrayList<String> ThreadList;
	
	/**
	 * Constructor is used to initialize thread list
	 */
	public ThreadStore() {
		this.ThreadList = new ArrayList<String>();
	}
	
	/**
	 * Create new thread into the database
	 * @param name is a String that represents the name of new thread
	 */
	public void addThread(String name) {
		this.ThreadList.add(name);
	}
	
	/**
	 * Read all threads in the database
	 * @return a String list contains all threads
	 */
	public ArrayList<String> getAllThreads() {
		return this.ThreadList;
	}
	
	/**
	 * Check whether the thread already exists in the database
	 * @param name is a String that represents the name of thread that wanted to check
	 * @return a boolean true if the thread is in the database already, otherwise, return false
	 */
	public boolean checkThreadExist(String name) {
		if (this.ThreadList.contains(name)) {
			return true;
		}
		
		return false;
	}
}