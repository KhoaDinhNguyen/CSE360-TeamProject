package guiForum;

import CRUD.Post;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment; 

/**
 * This class extend the format of list cell
 */
public class PostFormatCell extends ListCell<Post>{
	
	// vertical box with 2 label 
	private VBox vbox;
	private Label row1Label;
	private Label row2Label;

	/**
	 * Default constructor
	 */
	public PostFormatCell() {
		this.vbox = new VBox(8);
		this.row1Label = new Label();
		this.row2Label = new Label();
		
		vbox.getChildren().addAll(row1Label, row2Label);
	}


	/**
	 * This method extends the format for cell view
	 */
	@Override 
	protected void updateItem(Post item, boolean empty) {
		super.updateItem(item, empty);
		
		setText(null);

		if (item != null) {
			row1Label.setText((item.hasRead(ViewerForum.theUser.getUserName())?"":"[UNREAD] ") + item.toString());
			row2Label.setText(Integer.toString(item.getReplyPostId().size()) + " replies");
		
			setGraphic(vbox);
		} else {
			setGraphic(null);
		}
	}
}
