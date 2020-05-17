package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.AlertBuilder.buildErrorAlert;

public class SnakeGame extends Application {

    private final LevelManager levelManager;
    private SceneManager sceneManager;

    public SnakeGame() {
        levelManager = new LevelManager();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            sceneManager = new SceneManager(primaryStage, levelManager);
            sceneManager.switchToMenu();
            primaryStage.show();
        } catch (IllegalInputException e) {
            buildErrorAlert("game launching").showAndWait();
        }
    }

    @Override
    public void stop() {
        sceneManager.dispose();
    }
}
