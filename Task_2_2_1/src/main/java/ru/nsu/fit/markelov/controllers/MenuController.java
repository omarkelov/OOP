package ru.nsu.fit.markelov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ru.nsu.fit.markelov.SnakeGame;
import ru.nsu.fit.markelov.managers.levelmanager.Level;

import java.util.Map;

import static ru.nsu.fit.markelov.util.AlertBuilder.buildErrorAlert;

public class MenuController implements Controller {

    private static final String FXML_FILE_NAME = "menu.fxml";

    @FXML private Button helpButton;
    @FXML private Label levelsLabel;
    @FXML private ScrollPane buttonsScrollPane;
    @FXML private VBox buttonsVBox;

    @FXML
    private void initialize() {
        helpButton.setOnAction(actionEvent
            -> SnakeGame.getInstance().getSceneManager().changeScene(new HelpController()));

        Map<String, Level> levels = SnakeGame.getInstance().getLevelManager().getLevels();

        if (levels.isEmpty()) {
            levelsLabel.setText("No levels");
            ((VBox) buttonsScrollPane.getParent()).getChildren().remove(buttonsScrollPane);

            buildErrorAlert("levels loading").showAndWait();
        }

        for (Map.Entry<String, Level> levelEntry : levels.entrySet()) {
            String levelName = levelEntry.getKey();

            Button button = new Button(levelName);

            button.setOnAction(actionEvent -> SnakeGame.getInstance()
                .getSceneManager().changeScene(new GameController(levelName)));

            buttonsVBox.getChildren().add(button);
        }
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
