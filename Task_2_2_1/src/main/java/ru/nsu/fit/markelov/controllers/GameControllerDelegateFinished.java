package ru.nsu.fit.markelov.controllers;

public class GameControllerDelegateFinished implements GameControllerDelegate {

    private final GameController gameController;

    public GameControllerDelegateFinished(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        gameController.getSceneManager().switchToMenu();
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.getSceneManager().switchToHelp();
    }

    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }
}
