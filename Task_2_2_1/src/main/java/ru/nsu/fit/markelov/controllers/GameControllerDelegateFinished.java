package ru.nsu.fit.markelov.controllers;

/**
 * GameControllerDelegateFinished class is used for delegating some tasks from GameController class,
 * when it is in finished state.
 *
 * @author Oleg Markelov
 */
public class GameControllerDelegateFinished implements GameControllerDelegate {

    private final GameController gameController;

    /**
     * Creates new GameControllerDelegateFinished with specified game controller.
     *
     * @param gameController game controller.
     */
    public GameControllerDelegateFinished(GameController gameController) {
        this.gameController = gameController;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }
}
