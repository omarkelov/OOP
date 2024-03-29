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
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;

import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * SceneManager class is used for switching the scenes of JavaFX stage.
 *
 * @author Oleg Markelov
 */
public class SceneManager implements AutoCloseable {

    private static final String GAME_TITLE = "Blue Snake";
    private static final String FXML_DIRECTORY = "/ru/nsu/fit/markelov/fxml/";

    private final Stage stage;
    private final LevelManager levelManager;
    private Controller controller;

    /**
     * Creates new SceneManager with specified JavaFX stage and level manager.
     *
     * @param stage        JavaFX stage.
     * @param levelManager level manager.
     * @throws IllegalInputException if one of the input parameters is null.
     */
    public SceneManager(Stage stage, LevelManager levelManager) throws IllegalInputException {
        this.stage = requireNonNull(stage);
        this.levelManager = requireNonNull(levelManager);
    }

    /**
     * Creates new GameController and switches the scene to a specified level of a game.
     *
     * @param levelName level name.
     */
    public void switchToGame(String levelName) {
        try {
            switchScene(new GameController(this, levelManager.getLevel(levelName)));
        } catch (IllegalInputException e) {
            buildErrorAlert("scene switching").showAndWait();
        }
    }

    /**
     * Creates new MenuController and switches the scene to a menu.
     */
    public void switchToMenu() {
        try {
            switchScene(new MenuController(this, levelManager.getLevels()));
        } catch (IllegalInputException e) {
            buildErrorAlert("scene switching").showAndWait();
        }
    }

    /**
     * Creates new HelpController and switches the scene to a help one.
     */
    public void switchToHelp() {
        try {
            switchScene(new HelpController(this));
        } catch (IllegalInputException e) {
            buildErrorAlert("scene switching").showAndWait();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        controller.close();
    }

    private void switchScene(Controller controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(
                FXML_DIRECTORY + controller.getFXMLFileName()));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            if (this.controller != null) {
                this.controller.close();
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
        } catch (IllegalInputException e) {
            e.printStackTrace();
            buildErrorAlert().showAndWait();
        }
    }
}
