package ru.nsu.fit.markelov.util;

import javafx.scene.control.Alert;

public class ErrorBuilder {
    public static Alert buildErrorAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(capitalize(task) + " error");
        alert.setContentText("Sorry, something went wrong during " + task + ". Please contact the developer.");

        return alert;
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
