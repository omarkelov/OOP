package sample.java.controllers;

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
import sample.java.SnakeGame;
import sample.java.controllers.gamecontrollerstates.FinishedState;
import sample.java.controllers.gamecontrollerstates.PausedState;
import sample.java.controllers.gamecontrollerstates.PlayingState;
import sample.java.controllers.gamecontrollerstates.ReadyState;
import sample.java.controllers.gamecontrollerstates.State;
import sample.java.game.World;
import sample.java.game.WorldProperties;
import sample.java.util.observer.EventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static sample.java.game.Cell.DARK_BACKGROUND_CLASS_NAME;
import static sample.java.util.observer.Events.APP_CLOSING;
import static sample.java.util.observer.Events.FOOD_EATEN;
import static sample.java.util.observer.Events.SNAKE_DEATH;

public class GameController implements Controller, EventListener {

    private static final String FXML_FILE_NAME = "game.fxml";

    private static final String PAUSE_TEXT = "⏸";
    private static final String PLAY_TEXT = "⏵";

    private static final String INVISIBLE_CLASS_NAME = "invisible";

    private static final String WIN_TEXT = "You win!";
    private static final String LOSE_TEXT = "You lose!";

    private static final String CONFIRMATION_HEADER = "Leave the game?";
    private static final String CONFIRMATION_QUESTION =
        "Game progress will be lost. Are you sure you want to continue?";

    private Parent root;

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

    private ScheduledExecutorService worldUpdateExecutor;

    private WorldProperties worldProperties;
    private World world;

    private State state;

    private int currentScore;
    private int goalScore;

    public GameController() {
        SnakeGame.getInstance().getEventManager().subscribe(this, APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);

        worldProperties = new WorldProperties();
    }

    @FXML
    private void initialize() {
        state = new ReadyState(this);

        menuButton.setOnAction(actionEvent -> state.onMenuButtonClick());
        helpButton.setOnAction(actionEvent -> state.onHelpButtonClick());
        restartButton.setOnAction(actionEvent -> state.onRestartButtonClick());
        pauseButton.setOnAction(actionEvent -> state.onPauseButtonClick());

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

        popupLabel.fontProperty().bind(Bindings.createObjectBinding(
            () -> Font.font((w > h ? playingField.getHeight() : playingField.getWidth()) / 6),
            w > h ? playingField.heightProperty() : playingField.widthProperty()));

        initGame();
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

        currentScore = worldProperties.getSnakeCells().size();
        currentScoreLabel.setText(currentScore + "");

        playingField.getChildren().clear();
        int w = worldProperties.getWidth();
        int h = worldProperties.getHeight();
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

        if (root != null) {
            changeState(new ReadyState(this));
        }

        world = new World(regions, worldProperties);
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

    public boolean confirmLeaving() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, CONFIRMATION_QUESTION, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(CONFIRMATION_HEADER);

        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }

    public void switchToMenu() {
        System.out.println("switchToMenu");

        SnakeGame.getInstance().getSceneManager().changeScene(new MenuController());
    }

    public void switchToHelp() {
        System.out.println("switchToHelp");

        SnakeGame.getInstance().getSceneManager().changeScene(new HelpController());
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
        worldUpdateExecutor.scheduleWithFixedDelay(() -> world.update(), 0, 150, TimeUnit.MILLISECONDS);

        changeState(new PlayingState(this));
    }

    public void pauseGame() {
        System.out.println("pauseGame");

        pauseButton.setText(PLAY_TEXT);

        worldUpdateExecutor.shutdown();

        changeState(new PausedState(this));
    }

    public void unpauseGame() {
        System.out.println("unpauseGame");

        pauseButton.setText(PAUSE_TEXT);

        activateGame();
    }

    private void finishGame(boolean isWin) {
        System.out.println("finishGame");

        pauseButton.setDisable(true);

        worldUpdateExecutor.shutdown();

        if (isWin) {
            System.out.println("Win");
        } else {
            System.out.println("Lose");
        }

        Platform.runLater(() -> popupLabel.setText(isWin ? WIN_TEXT : LOSE_TEXT));
        popup.getStyleClass().remove(INVISIBLE_CLASS_NAME);

        changeState(new FinishedState(this));
    }

    private void changeState(State state) {
        root.setOnKeyPressed(state::handleGameplayInput);
        this.state = state;
    }

    @Override
    public void runAfterSceneSet(Parent root) {
        System.out.println("runAfterSceneSet");

        this.root = root;

        root.setOnKeyReleased(this::handleNavigationInput);
        root.setOnKeyPressed(state::handleGameplayInput);

        root.requestFocus();
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
            worldUpdateExecutor.shutdownNow();
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
                    worldUpdateExecutor.shutdownNow();
                }
                break;
        }
    }
}
