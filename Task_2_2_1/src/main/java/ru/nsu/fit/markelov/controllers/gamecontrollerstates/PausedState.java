package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.util.Closure;

public class PausedState implements State {

    private GameController gameController;

    public PausedState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        switchScene(() -> gameController.switchToMenu());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        switchScene(() -> gameController.switchToHelp());
    }

    private void switchScene(Closure switchSceneClosure) {
        if (gameController.confirmGameLeaving()) {
            switchSceneClosure.call();
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

        gameController.unpauseGame();
    }
}
