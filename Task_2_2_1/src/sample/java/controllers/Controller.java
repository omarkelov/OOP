package sample.java.controllers;

import javafx.scene.Scene;

public interface Controller {
    default void runAfterSceneSet(Scene scene) {}
    String getFXMLFileName();
}
