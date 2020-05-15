package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.game.gameobjects.multicelled.Obstacle;
import ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake;
import ru.nsu.fit.markelov.game.gameobjects.singlecelled.Food;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.util.Vector2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.DOWN;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.LEFT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.RIGHT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.UP;

public class World {

    private WorldObserver worldObserver;

    private int width;
    private int height;

    Snake snake;
    Obstacle obstacle;
    Food food;

    public World(Level level, WorldObserver worldObserver) {
        this.worldObserver = worldObserver;

        width = level.getWidth();
        height = level.getHeight();

        Deque<Cell> snakeCells = new LinkedList<>();
        for (Vector2 position : level.getSnakeCellPositions()) {
            snakeCells.add(new Cell(position, worldObserver));
        }

        List<Cell> obstacleCells = new LinkedList<>();
        for (Vector2 position : level.getObstacleCellPositions()) {
            obstacleCells.add(new Cell(position, worldObserver));
        }

        snake = new Snake(snakeCells);
        obstacle = new Obstacle(obstacleCells);
        food = generateFood();
    }

    public void update() {
        snake.updateDirection();
        Cell newHeadCell = new Cell(snake.getNewHeadPosition(), worldObserver);
        int x = newHeadCell.getPosition().getX();
        int y = newHeadCell.getPosition().getY();
        if (x < 0 || x >= width ||
            y < 0 || y >= height ||
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
        Cell cell = new Cell(new Vector2(-1, -1), worldObserver);
        do {
            cell.getPosition().setX(ThreadLocalRandom.current().nextInt(width));
            cell.getPosition().setY(ThreadLocalRandom.current().nextInt(height));
        } while (snake.isColliding(cell) || obstacle.isColliding(cell));

        return new Food(cell);
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
