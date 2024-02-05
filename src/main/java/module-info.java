module com.trymad {
    requires java.desktop;
    requires org.json;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.trymad to javafx.fxml;
    exports com.trymad;
    exports com.trymad.controller;
}
