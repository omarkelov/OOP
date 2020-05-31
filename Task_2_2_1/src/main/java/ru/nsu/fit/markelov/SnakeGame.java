package ru.nsu.fit.markelov;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelsDirectoryScanner;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;

import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;

/**
 * SnakeGame is a classic snake game made with JavaFX.
 *
 * @author Oleg Markelov
 */
public class SnakeGame extends Application {

    private SceneManager sceneManager;

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
            LevelManager levelManager = new LevelManager();
            for (Map.Entry<String, Level> entry : LevelsDirectoryScanner.getLevelsFromDirectory(
                "/ru/nsu/fit/markelov/levels/", ".png", ".json").entrySet()) {
                levelManager.addLevel(entry.getKey(), entry.getValue());
            }

            sceneManager = new SceneManager(primaryStage, levelManager);
            sceneManager.switchToMenu();
            primaryStage.show();
        } catch (IllegalInputException e) {
            buildErrorAlert("game launching").showAndWait();
        }
    }

    /**
     * Closes scene manager, relinquishing any underlying resources.
     *
     * Called when JavaFX stops the application.
     */
    @Override
    public void stop() {
        sceneManager.close();
    }
}
