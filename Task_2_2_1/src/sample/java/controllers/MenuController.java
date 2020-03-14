package sample.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.SnakeGame;

public class MenuController implements Controller {

    private static final String FXML_FILE_NAME = "menu.fxml";

    @FXML private Button playButton;
    @FXML private Button helpButton;

    @FXML
    private void initialize() {
        playButton.setOnAction(actionEvent ->
            SnakeGame.getInstance().getSceneManager().changeScene(new GameController()));
//        helpButton.setOnAction(actionEvent
//            -> SnakeGame.getInstance().getSceneManager().changeScene(new HelpController()));
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
