package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.controllers.MenuController;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;

public class SnakeGame extends Application {

    private static SnakeGame instance;

    private LevelManager levelManager;
    private SceneManager sceneManager;

    public SnakeGame() {
        instance = this;

        levelManager = new LevelManager();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);

        sceneManager.changeScene(new MenuController());

        primaryStage.show();
    }

    @Override
    public void stop() {
        sceneManager.dispose();
    }

    public static SnakeGame getInstance() {
        return instance;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
