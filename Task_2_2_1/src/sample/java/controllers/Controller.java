package sample.java.controllers;

import javafx.scene.Parent;

public interface Controller {
    default void runAfterSceneSet(Parent root) {}
    default void dispose() {}
    String getFXMLFileName();
}
