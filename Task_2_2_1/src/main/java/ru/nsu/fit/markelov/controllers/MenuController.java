package ru.nsu.fit.markelov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * MenuController interface is used by JavaFX in javafx.fxml.FXMLLoader for showing a menu scene.
 *
 * @author Oleg Markelov
 */
public class MenuController implements Controller {

    private static final String FXML_FILE_NAME = "menu.fxml";

    @FXML private Button helpButton;
    @FXML private Label levelsLabel;
    @FXML private ScrollPane buttonsScrollPane;
    @FXML private VBox buttonsVBox;

    private final SceneManager sceneManager;
    private final Map<String, Level> levels;

    /**
     * Creates new MenuController with specified SceneManager and levels.
     *
     * @param sceneManager scene manager.
     * @param levels       map of level name into level.
     * @throws IllegalInputException if one of the input parameters is null.
     */
    public MenuController(SceneManager sceneManager,
                          Map<String, Level> levels) throws IllegalInputException {
        this.sceneManager = requireNonNull(sceneManager);
        this.levels = requireNonNull(levels);
    }

    @FXML
    private void initialize() {
        helpButton.setOnAction(actionEvent -> sceneManager.switchToHelp());

        if (levels.isEmpty()) {
            levelsLabel.setText("No levels");
            ((VBox) buttonsScrollPane.getParent()).getChildren().remove(buttonsScrollPane);
        }

        for (Map.Entry<String, Level> levelEntry : levels.entrySet()) {
            String levelName = levelEntry.getKey();

            Button button = new Button(levelName);

            button.setOnAction(actionEvent -> sceneManager.switchToGame(levelName));

            buttonsVBox.getChildren().add(button);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
