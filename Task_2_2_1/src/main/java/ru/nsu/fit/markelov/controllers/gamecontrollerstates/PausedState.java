package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import ru.nsu.fit.markelov.controllers.Controller;
import ru.nsu.fit.markelov.controllers.GameController;
import ru.nsu.fit.markelov.controllers.HelpController;
import ru.nsu.fit.markelov.controllers.MenuController;

public class PausedState implements State {

    private GameController gameController;

    public PausedState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        switchScene(new MenuController());
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        switchScene(new HelpController());
    }

    private void switchScene(Controller controller) {
        if (gameController.confirmLeaving()) {
            gameController.switchScene(controller);
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
