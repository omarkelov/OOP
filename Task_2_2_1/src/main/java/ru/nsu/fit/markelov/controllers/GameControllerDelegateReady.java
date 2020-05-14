package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;

public class GameControllerDelegateReady implements GameControllerDelegate {

    private GameController gameController;

    public GameControllerDelegateReady(GameController gameController) {
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

        gameController.getSceneManager().switchToMenu();
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.getSceneManager().switchToHelp();
    }
}
