package ru.nsu.fit.markelov.managers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.controllers.Controller;
import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.controllers.HelpController;
import ru.nsu.fit.markelov.controllers.MenuController;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;

import java.io.IOException;

import static ru.nsu.fit.markelov.util.AlertBuilder.buildErrorAlert;

public class SceneManager {

    private static final String GAME_TITLE = "Blue Snake";
    private static final String FXML_DIRECTORY = "/ru/nsu/fit/markelov/fxml/";

    private Stage stage;
    private LevelManager levelManager;
    private Controller controller;

    public SceneManager(Stage stage, LevelManager levelManager) {
        this.stage = stage;
        this.levelManager = levelManager;
    }

    public void switchToGame(String levelName) {
        switchScene(new GameController(this, levelManager.getLevel(levelName)));
    }

    public void switchToMenu() {
        switchScene(new MenuController(this, levelManager));
    }

    public void switchToHelp() {
        switchScene(new HelpController(this));
    }

    public void dispose() {
        controller.dispose();
    }

    private void switchScene(Controller controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(
                FXML_DIRECTORY + controller.getFXMLFileName()));
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
