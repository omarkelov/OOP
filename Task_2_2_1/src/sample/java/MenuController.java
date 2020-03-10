package sample.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.SnakeGame;

public class MenuController {

    public static void setAsNewScene() {
        MenuController menuController = new MenuController();
        SnakeGame.getInstance().changeScene("menu", menuController);
    }

    @FXML private Button playButton;
    @FXML private Button helpButton;

    @FXML
    private void initialize() {
        playButton.setOnAction(actionEvent -> GameController.setAsNewScene());
//        helpButton.setOnAction(actionEvent -> SnakeGame.getInstance().changeScene("help", null));
    }
}
