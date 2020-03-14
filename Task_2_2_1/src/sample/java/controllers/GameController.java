package sample.java.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import sample.SnakeGame;
import sample.java.game.World;
import sample.java.game.WorldProperties;
import sample.java.observer.EventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sample.SnakeGame.APP_CLOSING;
import static sample.SnakeGame.FOOD_EATEN;
import static sample.SnakeGame.SNAKE_DEATH;

public class GameController implements Controller, EventListener {

    private static final String FXML_FILE_NAME = "game.fxml";
    private static final String PAUSE_TEXT = "⏸";
    private static final String PLAY_TEXT = "⏵";

    @FXML private Button menuButton;
    @FXML private Button restartButton;
    @FXML private Button pauseButton;

    @FXML private Label currentScoreLabel;
    @FXML private Label goalScoreLabel;

    @FXML private GridPane wrapper;
    @FXML private GridPane playingField;

    private ScheduledExecutorService worldUpdateExecutor;

    private WorldProperties worldProperties;
    private World world;

    private BooleanBinding gameStartedBinding;

    private boolean gameStarted;  // change to states?
    private boolean gamePaused;   // change to states?
    private boolean gameFinished; // change to states?

    private int currentScore;
    private int goalScore;

    public GameController() {
        SnakeGame.getInstance().getEventManager().subscribe(this, APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);

        worldProperties = new WorldProperties();
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent -> onMenuButtonClick());
        restartButton.setOnAction(actionEvent -> onRestartButtonClick());
        pauseButton.setOnAction(actionEvent -> onPauseButtonClick());

        goalScore = worldProperties.getGoal();
        goalScoreLabel.setText(goalScore + "");

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
        System.out.println("initGame");

        restartButton.setDisable(true);
        pauseButton.setDisable(true);
        pauseButton.setText(PAUSE_TEXT);

        currentScore = worldProperties.getSnakeCells().size();
        currentScoreLabel.setText(currentScore + "");

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

    private class NavigationKeyEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case M:
                    onMenuButtonClick();
                    break;
                case R:
                    onRestartButtonClick();
                    break;
                case P:
                    onPauseButtonClick();
                    break;
            }
        }
    }

    private class GameplayKeyEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (gamePaused) {
                return;
            }

            boolean expectedKey = true;

            switch (keyEvent.getCode()) {
                case W: case UP:
                    world.moveSnakeUp();
                    break;
                case S: case DOWN:
                    world.moveSnakeDown();
                    break;
                case D: case RIGHT:
                    world.moveSnakeRight();
                    break;
                case A: case LEFT:
                    world.moveSnakeLeft();
                    break;
                default:
                    expectedKey = false;
            }

            if (!gameStarted && expectedKey) {
                startGame();
            }
        }
    }

    private void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        SnakeGame.getInstance().changeScene(new MenuController());
    }

    private void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        if (!gameStarted) {
            return;
        }

        System.out.println("restartGame");

        playingField.getChildren().clear();
        worldUpdateExecutor.shutdownNow();

        initGame();
    }

    private void onPauseButtonClick() {
        System.out.println("onPauseButtonClick");

        if (!gamePaused) {
            pauseGame();
        } else {
            unpauseGame();
        }
    }

    private void startGame() {
        System.out.println("startGame");

        restartButton.setDisable(false);
        pauseButton.setDisable(false);

        activateGame();
        gameStarted = true;
    }

    private void finishGame(boolean isWin) {
        System.out.println("finishGame");

        pauseButton.setDisable(true);

        worldUpdateExecutor.shutdown();
        gameFinished = true;

        if (isWin) {
            System.out.println("win");
        } else {
            System.out.println("loss");
        }
    }

    private void pauseGame() {
        if (!gameStarted || gameFinished) {
            return;
        }

        System.out.println("pauseGame");

        pauseButton.setText(PLAY_TEXT);

        worldUpdateExecutor.shutdown();
        gamePaused = true;
    }

    private void unpauseGame() {
        if (!gameStarted || gameFinished) {
            return;
        }

        System.out.println("unpauseGame");

        pauseButton.setText(PAUSE_TEXT);

        activateGame();
        gamePaused = false;
    }

    private void activateGame() {
        System.out.println("activateGame");

        worldUpdateExecutor = Executors.newSingleThreadScheduledExecutor();
        worldUpdateExecutor.scheduleWithFixedDelay(() -> world.update(), 0, 150, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runAfterSceneSet(Scene scene) {
        System.out.println("runAfterSceneSet");

        scene.setOnKeyReleased(new NavigationKeyEventHandler());
        scene.setOnKeyPressed(new GameplayKeyEventHandler());
        playingField.requestFocus();
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        SnakeGame.getInstance().getEventManager().unsubscribe(this, APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);
        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdown();
        }
    }

    @Override
    public void onEvent(String eventType) {
        switch (eventType) {
            case SNAKE_DEATH:
                System.out.println("event: SNAKE_DEATH");
                finishGame(false);
                break;
            case FOOD_EATEN:
                System.out.println("event: FOOD_EATEN");
                if (++currentScore >= goalScore) {
                    finishGame(true);
                }
                Platform.runLater(() -> currentScoreLabel.setText(currentScore + ""));
                break;
            case APP_CLOSING:
                System.out.println("event: APP_CLOSING");
                if (worldUpdateExecutor != null) {
                    worldUpdateExecutor.shutdown();
                }
                break;
        }
    }
}
