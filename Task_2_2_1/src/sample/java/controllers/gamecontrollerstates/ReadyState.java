package sample.java.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;
import sample.java.controllers.GameController;
import sample.java.controllers.HelpController;
import sample.java.controllers.MenuController;

public class ReadyState implements State {

    private GameController gameController;

    public ReadyState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handleGameplayInput(KeyEvent keyEvent) {
        System.out.println("handleGameplayInput");

        boolean snakeMoved = gameController.moveSnake(keyEvent);

        if (snakeMoved) {
            gameController.startGame();
        }
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        gameController.switchScene(MenuController.class);
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.switchScene(HelpController.class);
    }
}
