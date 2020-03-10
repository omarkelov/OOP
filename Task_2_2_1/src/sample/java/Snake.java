package sample.java;

import javafx.scene.layout.Region;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Snake {

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
            cell.draw(regions, "snake");
        }
    }

    public void kill(Region[][] regions) {
        for (Cell cell : snakeCells) {
            cell.draw(regions, "dead-snake");
        }
    }

    public void removeTail(Region[][] regions) {
        Cell tailCell = snakeCells.removeLast();
        tailCell.erase(regions, "snake");
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
        cell.draw(regions, "snake");
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
