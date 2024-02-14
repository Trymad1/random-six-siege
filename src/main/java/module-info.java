module com.trymad {
    requires java.desktop;
    requires transitive org.json;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.trymad to javafx.fxml;
    opens com.trymad.controller to javafx.fxml;
    exports com.trymad;
    exports com.trymad.model;
    exports com.trymad.controller;
    exports com.trymad.api;
}
