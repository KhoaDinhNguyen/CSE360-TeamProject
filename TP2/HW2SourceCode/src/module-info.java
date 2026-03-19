/**
 * 
 */
/**
 * 
 */
module HW2SourceCode {
	requires org.junit.jupiter.api;
	requires javafx.controls;
	requires java.sql;
	requires org.junit.jupiter.params;
	
	opens entityClasses to javafx.base;
	opens applicationMain to javafx.graphics, javafx.fxml;
}