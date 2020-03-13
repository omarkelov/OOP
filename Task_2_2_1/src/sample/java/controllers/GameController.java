package sample.java.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import sample.SnakeGame;
import sample.java.game.World;
import sample.java.game.WorldProperties;

import java.util.concurrent.TimeUnit;

public class GameController implements Controller {

    private static final String FXML_FILE_NAME = "game.fxml";

    @FXML private GridPane wrapper;
    @FXML private GridPane playingField;

//    private Region[][] regions;
    private WorldProperties worldProperties;
    private World world;

    private boolean gameStarted; // change to states?
    private boolean gamePaused; // change to states?
    private boolean gameFinished; // change to states?

    @FXML
    private void initialize() {
        worldProperties = new WorldProperties();
        int w = worldProperties.getWidth();
        int h = worldProperties.getHeight();

        final NumberBinding cellSize = Bindings
            .when(Bindings.greaterThan(wrapper.widthProperty().divide(wrapper.heightProperty()), (double) w / h))
            .then(wrapper.heightProperty().divide(h))
            .otherwise(wrapper.widthProperty().divide(w));

        for (int i = 0; i < w; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.prefWidthProperty().bind(cellSize);
            playingField.getColumnConstraints().add(columnConstraints);
        }

        for (int j = 0; j < h; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.prefHeightProperty().bind(cellSize);
            playingField.getRowConstraints().add(rowConstraints);
        }

        initGame();
    }

    private void initGame() {
        SnakeGame.getInstance().initExecutor();

        int w = worldProperties.getWidth();
        int h = worldProperties.getHeight();
        Region[][] regions = new Region[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Region region = new Region();

                if (i % 2 == j % 2) {
                    region.getStyleClass().add("dark");
                }

                playingField.add(region, j, i);
                regions[i][j] = region;
            }
        }

        gameStarted = false;
        gamePaused = false;
        gameFinished = false;

        world = new World(regions, worldProperties);
    }

    private class KeyPressedEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case R:
                    resetGame();
                    break;
                case P:
                    if (!gamePaused) {
                        pauseGame();
                    } else {
                        unpauseGame();
                    }
                    break;
            }

            if (gamePaused) {
                return;
            }

            switch (keyEvent.getCode()) {
                case W: case UP:
                    world.moveSnakeUp();
                    startGameIfNotStarted();
                    break;
                case S: case DOWN:
                    world.moveSnakeDown();
                    startGameIfNotStarted();
                    break;
                case D: case RIGHT:
                    world.moveSnakeRight();
                    startGameIfNotStarted();
                    break;
                case A: case LEFT:
                    world.moveSnakeLeft();
                    startGameIfNotStarted();
                    break;
            }
        }
    }

    private void startGameIfNotStarted() {
        if (!gameStarted) {
            activateGame();
            gameStarted = true;
        }
    }

    private void resetGame() {
        if (!gameStarted) {
            return;
        }

        playingField.getChildren().clear();
        SnakeGame.getInstance().getExecutor().shutdownNow();

        initGame();
    }

    private void pauseGame() {
        if (!gameStarted || gameFinished) {
            return;
        }

        SnakeGame.getInstance().getExecutor().shutdown();
        gamePaused = true;
    }

    private void unpauseGame() {
        if (!gameStarted || gameFinished) {
            return;
        }

        activateGame();
        gamePaused = false;
    }

    private void activateGame() {
        SnakeGame.getInstance().initExecutor();
        SnakeGame.getInstance().getExecutor().scheduleWithFixedDelay(() -> world.update(),
            0, 150, TimeUnit.MILLISECONDS);
    }

    /*private class WorldUpdateTask implements Runnable {
        @Override
        public void run() {
            world.update();
        }
    }*/

    @Override
    public void runAfterSceneSet(Scene scene) {
        scene.setOnKeyPressed(new KeyPressedEventHandler());
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
