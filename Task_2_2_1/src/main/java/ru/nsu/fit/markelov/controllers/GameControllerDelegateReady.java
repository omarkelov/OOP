package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;

/**
 * GameControllerDelegateReady class is used for delegating some tasks from GameController class,
 * when it is in ready state.
 *
 * @author Oleg Markelov
 */
public class GameControllerDelegateReady implements GameControllerDelegate {

    private final GameController gameController;

    /**
     * Creates new GameControllerDelegateReady with specified game controller.
     *
     * @param gameController game controller.
     */
    public GameControllerDelegateReady(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleGameplayInput(KeyEvent keyEvent) {
        System.out.println("handleGameplayInput");

        boolean snakeMoved = gameController.moveSnake(keyEvent);

        if (snakeMoved) {
            gameController.startGame();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        gameController.getSceneManager().switchToMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.getSceneManager().switchToHelp();
    }
}
