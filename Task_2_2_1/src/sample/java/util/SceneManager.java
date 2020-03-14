package sample.java.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.java.controllers.Controller;

import java.io.IOException;

import static sample.java.util.ErrorBuilder.buildErrorAlert;

public class SceneManager {

    private static final String GAME_TITLE = "Snaky";
    private static final String FXML_DIRECTORY = "../../resources/fxml/";

    private Stage stage;
    private Controller controller;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(Controller controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(FXML_DIRECTORY + controller.getFXMLFileName()));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            if (this.controller != null) {
                this.controller.dispose();
            }
            this.controller = controller;

            if (stage.getScene() == null) {
                stage.setTitle(GAME_TITLE);
                stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
            } else {
                stage.getScene().setRoot(root);
            }

            controller.runAfterSceneSet(root);
        } catch (IOException e) {
            e.printStackTrace();

            buildErrorAlert("layout loading").showAndWait();
        }
    }
}
