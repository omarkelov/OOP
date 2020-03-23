package sample.java;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.java.controllers.GameController;
import sample.java.controllers.HelpController;
import sample.java.controllers.MenuController;
import sample.java.util.SceneManager;
import sample.java.util.observer.EventManager;

import static sample.java.util.observer.Events.APP_CLOSING;
import static sample.java.util.observer.Events.FOOD_EATEN;
import static sample.java.util.observer.Events.SNAKE_DEATH;

public class SnakeGame extends Application {

    private static SnakeGame instance;

    private SceneManager sceneManager;
    private EventManager eventManager;

    public SnakeGame() {
        instance = this;
        eventManager = new EventManager(APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);

//        sceneManager.changeScene(new MenuController());
//        sceneManager.changeScene(new HelpController());
        sceneManager.changeScene(new GameController(1));

        primaryStage.show();
    }

    @Override
    public void stop() {
        eventManager.notify(APP_CLOSING);
    }

    public static SnakeGame getInstance() {
        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
