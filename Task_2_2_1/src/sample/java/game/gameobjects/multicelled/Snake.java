package sample.java.game.gameobjects.multicelled;

import javafx.scene.layout.Region;
import sample.java.game.Cell;
import sample.java.observer.EventManager;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Snake extends MultiCelledGameObject {

    private static final String SNAKE_CLASS_NAME = "snake";
    private static final String HEAD_CLASS_NAME = "head";
    private static final String DEAD_SNAKE_CLASS_NAME = "dead-snake";

    public enum Direction {
        UP, RIGHT, DOWN, LEFT;
    }

    private Direction direction;
    private final Queue<Direction> directionQueue;

    private Deque<Cell> snakeCells;

    public Snake(Region[][] regions, Deque<Cell> snakeCells) {
        super(regions);
        this.snakeCells = snakeCells;

        direction = Direction.RIGHT;
        directionQueue = new LinkedList<>();

        drawObjectCells(SNAKE_CLASS_NAME);
        snakeCells.getFirst().draw(getRegions(), HEAD_CLASS_NAME);
    }

    public void kill() {
        for (Cell cell : snakeCells) {
            cell.draw(getRegions(), DEAD_SNAKE_CLASS_NAME);
        }
    }

    public void removeTail() {
        Cell tailCell = snakeCells.removeLast();
        tailCell.erase(getRegions());
    }

    public Cell getNewHeadCell() {
        Cell headCell = snakeCells.getFirst();
        switch (direction) {
            case UP:
                return new Cell(headCell.getRow() - 1, headCell.getColumn());
            case DOWN:
                return new Cell(headCell.getRow() + 1, headCell.getColumn());
            case RIGHT:
                return new Cell(headCell.getRow(), headCell.getColumn() + 1);
            case LEFT:
                return new Cell(headCell.getRow(), headCell.getColumn() - 1);
            default:
                return null;
        }
    }

    public void addHead(Cell cell) {
        snakeCells.getFirst().draw(getRegions(), SNAKE_CLASS_NAME);
        cell.draw(getRegions(), HEAD_CLASS_NAME);
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
