package sample.java.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;
import sample.java.controllers.Controller;
import sample.java.controllers.GameController;
import sample.java.controllers.HelpController;
import sample.java.controllers.MenuController;

public class PlayingState implements State {

    private GameController gameController;

    public PlayingState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handleGameplayInput(KeyEvent keyEvent) {
        System.out.println("handleGameplayInput");

        gameController.moveSnake(keyEvent);
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        switchScene(MenuController.class);
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        switchScene(HelpController.class);
    }

    private void switchScene(Class<? extends Controller> controllerClass) {
        gameController.pauseGame();

        if (gameController.confirmLeaving()) {
            gameController.switchScene(controllerClass);
        } else {
            gameController.unpauseGame();
        }
    }

    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }

    @Override
    public void onPauseButtonClick() {
        System.out.println("onPauseButtonClick");

        gameController.pauseGame();
    }
}
