package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;
import ru.nsu.fit.markelov.controllers.GameController;

public class ReadyState implements State {

    private GameController gameController;

    public ReadyState(GameController gameController) {
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

        gameController.switchToMenu();
    }

    @Override
    public void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        gameController.switchToHelp();
    }
}
