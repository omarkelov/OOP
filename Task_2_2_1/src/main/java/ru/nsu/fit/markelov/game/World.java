package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.game.gameobjects.multicelled.Obstacle;
import ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake;
import ru.nsu.fit.markelov.game.gameobjects.singlecelled.Food;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.DOWN;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.LEFT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.RIGHT;
import static ru.nsu.fit.markelov.game.gameobjects.multicelled.Snake.Direction.UP;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * World class is used for representing the plain world, which has limited bounds. It also contains
 * Snake, Obstacle and Food game objects, that can interact with each other inside the 'update()'
 * method.
 *
 * @author Oleg Markelov
 */
public class World {

    private final WorldObserver worldObserver;

    private final int width;
    private final int height;

    private final Snake snake;
    private final Obstacle obstacle;
    private Food food;

    /**
     * Creates new World from specified game level.
     *
     * WorldObserver is used for firing 'onSnakeDeath()' and 'onFoodEaten()' events.
     *
     * @param level         game level.
     * @param worldObserver world observer.
     * @throws IllegalInputException if one of the input parameters is null. It can also be rethrown
     *                               from Cell or any game object class.
     */
    public World(Level level, WorldObserver worldObserver) throws IllegalInputException {
        requireNonNull(level);
        this.worldObserver = requireNonNull(worldObserver);

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

    /**
     * Updates the world: the snake moves one cell ahead in its direction. The snake dies if it
     * collides with an obstacle. The snake grows up by one cell if it collides with a food.
     *
     * @throws IllegalInputException from Cell or any game object class.
     */
    public void update() throws IllegalInputException {
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

    private Food generateFood() throws IllegalInputException {
        Cell cell = new Cell(new Vector2(-1, -1), worldObserver);
        do {
            cell.getPosition().setX(ThreadLocalRandom.current().nextInt(width));
            cell.getPosition().setY(ThreadLocalRandom.current().nextInt(height));
        } while (snake.isColliding(cell) || obstacle.isColliding(cell));

        return new Food(cell);
    }

    /**
     * Adds 'UP' direction for a snake.
     */
    public void moveSnakeUp() {
        snake.addDirection(UP);
    }

    /**
     * Adds 'DOWN' direction for a snake.
     */
    public void moveSnakeDown() {
        snake.addDirection(DOWN);
    }

    /**
     * Adds 'RIGHT' direction for a snake.
     */
    public void moveSnakeRight() {
        snake.addDirection(RIGHT);
    }

    /**
     * Adds 'LEFT' direction for a snake.
     */
    public void moveSnakeLeft() {
        snake.addDirection(LEFT);
    }
}
