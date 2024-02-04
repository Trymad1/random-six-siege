module com.trymad {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.trymad to javafx.fxml;
    exports com.trymad;
    exports com.trymad.controller;
}
