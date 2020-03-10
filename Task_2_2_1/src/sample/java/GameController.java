package sample.java;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import sample.SnakeGame;

import java.util.concurrent.TimeUnit;

public class GameController {

    public static void setAsNewScene() {
        GameController gameController = new GameController();
        SnakeGame.getInstance().changeScene("game", gameController);
        gameController.init();
    }

    @FXML private GridPane outer;
    @FXML private GridPane inner;

    private int w = 32;
    private int h = 18;

    private Region[][] regions;

    Snake snake;

    @FXML
    private void initialize() {
        final NumberBinding cellSize = Bindings
            .when(Bindings.greaterThan(outer.widthProperty().divide(outer.heightProperty()), (double) w / h))
            .then(outer.heightProperty().divide(h))
            .otherwise(outer.widthProperty().divide(w));

        for (int i = 0; i < w; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.prefWidthProperty().bind(cellSize);
            inner.getColumnConstraints().add(columnConstraints);
        }

        for (int j = 0; j < h; j++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.prefHeightProperty().bind(cellSize);
            inner.getRowConstraints().add(rowConstraints);
        }

        regions = new Region[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Region region = new Region();

                if (i % 2 == j % 2) {
                    region.getStyleClass().add("dark");
                }

                inner.add(region, j, i);
                regions[i][j] = region;
            }
        }

        snake = new Snake(regions);
        inner.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W: case UP:
                    snake.moveUp();
                    break;
                case S: case DOWN:
                    snake.moveDown();
                    break;
                case D: case RIGHT:
                    snake.moveRight();
                    break;
                case A: case LEFT:
                    snake.moveLeft();
                    break;
            }
        });

        SnakeGame.getInstance().getExecutor().scheduleWithFixedDelay(() -> {
            snake.updateDirection();
            Cell newHeadCell = snake.getNewHeadCell();
            if (newHeadCell.getColumn() < 0 || newHeadCell.getColumn() >= w || newHeadCell.getRow() < 0 || newHeadCell.getRow() >= h) {
                snake.kill(regions);
                SnakeGame.getInstance().getExecutor().shutdown();
                return;
            }
            snake.removeTail(regions);
            snake.addHead(regions, newHeadCell);
        }, 500, 200, TimeUnit.MILLISECONDS);
    }

    public void init() {
        inner.requestFocus();
    }
}
