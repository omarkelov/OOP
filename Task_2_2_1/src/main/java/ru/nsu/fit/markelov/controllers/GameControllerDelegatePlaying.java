package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;
import ru.nsu.fit.markelov.util.Closure;

/**
 * GameControllerDelegatePlaying class is used for delegating some tasks from GameController class,
 * when it is in playing state.
 *
 * @author Oleg Markelov
 */
public class GameControllerDelegatePlaying implements GameControllerDelegate {

    private final GameController gameController;

    /**
     * Creates new GameControllerDelegatePlaying with specified game controller.
     *
     * @param gameController game controller.
     */
    public GameControllerDelegatePlaying(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleGameplayInput(KeyEvent keyEvent) {
        System.out.println("handleGameplayInput");

        gameController.moveSnake(keyEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        askConfirmAndSwitchScene(() -> gameController.getSceneManager().switchToMenu());
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPauseButtonClick() {
        System.out.println("onPauseButtonClick");

        gameController.pauseGame();
    }
}
