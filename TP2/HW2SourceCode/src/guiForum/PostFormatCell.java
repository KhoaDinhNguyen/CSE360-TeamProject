package guiForum;

import CRUD.Post;
import javafx.scene.control.ListCell; 

/**
 * This class extend the format of list cell
 */
public class PostFormatCell extends ListCell<Post>{

	/**
	 * Default contructor, unused
	 */
	public PostFormatCell() {}

	/**
	 * This method extends the format for cell view
	 */
	@Override 
	protected void updateItem(Post item, boolean empty) {
		super.updateItem(item, empty);
		
		setText(item == null? " ": (item.hasRead(ViewerForum.theUser.getUserName())?"":"[UNREAD] ") + item.toString());
	}
}
