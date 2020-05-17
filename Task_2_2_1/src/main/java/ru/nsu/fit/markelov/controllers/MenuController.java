package ru.nsu.fit.markelov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class MenuController implements Controller {

    private static final String FXML_FILE_NAME = "menu.fxml";

    @FXML private Button helpButton;
    @FXML private Label levelsLabel;
    @FXML private ScrollPane buttonsScrollPane;
    @FXML private VBox buttonsVBox;

    private final SceneManager sceneManager;
    private final LevelManager levelManager;

    public MenuController(SceneManager sceneManager,
                          LevelManager levelManager) throws IllegalInputException {
        this.sceneManager = requireNonNull(sceneManager);
        this.levelManager = requireNonNull(levelManager);
    }

    @FXML
    private void initialize() {
        helpButton.setOnAction(actionEvent -> sceneManager.switchToHelp());

        Map<String, Level> levels = levelManager.getLevels();

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

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
