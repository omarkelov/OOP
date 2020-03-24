package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.controllers.HelpController;
import ru.nsu.fit.markelov.controllers.MenuController;
import ru.nsu.fit.markelov.util.SceneManager;
import ru.nsu.fit.markelov.util.observer.EventManager;

import static ru.nsu.fit.markelov.util.observer.Events.APP_CLOSING;
import static ru.nsu.fit.markelov.util.observer.Events.FOOD_EATEN;
import static ru.nsu.fit.markelov.util.observer.Events.SNAKE_DEATH;

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
