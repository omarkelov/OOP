package ru.nsu.fit.markelov.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.game.World;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.Level;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.nsu.fit.markelov.game.Cell.Type.DEAD_SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.EMPTY;
import static ru.nsu.fit.markelov.game.Cell.Type.FOOD;
import static ru.nsu.fit.markelov.game.Cell.Type.OBSTACLE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE_HEAD;
import static ru.nsu.fit.markelov.util.AlertBuilder.buildConfirmationAlert;

public class GameController implements Controller, WorldObserver {

    private static final String FXML_FILE_NAME = "game.fxml";

    private static final String INVISIBLE_CLASS_NAME = "invisible";
    private static final String SNAKE_CLASS_NAME = "snake";
    private static final String SNAKE_HEAD_CLASS_NAME = "snake-head";
    private static final String DEAD_SNAKE_CLASS_NAME = "snake-dead";
    private static final String OBSTACLE_CLASS_NAME = "obstacle";
    private static final String FOOD_CLASS_NAME = "food";
    private static final String DARK_BACKGROUND_CLASS_NAME = "dark-background";

    private static final String PAUSE_TEXT = "\u23F8"; // ⏸
    private static final String PLAY_TEXT = "\u23F5"; // ⏵

    private static final String WIN_TEXT = "You win!";
    private static final String LOSE_TEXT = "You lose!";

    private static final String CONFIRMATION_HEADER = "Leave the game?";
    private static final String CONFIRMATION_QUESTION =
        "Game progress will be lost. Are you sure you want to continue?";

    @FXML private Button menuButton;
    @FXML private Button helpButton;
    @FXML private Button restartButton;
    @FXML private Button pauseButton;

    @FXML private Label currentScoreLabel;
    @FXML private Label goalScoreLabel;

    @FXML private GridPane wrapper;
    @FXML private GridPane playingField;

    @FXML private GridPane popup;
    @FXML private Label popupLabel;

    private SceneManager sceneManager;

    private ScheduledExecutorService worldUpdateExecutor;

    private Region[][] regions;

    private Level level;
    private World world;

    private Map<Cell.Type, String> cellTypeToCssClassMap;

    private GameControllerDelegate gameControllerDelegate;

    private int currentScore;

    public GameController(SceneManager sceneManager, Level level) {
        this.sceneManager = sceneManager;
        this.level = level;

        cellTypeToCssClassMap = new TreeMap<>();
        cellTypeToCssClassMap.put(SNAKE, SNAKE_CLASS_NAME);
        cellTypeToCssClassMap.put(SNAKE_HEAD, SNAKE_HEAD_CLASS_NAME);
        cellTypeToCssClassMap.put(DEAD_SNAKE, DEAD_SNAKE_CLASS_NAME);
        cellTypeToCssClassMap.put(OBSTACLE, OBSTACLE_CLASS_NAME);
        cellTypeToCssClassMap.put(FOOD, FOOD_CLASS_NAME);
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent -> gameControllerDelegate.onMenuButtonClick());
        helpButton.setOnAction(actionEvent -> gameControllerDelegate.onHelpButtonClick());
        restartButton.setOnAction(actionEvent -> gameControllerDelegate.onRestartButtonClick());
        pauseButton.setOnAction(actionEvent -> gameControllerDelegate.onPauseButtonClick());

        goalScoreLabel.setText(level.getGoalScore() + "");

        int w = level.getWidth();
        int h = level.getHeight();

        final NumberBinding cellSize = Bindings
            .when(Bindings.greaterThan(
                wrapper.widthProperty().divide(wrapper.heightProperty()), (double) w / h))
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

        popupLabel.fontProperty().bind(Bindings.createObjectBinding(
            () -> Font.font((w > h ? playingField.getHeight() : playingField.getWidth()) / 6),
            w > h ? playingField.heightProperty() : playingField.widthProperty()));

        initGame();
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }

    @Override
    public void runAfterSceneSet(Parent root) {
        System.out.println("runAfterSceneSet");

        root.setOnKeyReleased(this::handleNavigationInput);
        root.setOnKeyPressed(keyEvent -> gameControllerDelegate.handleGameplayInput(keyEvent));

        root.requestFocus();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void onFoodEaten() {
        System.out.println("onFoodEaten");

        if (++currentScore >= level.getGoalScore()) {
            finishGame(true);
        }
        Platform.runLater(() -> currentScoreLabel.setText(currentScore + ""));
    }

    @Override
    public void onSnakeDeath() {
        System.out.println("onSnakeDeath");

        finishGame(false);
    }

    @Override
    public void onCellChanged(Cell cell) {
        System.out.println("onCellChanged");

        System.out.println(cell.getType());
        if (cell.getType() != EMPTY) {
            draw(cell);
        } else {
            erase(cell);
        }
    }

    private void draw(Cell cell) {
        erase(cell);
        System.out.println(cellTypeToCssClassMap.get(cell.getType()));
        regions[cell.getPosition().getY()][cell.getPosition().getX()]
            .getStyleClass().add(cellTypeToCssClassMap.get(cell.getType()));
    }

    private void erase(Cell cell) {
        regions[cell.getPosition().getY()][cell.getPosition().getX()]
            .getStyleClass().removeIf(className -> !className.equals(DARK_BACKGROUND_CLASS_NAME));
    }

    public void initGame() {
        System.out.println("initGame");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }

        popup.getStyleClass().add(INVISIBLE_CLASS_NAME);

        restartButton.setDisable(true);
        pauseButton.setDisable(true);
        pauseButton.setText(PAUSE_TEXT);

        currentScore = level.getSnakeCellPositions().size();
        currentScoreLabel.setText(currentScore + "");

        playingField.getChildren().clear();

        int w = level.getWidth();
        int h = level.getHeight();

        regions = new Region[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Region region = new Region();

                if (i % 2 == j % 2) {
                    region.getStyleClass().add(DARK_BACKGROUND_CLASS_NAME);
                }

                playingField.add(region, j, i);
                regions[i][j] = region;
            }
        }

        gameControllerDelegate = new GameControllerDelegateReady(this);

        world = new World(level, this);
    }

    private void handleNavigationInput(KeyEvent keyEvent) {
        System.out.println("handleNavigationInput");

        switch (keyEvent.getCode()) {
            case M: case ESCAPE:
                gameControllerDelegate.onMenuButtonClick();
                break;
            case H:
                gameControllerDelegate.onHelpButtonClick();
                break;
            case R:
                gameControllerDelegate.onRestartButtonClick();
                break;
            case P:
                gameControllerDelegate.onPauseButtonClick();
                break;
        }
    }

    public boolean moveSnake(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W: case UP:
                System.out.println("moveSnakeUp");
                world.moveSnakeUp();
                return true;
            case S: case DOWN:
                System.out.println("moveSnakeDown");
                world.moveSnakeDown();
                return true;
            case D: case RIGHT:
                System.out.println("moveSnakeRight");
                world.moveSnakeRight();
                return true;
            case A: case LEFT:
                System.out.println("moveSnakeLeft");
                world.moveSnakeLeft();
                return true;
            default:
                return false;
        }
    }

    public boolean confirmGameLeaving() {
        Alert alert = buildConfirmationAlert(CONFIRMATION_HEADER, CONFIRMATION_QUESTION);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    public void startGame() {
        System.out.println("startGame");

        restartButton.setDisable(false);
        pauseButton.setDisable(false);

        activateGame();
    }

    public void activateGame() {
        System.out.println("activateGame");

        worldUpdateExecutor = Executors.newSingleThreadScheduledExecutor();
        worldUpdateExecutor.scheduleWithFixedDelay(() -> world.update(),
            0, level.getDelayBetweenMoves(), TimeUnit.MILLISECONDS);

        gameControllerDelegate = new GameControllerDelegatePlaying(this);
    }

    public void pauseGame() {
        System.out.println("pauseGame");

        pauseButton.setText(PLAY_TEXT);

        worldUpdateExecutor.shutdown();

        gameControllerDelegate = new GameControllerDelegatePaused(this);
    }

    public void unpauseGame() {
        System.out.println("unpauseGame");

        pauseButton.setText(PAUSE_TEXT);

        activateGame();
    }

    private void finishGame(boolean isWin) {
        System.out.println("finishGame: " + (isWin ? "Win" : "Lose"));

        pauseButton.setDisable(true);

        worldUpdateExecutor.shutdown();

        Platform.runLater(() -> popupLabel.setText(isWin ? WIN_TEXT : LOSE_TEXT));
        popup.getStyleClass().remove(INVISIBLE_CLASS_NAME);

        gameControllerDelegate = new GameControllerDelegateFinished(this);
    }
}
