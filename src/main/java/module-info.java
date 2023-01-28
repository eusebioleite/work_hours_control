module com.boozy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.boozy to javafx.fxml;
    opens com.boozy.controllers to javafx.fxml;
    opens com.boozy.tables to javafx.base;
    
    exports com.boozy;
    exports com.boozy.controllers;
    exports com.boozy.tables;
}
