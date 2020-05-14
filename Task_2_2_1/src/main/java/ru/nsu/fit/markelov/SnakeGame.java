package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.controllers.MenuController;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;

public class SnakeGame extends Application {

    private LevelManager levelManager;
    private SceneManager sceneManager;

    public SnakeGame() {
        levelManager = new LevelManager();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);

        sceneManager.changeScene(new MenuController(levelManager, sceneManager));

        primaryStage.show();
    }

    @Override
    public void stop() {
        sceneManager.dispose();
    }
}
