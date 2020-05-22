package ru.nsu.fit.markelov.controllers;

import javafx.scene.Parent;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

/**
 * Controller interface is used by JavaFX in javafx.fxml.FXMLLoader for controlling a view and a
 * model.
 *
 * @author Oleg Markelov
 */
public interface Controller {
    /**
     * Returns .fxml file name.
     *
     * @return .fxml file name.
     */
    String getFXMLFileName();

    /**
     * This method is designed to run after the parent node is loaded by javafx.fxml.FXMLLoader.
     *
     * @param root the parent node.
     * @throws IllegalInputException if 'root' parameter is null.
     */
    default void runAfterSceneSet(Parent root) throws IllegalInputException {}

    /**
     * Disposes all the controller resources.
     */
    default void dispose() {}
}
