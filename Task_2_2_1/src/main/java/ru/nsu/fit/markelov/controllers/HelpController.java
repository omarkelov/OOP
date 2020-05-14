package ru.nsu.fit.markelov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.LevelManager;
import ru.nsu.fit.markelov.models.Control;

public class HelpController implements Controller {

    private static final String FXML_FILE_NAME = "help.fxml";

    @FXML private Button menuButton;
    @FXML private Button helpButton;

    @FXML private TableView<Control> controlsTableView;

    private LevelManager levelManager;
    private SceneManager sceneManager;

    public HelpController(LevelManager levelManager, SceneManager sceneManager) {
        this.levelManager = levelManager;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent ->
            sceneManager.changeScene(new MenuController(levelManager, sceneManager)));
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
