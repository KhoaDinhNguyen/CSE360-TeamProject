package guiGradingSystem;

import java.util.ArrayList;

import entityClasses.Post;
import entityClasses.Reply;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment; 

/**
 * This class extend the format of list cell
 */
public class ListCellFormat extends ListCell<Post>{
	
	// vertical box with 2 label 
	private VBox vbox;
	private Label row1Label;
	private Label row2Label;

	/**
	 * Default constructor
	 */
	public ListCellFormat() {
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
			row1Label.setText((item.hasRead(ViewGradingSystem.theUser.getUserName())?"":"[UNREAD] ") + item.toString());
			
			int repliesCount = item.getReplyPostId().size();
			int unreadRepliesCount = ModelGradingSystem.getReplyStore()
					.getUnreadReplies(ViewGradingSystem.theUser.getUserName(), item.getId()).size();
			
//			ArrayList<Reply> test = ModelGradingSystem.getReplyStore().getReplyList();
//					//.getUnreadReplies(ViewGradingSystem.theUser.getUserName(), item.getId());
//			
//			System.out.println("-> " + item.getId());
//			for (int i = 0; i < test.size(); i++) {
//				System.out.println(test.get(i).getContent() + ", " + test.get(i).getParentPostId());
//			}
			
			row2Label.setText(repliesCount + (repliesCount <= 1? " reply" : " replies")
								+ ", " + unreadRepliesCount + " unread"
					);
		
			setGraphic(vbox);
		} else {
			setGraphic(null);
		}
	}
}
