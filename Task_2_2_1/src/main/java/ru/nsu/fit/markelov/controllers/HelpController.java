package ru.nsu.fit.markelov.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.models.Control;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class HelpController implements Controller {

    private static final String FXML_FILE_NAME = "help.fxml";

    @FXML private Button menuButton;
    @FXML private Button helpButton;

    @FXML private TableView<Control> controlsTableView;

    private final SceneManager sceneManager;

    public HelpController(SceneManager sceneManager) throws IllegalInputException {
        this.sceneManager = requireNonNull(sceneManager);
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent -> sceneManager.switchToMenu());
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
