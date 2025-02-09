module com.example {
	requires javafx.controls;
	requires transitive java.sql;
	requires javafx.base;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example to javafx.graphics, javafx.fxml, java.sql, javafx.base;
    exports com.example;
}
