package ru.nsu.fit.markelov.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public class AlertBuilder {

    public static Alert buildErrorAlert() {
        return buildErrorAlert(null);
    }

    public static Alert buildErrorAlert(String task) {
        if (task == null || task.isEmpty()) {
            task = "game";
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(capitalize(task) + " error");
        alert.setContentText(
            "Sorry, something went wrong during " + task + ". Please contact the developer.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return alert;
    }

    public static Alert buildConfirmationAlert(String header, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);

        alert.setHeaderText(header);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return alert;
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
