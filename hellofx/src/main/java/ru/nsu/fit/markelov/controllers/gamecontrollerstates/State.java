package ru.nsu.fit.markelov.controllers.gamecontrollerstates;

import javafx.scene.input.KeyEvent;

public interface State {
    default void handleGameplayInput(KeyEvent keyEvent) {}
    default void onMenuButtonClick() {}
    default void onHelpButtonClick() {}
    default void onRestartButtonClick() {}
    default void onPauseButtonClick() {}
}
