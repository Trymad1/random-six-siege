package com.trymad.util;

import org.json.JSONException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class ExceptionAlertFactory {
    public static void createAlert(OperatorsDirectoryNotFound exception) {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Loading error");
        alert.setHeaderText("Can`t load operators data from operatorsData directory");
        alert.setContentText("Update operator data by running OperatorDataLoader.exe in the same directory as the jar file");
        alert.showAndWait();      
    }
    public static void createAlert(JSONException exception) {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Operator data error");
        alert.setHeaderText("Operator data contain incomplete information");
        alert.setContentText("Update operator data by delete old operatorData folder and running OperatorDataLoader.exe in the same directory as the jar file");
        alert.showAndWait();
    }
}
