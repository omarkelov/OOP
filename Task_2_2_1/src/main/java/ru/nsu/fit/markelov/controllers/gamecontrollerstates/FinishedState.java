package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import ru.nsu.fit.markelov.controllers.GameController;

public class FinishedState implements State {

    private GameController gameController;

    public FinishedState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        gameController.switchToMenu();
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.switchToHelp();
    }

    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }
}
