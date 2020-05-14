package ru.nsu.fit.markelov.game;

import javafx.scene.layout.Region;
import ru.nsu.fit.markelov.game.gameobjects.multicelled.Obstacle;
import ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake;
import ru.nsu.fit.markelov.game.gameobjects.singlecelled.Food;
import ru.nsu.fit.markelov.managers.levelmanager.Level;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.DOWN;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.LEFT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.RIGHT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.UP;

public class World {

    private Region[][] regions;
    private WorldObserver worldObserver;

    private int width;
    private int height;

    Snake snake;
    Obstacle obstacle;
    Food food;

    public World(Region[][] regions, Level level, WorldObserver worldObserver) {
        this.regions = regions;
        this.worldObserver = worldObserver;

        width = level.getWidth();
        height = level.getHeight();

        snake = new Snake(regions, new LinkedList<>(level.getSnakeCells()));
        obstacle = new Obstacle(regions, new LinkedList<>(level.getObstacleCells()));
        food = generateFood();
    }

    public void update() {
        snake.updateDirection();
        Cell newHeadCell = snake.getNewHeadCell();
        if (newHeadCell.getColumn() < 0 ||
            newHeadCell.getColumn() >= width ||
            newHeadCell.getRow() < 0 ||
            newHeadCell.getRow() >= height ||
            snake.isColliding(newHeadCell) ||
            obstacle.isColliding(newHeadCell)
        ) {
            snake.die();
            worldObserver.onSnakeDeath();

            return;
        }

        boolean isFoodEaten = food.isColliding(newHeadCell);
        if (!isFoodEaten) {
            snake.removeTail();
        }

        snake.addHead(newHeadCell);

        if (isFoodEaten) {
            food = generateFood();
            worldObserver.onFoodEaten();
        }
    }

    private Food generateFood() {
        Cell cell = new Cell(-1, -1);
        do {
            cell.setRow(ThreadLocalRandom.current().nextInt(height));
            cell.setColumn(ThreadLocalRandom.current().nextInt(width));
        } while (snake.isColliding(cell) || obstacle.isColliding(cell));

        return new Food(regions, cell);
    }

    public void moveSnakeUp() {
        snake.addDirection(UP);
    }

    public void moveSnakeDown() {
        snake.addDirection(DOWN);
    }

    public void moveSnakeRight() {
        snake.addDirection(RIGHT);
    }

    public void moveSnakeLeft() {
        snake.addDirection(LEFT);
    }
}
