package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;

/**
 * SnakeGame is a classic snake game made with JavaFX.
 *
 * @author Oleg Markelov
 */
public class SnakeGame extends Application {

    private final LevelManager levelManager;
    private SceneManager sceneManager;

    /**
     * Creates new SnakeGame and loads all the game levels.
     */
    public SnakeGame() {
        levelManager = new LevelManager();
    }

    /**
     * Launches JavaFX application.
     *
     * @param args command-line options.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates a new SceneManager and shows a menu window.
     *
     * Called when JavaFX starts the application.
     *
     * @param primaryStage stage created by JavaFX.
     */
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

    /**
     * Disposes all the scene manager resources.
     *
     * Called when JavaFX stops the application.
     */
    @Override
    public void stop() {
        sceneManager.dispose();
    }
}
