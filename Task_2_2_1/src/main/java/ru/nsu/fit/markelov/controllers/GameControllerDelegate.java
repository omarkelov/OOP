package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;

public interface GameControllerDelegate {
    default void handleGameplayInput(KeyEvent keyEvent) {}
    default void onMenuButtonClick() {}
    default void onHelpButtonClick() {}
    default void onRestartButtonClick() {}
    default void onPauseButtonClick() {}
}
