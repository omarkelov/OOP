package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import static ru.nsu.fit.markelov.game.Cell.Type.DEAD_SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.EMPTY;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE_HEAD;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Snake is used for representing a snake in the World.
 *
 * @author Oleg Markelov
 */
public class Snake extends MultiCelledGameObject {

    /**
     * Direction enum is used for representing snake direction in the World.
     */
    public enum Direction {
        UNDEFINED, UP, RIGHT, DOWN, LEFT
    }

    private Direction direction;
    private final Queue<Direction> directionQueue;

    private final Deque<Cell> snakeCells;

    /**
     * Creates new Snake with specified cells.
     *
     * If snake consists of only one cell, its direction is set to UNDEFINED, otherwise – to RIGHT.
     *
     * @param snakeCells snake cells.
     * @throws IllegalInputException if 'snakeCells' parameter is null. It can also be rethrown from
     *                               Cell.
     */
    public Snake(Deque<Cell> snakeCells) throws IllegalInputException {
        this.snakeCells = requireNonNull(snakeCells);

        direction = snakeCells.size() == 1 ? Direction.UNDEFINED : Direction.RIGHT;
        directionQueue = new LinkedList<>();

        changeObjectCellsType(SNAKE);
        snakeCells.getFirst().changeType(SNAKE_HEAD);
    }

    /**
     * Returns snake's next head position.
     *
     * @return snake's next head position.
     */
    public Vector2 getNewHeadPosition() {
        Cell headCell = snakeCells.getFirst();
        switch (direction) {
            case UP:
                return new Vector2(headCell.getPosition().getX(), headCell.getPosition().getY() - 1);
            case DOWN:
                return new Vector2(headCell.getPosition().getX(), headCell.getPosition().getY() + 1);
            case RIGHT:
                return new Vector2(headCell.getPosition().getX() + 1, headCell.getPosition().getY());
            case LEFT:
                return new Vector2(headCell.getPosition().getX() - 1, headCell.getPosition().getY());
            default:
                return null;
        }
    }

    /**
     * Changes a type of all snake cells to DEAD_SNAKE.
     *
     * @throws IllegalInputException from Cell.changeType().
     */
    public void die() throws IllegalInputException {
        for (Cell cell : snakeCells) {
            cell.changeType(DEAD_SNAKE);
        }
    }

    /**
     * Changes a type of the last snake cell to EMPTY.
     *
     * @throws IllegalInputException from Cell.changeType().
     */
    public void removeTail() throws IllegalInputException {
        Cell tailCell = snakeCells.removeLast();
        tailCell.changeType(EMPTY);
    }

    /**
     * Sets specified cell as a snake head.
     *
     * @param cell cell to add.
     * @throws IllegalInputException from Cell.changeType().
     */
    public void addHead(Cell cell) throws IllegalInputException {
        if (!snakeCells.isEmpty()) {
            snakeCells.getFirst().changeType(SNAKE);
        }
        cell.changeType(SNAKE_HEAD);
        snakeCells.addFirst(cell);
    }

    /**
     * Updates snake direction taking the value from the direction queue.
     */
    public synchronized void updateDirection() {
        if (directionQueue.isEmpty()) return;

        switch (directionQueue.peek()) {
            case UP:
            case DOWN:
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    directionQueue.remove();
                    updateDirection();
                } else {
                    direction = directionQueue.remove();
                }
                break;
            case RIGHT:
            case LEFT:
                if (direction == Direction.RIGHT || direction == Direction.LEFT) {
                    directionQueue.remove();
                    updateDirection();
                } else {
                    direction = directionQueue.remove();
                }
                break;
        }
    }

    /**
     * Adds a new direction to the direction queue.
     *
     * @param direction direction to add.
     */
    public void addDirection(Direction direction) {
        directionQueue.add(direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<Cell> getGameObjectCells() {
        return snakeCells;
    }
}
