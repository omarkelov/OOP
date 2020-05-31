package ru.nsu.fit.markelov.game;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public class CellTest {

    private final WorldObserver worldObserver = new WorldObserver() {
        @Override
        public void onFoodEaten() {}
        @Override
        public void onSnakeDeath() {}
        @Override
        public void onCellChanged(Cell cell) {}
    };

    @Test
    public void testCell() {
        try {
            new Cell(new Vector2(0, 0), worldObserver);
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testSamePosition() {
        try {
            Cell cell = new Cell(new Vector2(0, 0), worldObserver);
            Assert.assertTrue(cell.hasSamePosition(new Cell(new Vector2(0, 0), worldObserver)));
            Assert.assertFalse(cell.hasSamePosition(new Cell(new Vector2(1, 0), worldObserver)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullPosition() {
        boolean exceptionCaught = false;

        try {
            new Cell(null, worldObserver);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullWorldObserver() {
        boolean exceptionCaught = false;

        try {
            new Cell(new Vector2(0, 0), null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
