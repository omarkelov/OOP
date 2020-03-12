package sample.java.gameobjects;

import javafx.scene.layout.Region;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Snake {

    private static final String SNAKE_CLASS_NAME = "snake";
    private static final String HEAD_CLASS_NAME = "head";
    private static final String DEAD_SNAKE_CLASS_NAME = "dead-snake";

    private enum Direction {
        UP, RIGHT, DOWN, LEFT;
    }

    private Direction direction;
    private final Queue<Direction> directionQueue;

    private Deque<Cell> snakeCells;

    public Snake(Region[][] regions) {
        direction = Direction.RIGHT;
        directionQueue = new LinkedList<>();

        snakeCells = new LinkedList<>();
        snakeCells.addFirst(new Cell(0, 0));
        snakeCells.addFirst(new Cell(0, 1));
        snakeCells.addFirst(new Cell(0, 2));

        for (Cell cell : snakeCells) {
            cell.draw(regions, SNAKE_CLASS_NAME);
        }
    }

    public boolean isColliding(Cell cell) {
        boolean colliding = false;

        for (Cell snakeCell : snakeCells) {
            if (snakeCell.hasSamePosition(cell)) {
                colliding = true;
                break;
            }
        }

        return colliding;
    }

    public void kill(Region[][] regions) {
        for (Cell cell : snakeCells) {
            cell.draw(regions, DEAD_SNAKE_CLASS_NAME);
        }
    }

    public void removeTail(Region[][] regions) {
        Cell tailCell = snakeCells.removeLast();
        tailCell.erase(regions);
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

    public void addHead(Region[][] regions, Cell cell) {
        snakeCells.getFirst().draw(regions, SNAKE_CLASS_NAME);
        cell.draw(regions, HEAD_CLASS_NAME);
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

    public void moveUp() {
        directionQueue.add(Direction.UP);
    }

    public void moveDown() {
        directionQueue.add(Direction.DOWN);
    }

    public void moveRight() {
        directionQueue.add(Direction.RIGHT);
    }

    public void moveLeft() {
        directionQueue.add(Direction.LEFT);
    }
}
