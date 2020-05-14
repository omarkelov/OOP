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
import ru.nsu.fit.markelov.SnakeGame;
import ru.nsu.fit.markelov.controllers.gamecontrollerstates.FinishedState;
import ru.nsu.fit.markelov.controllers.gamecontrollerstates.PausedState;
import ru.nsu.fit.markelov.controllers.gamecontrollerstates.PlayingState;
import ru.nsu.fit.markelov.controllers.gamecontrollerstates.ReadyState;
import ru.nsu.fit.markelov.controllers.gamecontrollerstates.State;
import ru.nsu.fit.markelov.game.World;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.managers.levelmanager.Level;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.nsu.fit.markelov.game.Cell.DARK_BACKGROUND_CLASS_NAME;
import static ru.nsu.fit.markelov.util.AlertBuilder.buildConfirmationAlert;

public class GameController implements Controller, WorldObserver {

    private static final String FXML_FILE_NAME = "game.fxml";

    private static final String INVISIBLE_CLASS_NAME = "invisible";

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

    private String levelName;

    private ScheduledExecutorService worldUpdateExecutor;

    private Level level;
    private World world;

    private State state;

    private int currentScore;

    public GameController(String levelName) {
        this.levelName = levelName;
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent -> state.onMenuButtonClick());
        helpButton.setOnAction(actionEvent -> state.onHelpButtonClick());
        restartButton.setOnAction(actionEvent -> state.onRestartButtonClick());
        pauseButton.setOnAction(actionEvent -> state.onPauseButtonClick());

        level = SnakeGame.getInstance().getLevelManager().getLevel(levelName);

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
        root.setOnKeyPressed(keyEvent -> state.handleGameplayInput(keyEvent));

        root.requestFocus();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }
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

    public void initGame() {
        System.out.println("initGame");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }

        popup.getStyleClass().add(INVISIBLE_CLASS_NAME);

        restartButton.setDisable(true);
        pauseButton.setDisable(true);
        pauseButton.setText(PAUSE_TEXT);

        currentScore = level.getSnakeCells().size();
        currentScoreLabel.setText(currentScore + "");

        playingField.getChildren().clear();

        int w = level.getWidth();
        int h = level.getHeight();

        Region[][] regions = new Region[h][w];
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

        state = new ReadyState(this);

        world = new World(regions, level, this);
    }

    private void handleNavigationInput(KeyEvent keyEvent) {
        System.out.println("handleNavigationInput");

        switch (keyEvent.getCode()) {
            case M: case ESCAPE:
                state.onMenuButtonClick();
                break;
            case H:
                state.onHelpButtonClick();
                break;
            case R:
                state.onRestartButtonClick();
                break;
            case P:
                state.onPauseButtonClick();
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

    public void switchScene(Controller controller) {
        System.out.println("switchToMenu");

        SnakeGame.getInstance().getSceneManager().changeScene(controller);
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

        state = new PlayingState(this);
    }

    public void pauseGame() {
        System.out.println("pauseGame");

        pauseButton.setText(PLAY_TEXT);

        worldUpdateExecutor.shutdown();

        state = new PausedState(this);
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

        state = new FinishedState(this);
    }
}
