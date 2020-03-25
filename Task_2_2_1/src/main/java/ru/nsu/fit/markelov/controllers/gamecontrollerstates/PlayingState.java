package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;
import ru.nsu.fit.markelov.controllers.Controller;
import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.controllers.HelpController;
import ru.nsu.fit.markelov.controllers.MenuController;

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

        askConfirmAndSwitchScene(new MenuController());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        askConfirmAndSwitchScene(new HelpController());
    }

    private void askConfirmAndSwitchScene(Controller controller) {
        gameController.pauseGame();

        if (gameController.confirmGameLeaving()) {
            gameController.switchScene(controller);
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
