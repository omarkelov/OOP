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
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.game.World;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.managers.SceneManager;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.nsu.fit.markelov.game.Cell.Type.EMPTY;
import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildConfirmationAlert;
import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * GameController interface is used by JavaFX in javafx.fxml.FXMLLoader for showing a game scene.
 *
 * @author Oleg Markelov
 */
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

    @FXML private GridPane wrapperGrid;
    @FXML private GridPane fieldGrid;

    @FXML private GridPane popupGrid;
    @FXML private Label popupLabel;

    private final SceneManager sceneManager;

    private ScheduledExecutorService worldUpdateExecutor;

    private final Level level;
    private World world;
    private final GameField gameField;

    private GameControllerDelegate gameControllerDelegate;

    private int currentScore;

    /**
     * Creates new GameController with specified SceneManager and a level.
     *
     * @param sceneManager scene manager.
     * @param level        level.
     * @throws IllegalInputException if one of the input parameters is null.
     */
    public GameController(SceneManager sceneManager, Level level) throws IllegalInputException {
        this.sceneManager = requireNonNull(sceneManager);
        this.level = requireNonNull(level);
        gameField = new GameField();
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
                wrapperGrid.widthProperty().divide(wrapperGrid.heightProperty()), (double) w / h))
            .then(wrapperGrid.heightProperty().divide(h))
            .otherwise(wrapperGrid.widthProperty().divide(w));

        for (int i = 0; i < w; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.prefWidthProperty().bind(cellSize);
            fieldGrid.getColumnConstraints().add(columnConstraints);
        }

        for (int j = 0; j < h; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.prefHeightProperty().bind(cellSize);
            fieldGrid.getRowConstraints().add(rowConstraints);
        }

        popupLabel.fontProperty().bind(Bindings.createObjectBinding(
            () -> Font.font((w > h ? fieldGrid.getHeight() : fieldGrid.getWidth()) / 6),
            w > h ? fieldGrid.heightProperty() : fieldGrid.widthProperty()));

        initGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runAfterSceneSet(Parent root) throws IllegalInputException {
        System.out.println("runAfterSceneSet");

        requireNonNull(root);

        root.setOnKeyReleased(this::handleNavigationInput);
        root.setOnKeyPressed(keyEvent -> gameControllerDelegate.handleGameplayInput(keyEvent));

        root.requestFocus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        System.out.println("close");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }
    }

    /**
     * Returns scene manager.
     *
     * @return scene manager.
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFoodEaten() {
        System.out.println("onFoodEaten");

        if (++currentScore >= level.getGoalScore()) {
            finishGame(true);
        }
        Platform.runLater(() -> currentScoreLabel.setText(currentScore + ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSnakeDeath() {
        System.out.println("onSnakeDeath");

        finishGame(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCellChanged(Cell cell) throws IllegalInputException {
        System.out.println("onCellChanged");

        requireNonNull(cell);

        if (cell.getType() != EMPTY) {
            gameField.draw(cell);
        } else {
            gameField.erase(cell);
        }
    }

    public void initGame() {
        System.out.println("initGame");

        if (worldUpdateExecutor != null) {
            worldUpdateExecutor.shutdownNow();
        }

        gameControllerDelegate = new GameControllerDelegateReady(this);

        popupGrid.getStyleClass().add(INVISIBLE_CLASS_NAME);

        restartButton.setDisable(true);
        pauseButton.setDisable(true);
        pauseButton.setText(PAUSE_TEXT);

        currentScore = level.getSnakeCellPositions().size();
        currentScoreLabel.setText(currentScore + "");

        try {
            gameField.init(level.getWidth(), level.getHeight(), fieldGrid);
            world = new World(level, this);
        } catch (IllegalInputException e) {
            Platform.runLater(() -> {
                buildErrorAlert().showAndWait();
                sceneManager.switchToMenu();
            });
        }
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
        worldUpdateExecutor.scheduleWithFixedDelay(() -> {
                try {
                    world.update();
                } catch (IllegalInputException e) {
                    sceneManager.switchToMenu();
                    Platform.runLater(() -> buildErrorAlert().showAndWait());
                }
            }, 0, level.getDelayBetweenMoves(), TimeUnit.MILLISECONDS);

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
        popupGrid.getStyleClass().remove(INVISIBLE_CLASS_NAME);

        gameControllerDelegate = new GameControllerDelegateFinished(this);
    }
}
