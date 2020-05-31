package ru.nsu.fit.markelov.controllers;

import javafx.scene.input.KeyEvent;

/**
 * GameControllerDelegate interface is used for delegating some tasks from GameController class.
 *
 * @author Oleg Markelov
 */
public interface GameControllerDelegate {
    /**
     * Handles KeyEvent.
     *
     * @param keyEvent JavaFX KeyEvent.
     */
    default void handleGameplayInput(KeyEvent keyEvent) {}

    /**
     * An event occurring when the menu button is clicked/pressed.
     */
    default void onMenuButtonClick() {}

    /**
     * An event occurring when the help button is clicked/pressed.
     */
    default void onHelpButtonClick() {}

    /**
     * An event occurring when the restart button is clicked/pressed.
     */
    default void onRestartButtonClick() {}

    /**
     * An event occurring when the pause button is clicked/pressed.
     */
    default void onPauseButtonClick() {}
}
