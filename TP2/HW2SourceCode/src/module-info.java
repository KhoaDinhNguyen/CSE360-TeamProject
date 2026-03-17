/**
 * 
 */
/**
 * 
 */
module HW2SourceCode {
	
	requires javafx.controls;
	requires java.sql;
	
	opens entityClasses to javafx.base;
	opens applicationMain to javafx.graphics, javafx.fxml;
}