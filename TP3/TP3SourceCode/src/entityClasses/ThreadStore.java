package entityClasses;

import java.util.ArrayList;

/**
 * <p> This ThreadStore class manages the thread CRUD operation in the database</p>
 */
public class ThreadStore {
	private ArrayList<String> ThreadList;
	
	/**
	 * <p>Constructor is used to initialize thread list</p>
	 */
	public ThreadStore() {
		this.ThreadList = new ArrayList<String>();
	}
	
	/**
	 * <p>Create new thread into the database</p>
	 * @param name is a String that represents the name of new thread
	 */
	public void addThread(String name) {
		this.ThreadList.add(name);
	}
	
	public void deleteThread(String name) {
		this.ThreadList.remove(name);
	}
	
	public void editThread(String oldName, String newName) {
		for (int i = 0; i < ThreadList.size(); ++i) {
			if(ThreadList.get(i).compareTo(oldName) == 0) {
				ThreadList.set(i, newName);
			}
		}
	}
	
	/**
	 * <p>Read all threads in the database</p>
	 * @return a String list contains all threads
	 */
	public ArrayList<String> getAllThreads() {
		return this.ThreadList;
	}
	
	/**
	 * <p>Check whether the thread already exists in the database</p>
	 * @param name is a String that represents the name of thread that wanted to check
	 * @return a boolean true if the thread is in the database already, otherwise, return false
	 */
	public boolean checkThreadExist(String name) {
		if (this.ThreadList.contains(name)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Reset the lists
	 */
	public void hardReset() {
		this.ThreadList.clear();
	}
}