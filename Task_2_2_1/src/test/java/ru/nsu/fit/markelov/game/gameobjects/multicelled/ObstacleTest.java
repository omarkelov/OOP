package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.game.gameobjects.singlecelled.Food;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.ArrayList;
import java.util.List;

public class ObstacleTest {

    private final WorldObserver worldObserver = new WorldObserver() {
        @Override
        public void onFoodEaten() {}
        @Override
        public void onSnakeDeath() {}
        @Override
        public void onCellChanged(Cell cell) {}
    };

    private final List<Cell> cells = new ArrayList<>();

    public ObstacleTest() throws IllegalInputException {
        cells.add(new Cell(new Vector2(0, 0), worldObserver));
        cells.add(new Cell(new Vector2(0, 1), worldObserver));
    }

    @Test
    public void testObstacle() {
        try {
            new Obstacle(cells).changeObjectCellsType(Cell.Type.OBSTACLE);
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testColliding() {
        try {
            Obstacle obstacle = new Obstacle(cells);
            Assert.assertTrue(obstacle.isColliding(new Cell(new Vector2(0, 0), worldObserver)));
            Assert.assertTrue(obstacle.isColliding(new Cell(new Vector2(0, 1), worldObserver)));
            Assert.assertFalse(obstacle.isColliding(new Cell(new Vector2(1, 1), worldObserver)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullCells() {
        boolean exceptionCaught = false;

        try {
            new Obstacle(null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testChangeObjectCellsTypeNullType() {
        boolean exceptionCaught = false;

        try {
            new Obstacle(cells).changeObjectCellsType(null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
