module com.example {
	requires transitive javafx.controls;
	requires transitive java.sql;
	requires transitive javafx.base;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    opens com.example to javafx.graphics, javafx.fxml, java.sql, javafx.base;
    exports com.example;
}
