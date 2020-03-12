package sample.java.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import sample.SnakeGame;
import sample.java.gameobjects.Cell;
import sample.java.gameobjects.Food;
import sample.java.gameobjects.Snake;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameController extends Controller {

    private static final String FXML_FILE_NAME = "game.fxml";

    @FXML private GridPane outer;
    @FXML private GridPane inner;

    private int w = 32;
    private int h = 18;

    private Region[][] regions;

    Snake snake;
    Food food;

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


        food = generateFood();

        SnakeGame.getInstance().getExecutor().scheduleWithFixedDelay(() -> {
            snake.updateDirection();
            Cell newHeadCell = snake.getNewHeadCell();
            if (newHeadCell.getColumn() < 0 || newHeadCell.getColumn() >= w || newHeadCell.getRow() < 0 || newHeadCell.getRow() >= h) {
                snake.kill(regions);
                SnakeGame.getInstance().getExecutor().shutdown();
                return;
            }

            boolean isFoodEaten = food.isColliding(newHeadCell);

            if (!isFoodEaten) {
                snake.removeTail(regions);
            }

            snake.addHead(regions, newHeadCell);

            if (isFoodEaten) {
                food = generateFood();
            }
        }, 500, 120, TimeUnit.MILLISECONDS);
    }

    private Food generateFood() {
        Cell cell = new Cell(-1, -1);
        do {
            cell.setRow(ThreadLocalRandom.current().nextInt(h))
                .setColumn(ThreadLocalRandom.current().nextInt(w));

        } while (snake.isColliding(cell));

        return new Food(regions, cell);
    }

    @Override
    public void runAfterSceneSet() {
        inner.requestFocus();
    }

    @Override
    public String getFXMLFileName() {
        return FXML_FILE_NAME;
    }
}
