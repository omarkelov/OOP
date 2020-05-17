package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;
import ru.nsu.fit.markelov.util.Closure;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public class GameControllerDelegatePlaying implements GameControllerDelegate {

    private final GameController gameController;

    public GameControllerDelegatePlaying(GameController gameController) {
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

        askConfirmAndSwitchScene(() -> gameController.getSceneManager().switchToMenu());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        askConfirmAndSwitchScene(() -> gameController.getSceneManager().switchToHelp());
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
