package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.controllers.MenuController;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.eventmanager.EventManager;

import static ru.nsu.fit.markelov.managers.eventmanager.Events.APP_CLOSING;
import static ru.nsu.fit.markelov.managers.eventmanager.Events.FOOD_EATEN;
import static ru.nsu.fit.markelov.managers.eventmanager.Events.SNAKE_DEATH;

public class SnakeGame extends Application {

    private static SnakeGame instance;

    private LevelManager levelManager;
    private EventManager eventManager;
    private SceneManager sceneManager;

    public SnakeGame() {
        instance = this;

        levelManager = new LevelManager();
        eventManager = new EventManager(APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);

        sceneManager.changeScene(new MenuController());
//        sceneManager.changeScene(new HelpController());
//        sceneManager.changeScene(new GameController(3));

        primaryStage.show();
    }

    @Override
    public void stop() {
        eventManager.notify(APP_CLOSING);
    }

    public static SnakeGame getInstance() {
        return instance;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
