package ru.nsu.fit.markelov.controllers;

import ru.nsu.fit.markelov.util.Closure;

public class GameControllerDelegatePaused implements GameControllerDelegate {

    private final GameController gameController;

    public GameControllerDelegatePaused(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        switchScene(() -> gameController.getSceneManager().switchToMenu());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        switchScene(() -> gameController.getSceneManager().switchToHelp());
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
