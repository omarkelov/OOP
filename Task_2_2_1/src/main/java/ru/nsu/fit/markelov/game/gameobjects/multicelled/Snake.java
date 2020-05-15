package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.Vector2;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import static ru.nsu.fit.markelov.game.Cell.Type.DEAD_SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.EMPTY;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE_HEAD;

public class Snake extends MultiCelledGameObject {

    public enum Direction {
        UNDEFINED, UP, RIGHT, DOWN, LEFT;
    }

    private int size;

    private Direction direction;
    private final Queue<Direction> directionQueue;

    private Deque<Cell> snakeCells;

    public Snake(Deque<Cell> snakeCells) {
        this.snakeCells = snakeCells;

        size = snakeCells.size();

        direction = size == 1 ? Direction.UNDEFINED : Direction.RIGHT;
        directionQueue = new LinkedList<>();

        changeObjectCellsType(SNAKE);
        snakeCells.getFirst().changeType(SNAKE_HEAD);
    }

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

    public void die() {
        for (Cell cell : snakeCells) {
            cell.changeType(DEAD_SNAKE);
        }
    }

    public void removeTail() {
        Cell tailCell = snakeCells.removeLast();
        tailCell.changeType(EMPTY);
    }

    public void addHead(Cell cell) {
        if (!snakeCells.isEmpty()) {
            snakeCells.getFirst().changeType(SNAKE);
        }
        cell.changeType(SNAKE_HEAD);
        snakeCells.addFirst(cell);
    }

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

    public void addDirection(Direction direction) {
        directionQueue.add(direction);
    }

    @Override
    protected Collection<Cell> getGameObjectCells() {
        return snakeCells;
    }
}
