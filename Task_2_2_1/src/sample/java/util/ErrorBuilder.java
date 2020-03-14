package sample.java.util;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.scene.control.Alert;

public class ErrorBuilder {
    public static Alert buildErrorAlert(String task) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(StringUtils.capitalize(task) + " error");
        alert.setContentText("Sorry, something went wrong during " + task + ". Please contact the developer.");

        return alert;
    }
}
