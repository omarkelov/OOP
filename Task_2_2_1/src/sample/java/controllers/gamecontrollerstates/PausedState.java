package sample.java.controllers.gamecontrollerstates;

import sample.java.controllers.GameController;

public class PausedState implements State {

    private GameController gameController;

    public PausedState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        if (gameController.confirmLeaving()) {
            gameController.switchToMenu();
        }
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        if (gameController.confirmLeaving()) {
            gameController.switchToHelp();
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
