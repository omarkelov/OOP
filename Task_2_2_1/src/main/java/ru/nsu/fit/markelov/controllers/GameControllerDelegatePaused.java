package ru.nsu.fit.markelov.controllers;

import ru.nsu.fit.markelov.util.Closure;

/**
 * GameControllerDelegatePaused class is used for delegating some tasks from GameController class,
 * when it is in paused state.
 *
 * @author Oleg Markelov
 */
public class GameControllerDelegatePaused implements GameControllerDelegate {

    private final GameController gameController;

    /**
     * Creates new GameControllerDelegatePaused with specified game controller.
     *
     * @param gameController game controller.
     */
    public GameControllerDelegatePaused(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        switchScene(() -> gameController.getSceneManager().switchToMenu());
    }

    /**
     * {@inheritDoc}
     */
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

        gameController.unpauseGame();
    }
}
