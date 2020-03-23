package sample.java.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
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

    private enum State { NOT_PLAYING, PLAYING, PAUSED, FINISHED }
    private State state;

    private int currentScore;
    private int goalScore;

    public GameController() {
        SnakeGame.getInstance().getEventManager().subscribe(this, APP_CLOSING, FOOD_EATEN, SNAKE_DEATH);

        worldProperties = new WorldProperties();
    }

    @FXML
    private void initialize() {
        menuButton.setOnAction(actionEvent -> onMenuButtonClick());
        helpButton.setOnAction(actionEvent -> onHelpButtonClick());
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

        popupLabel.fontProperty().bind(Bindings.createObjectBinding(
            () -> Font.font((w > h ? playingField.getHeight() : playingField.getWidth()) / 6),
            w > h ? playingField.heightProperty() : playingField.widthProperty()));

        initGame();
    }

    private void initGame() {
        System.out.println("initGame");

        popup.getStyleClass().add(INVISIBLE_CLASS_NAME);

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
                    region.getStyleClass().add(DARK_BACKGROUND_CLASS_NAME);
                }

                playingField.add(region, j, i);
                regions[i][j] = region;
            }
        }

        state = State.NOT_PLAYING;

        world = new World(regions, worldProperties);
    }

    private class NavigationKeyEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {
                case M: case ESCAPE:
                    onMenuButtonClick();
                    break;
                case H:
                    onHelpButtonClick();
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
            if (state == State.PAUSED) {
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

            if (state == State.NOT_PLAYING && expectedKey) {
                startGame();
            }
        }
    }

    private void onMenuButtonClick() {
        System.out.println("onMenuButtonClick");

        if (getUserConfirmationIfNeeded()) {
            System.out.println("getUserConfirmation() == true");

            SnakeGame.getInstance().getSceneManager().changeScene(new MenuController());
        }
    }

    private void onHelpButtonClick() {
        System.out.println("onHelpButtonClick");

        if (getUserConfirmationIfNeeded()) {
            System.out.println("getUserConfirmation() == true");

            SnakeGame.getInstance().getSceneManager().changeScene(new HelpController());
        }
    }

    private void onRestartButtonClick() {
        System.out.println("onRestartButtonClick");

        if (state == State.NOT_PLAYING) {
            return;
        }

        System.out.println("restartGame");

        playingField.getChildren().clear();
        worldUpdateExecutor.shutdownNow();

        initGame();
    }

    private void onPauseButtonClick() {
        System.out.println("onPauseButtonClick");

        if (state == State.PLAYING) {
            pauseGame();
        } else if (state == State.PAUSED) {
            unpauseGame();
        }
    }

    private boolean getUserConfirmationIfNeeded() {
        if (state == State.NOT_PLAYING || state == State.FINISHED) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, CONFIRMATION_QUESTION, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(CONFIRMATION_HEADER);

        boolean isPlaying = state == State.PLAYING;

        if (isPlaying) {
            pauseGame();
        }

        alert.showAndWait();

        if (isPlaying) {
            unpauseGame();
        }

        return alert.getResult() == ButtonType.YES;
    }

    private void startGame() {
        System.out.println("startGame");

        restartButton.setDisable(false);
        pauseButton.setDisable(false);

        activateGame();
    }

    private void finishGame(boolean isWin) {
        System.out.println("finishGame");

        pauseButton.setDisable(true);

        worldUpdateExecutor.shutdown();
        state = State.FINISHED;

        if (isWin) {
            System.out.println("Win");
        } else {
            System.out.println("Lose");
        }

        Platform.runLater(() -> popupLabel.setText(isWin ? WIN_TEXT : LOSE_TEXT));
        popup.getStyleClass().remove(INVISIBLE_CLASS_NAME);
    }

    private void pauseGame() {
        System.out.println("pauseGame");

        pauseButton.setText(PLAY_TEXT);

        worldUpdateExecutor.shutdown();
        state = State.PAUSED;
    }

    private void unpauseGame() {
        System.out.println("unpauseGame");

        pauseButton.setText(PAUSE_TEXT);

        activateGame();
    }

    private void activateGame() {
        System.out.println("activateGame");

        worldUpdateExecutor = Executors.newSingleThreadScheduledExecutor();
        worldUpdateExecutor.scheduleWithFixedDelay(() -> world.update(), 0, 150, TimeUnit.MILLISECONDS);

        state = State.PLAYING;
    }

    @Override
    public void runAfterSceneSet(Parent root) {
        System.out.println("runAfterSceneSet");

        root.setOnKeyReleased(new NavigationKeyEventHandler());
        root.setOnKeyPressed(new GameplayKeyEventHandler());
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
