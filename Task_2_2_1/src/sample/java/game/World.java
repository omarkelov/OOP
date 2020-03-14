package sample.java.game;

import javafx.scene.layout.Region;
import sample.SnakeGame;
import sample.java.game.gameobjects.multicelled.Obstacle;
import sample.java.game.gameobjects.multicelled.Snake;
import sample.java.game.gameobjects.singlecelled.Food;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import static sample.java.game.gameobjects.multicelled.Snake.Direction.DOWN;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.LEFT;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.RIGHT;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.UP;
import static sample.java.observer.Events.FOOD_EATEN;
import static sample.java.observer.Events.SNAKE_DEATH;

public class World {

    private Region[][] regions;

    private int width;
    private int height;

    Snake snake;
    Obstacle obstacle;
    Food food;

    public World(Region[][] regions, WorldProperties worldProperties) {
        this.regions = regions;

        width = worldProperties.getWidth();
        height = worldProperties.getHeight();

        snake = new Snake(regions, new LinkedList<>(worldProperties.getSnakeCells()));
        obstacle = new Obstacle(regions, new LinkedList<>(worldProperties.getObstacleCells()));
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
            snake.kill();
            SnakeGame.getInstance().getEventManager().notify(SNAKE_DEATH);

            return;
        }

        boolean isFoodEaten = food.isColliding(newHeadCell);
        if (!isFoodEaten) {
            snake.removeTail();
        }

        snake.addHead(newHeadCell);

        if (isFoodEaten) {
            food = generateFood();
            SnakeGame.getInstance().getEventManager().notify(FOOD_EATEN);
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
