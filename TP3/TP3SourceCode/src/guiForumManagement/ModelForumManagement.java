package guiForumManagement;

import java.util.ArrayList;
import java.util.List;

import entityClasses.Post;
import entityClasses.ThreadStore;
import guiForum.ModelForum;

public class ModelForumManagement {
	private static ThreadStore theThreadStore = ModelForum.getThreadStore();
	private static entityClasses.PostStore postStore = ModelForum.getPostStore();
	private static entityClasses.ReplyStore replyStore = ModelForum.getReplyStore();
	
	protected static String createThread(String threadName) {
		if (threadName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (theThreadStore.checkThreadExist(threadName)) {
			return "Thread already existed in the database";
		}
		
		theThreadStore.addThread(threadName);
		
		return "";
	}
	
	protected static String validateDeleteThread(String threadName) {
		if (threadName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (threadName.compareTo("General") == 0) {
			return "Cannot delete General thread";
		}
		else if (!theThreadStore.checkThreadExist(threadName)) {
			return "Thread does not exist in the database";
		}
		
		return "";
	}
	
	protected static String deteleThread(String threadName) {
		System.out.println(theThreadStore.getAllThreads().size());
		String errorMessage = validateDeleteThread(threadName);
		
		if (!errorMessage.isEmpty()) return errorMessage;
		
		theThreadStore.deleteThread(threadName);
		
		List<Post> postList = ModelForum.getPostList();
		
		for (int i = 0; i < postList.size(); ++i) {
			Post p = postList.get(i);
			
			if (p.getThread().compareTo(threadName) == 0) {
				ModelForum.editPost(p.getId(), "General", p.getAuthor(), p.getTitle(), p.getContent());
			}
		}
		
		return "";
	}
	
	protected static String validateEditThread(String oldName, String newName) {
		if (newName.isBlank()) {
			return "Thread's name cannot be empty";
		}
		else if (oldName.compareTo("General") == 0) {
			return "Cannot edit General thread";
		}
		else if (!theThreadStore.checkThreadExist(oldName)) {
			return "Thread does not exist in the database";
		}
		else if (theThreadStore.checkThreadExist(newName)) {
			return "Thread already exists in the database";
		}
		
		return "";
	}
	
	protected static String editThread(String oldName, String newName) {
		String errorMessage = validateEditThread(oldName, newName);
		
		if (!errorMessage.isEmpty()) return errorMessage;
		
		theThreadStore.editThread(oldName, newName);
		
		List<Post> postList = ModelForum.getPostList();
		
		for (int i = 0; i < postList.size(); ++i) {
			Post p = postList.get(i);
			
			if (p.getThread().compareTo(oldName) == 0) {
				ModelForum.editPost(p.getId(), newName, p.getAuthor(), p.getTitle(), p.getContent());
			}
		}
		
		return "";
	}
	
	protected static ArrayList<String> readAllThread() {
		return theThreadStore.getAllThreads();
	}
	
}