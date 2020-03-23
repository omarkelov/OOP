package sample.java.controllers.gamecontrollerstates;

import sample.java.controllers.GameController;
import sample.java.controllers.HelpController;
import sample.java.controllers.MenuController;

public class FinishedState implements State {

    private GameController gameController;

    public FinishedState(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        gameController.switchScene(MenuController.class);
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.switchScene(HelpController.class);
    }

    @Override
    public void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        gameController.initGame();
    }
}
