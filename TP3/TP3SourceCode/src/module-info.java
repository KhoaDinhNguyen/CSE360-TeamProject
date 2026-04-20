/**
 * All modules that needed for the project
 */
module TP3Test {
	requires org.junit.jupiter.api;
	requires javafx.controls;
	requires java.sql;
	requires org.junit.jupiter.params;
	requires javafx.graphics;
	requires javafx.base;
	
	opens entityClasses to javafx.base;
	opens applicationMain to javafx.graphics, javafx.fxml;
}