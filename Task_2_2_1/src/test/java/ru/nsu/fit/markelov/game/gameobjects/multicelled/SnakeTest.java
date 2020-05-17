package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeTest {

    private final WorldObserver worldObserver = new WorldObserver() {
        @Override
        public void onFoodEaten() {}
        @Override
        public void onSnakeDeath() {}
        @Override
        public void onCellChanged(Cell cell) {}
    };

    private final Deque<Cell> cells = new LinkedList<>();

    public SnakeTest() throws IllegalInputException {
        cells.add(new Cell(new Vector2(0, 0), worldObserver));
        cells.add(new Cell(new Vector2(0, 1), worldObserver));
        cells.add(new Cell(new Vector2(0, 2), worldObserver));
    }

    @Test
    public void testSnake() {
        try {
            new Snake(cells).changeObjectCellsType(Cell.Type.SNAKE);
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testColliding() {
        try {
            Snake snake = new Snake(cells);
            Assert.assertTrue(snake.isColliding(new Cell(new Vector2(0, 0), worldObserver)));
            Assert.assertTrue(snake.isColliding(new Cell(new Vector2(0, 1), worldObserver)));
            Assert.assertFalse(snake.isColliding(new Cell(new Vector2(1, 1), worldObserver)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullCells() {
        boolean exceptionCaught = false;

        try {
            new Snake(null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testChangeObjectCellsTypeNullType() {
        boolean exceptionCaught = false;

        try {
            new Snake(cells).changeObjectCellsType(null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
