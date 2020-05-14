package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;
import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.util.Closure;

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

        askConfirmAndSwitchScene(() -> gameController.switchToMenu());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        askConfirmAndSwitchScene(() -> gameController.switchToHelp());
    }

    private void askConfirmAndSwitchScene(Closure switchSceneClosure) {
        gameController.pauseGame();

        if (gameController.confirmGameLeaving()) {
            switchSceneClosure.call();
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
