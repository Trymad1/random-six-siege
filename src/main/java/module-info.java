module com.trymad {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.trymad to javafx.fxml;
    exports com.trymad;
}
