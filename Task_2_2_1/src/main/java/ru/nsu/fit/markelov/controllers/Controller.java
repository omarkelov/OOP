package ru.nsu.fit.markelov.controllers;

import javafx.scene.Parent;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public interface Controller {
    String getFXMLFileName();
    default void runAfterSceneSet(Parent root) throws IllegalInputException {}
    default void dispose() {}
}
