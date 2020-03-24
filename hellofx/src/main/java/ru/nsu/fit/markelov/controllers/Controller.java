package ru.nsu.fit.markelov.controllers;

import javafx.scene.Parent;

public interface Controller {
    String getFXMLFileName();
    default void runAfterSceneSet(Parent root) {}
    default void dispose() {}
}
