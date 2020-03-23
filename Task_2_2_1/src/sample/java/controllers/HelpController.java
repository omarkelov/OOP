package sample.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sample.java.SnakeGame;
import sample.java.models.Control;

public class HelpController implements Controller {

    private static final String FXML_FILE_NAME = "help.fxml";

    @FXML private Button menuButton;
    @FXML private Button helpButton;

    @FXML private TableView<Control> controlsTableView;

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent ->
            SnakeGame.getInstance().getSceneManager().changeScene(new MenuController()));
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
