package ru.nsu.fit.markelov.game.gameobjects.singlecelled;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.game.WorldObserver;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public class FoodTest {

    private final WorldObserver worldObserver = new WorldObserver() {
        @Override
        public void onFoodEaten() {}
        @Override
        public void onSnakeDeath() {}
        @Override
        public void onCellChanged(Cell cell) {}
    };

    @Test
    public void testFood() {
        try {
            new Food(new Cell(new Vector2(0, 0), worldObserver));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testColliding() {
        try {
            Food food = new Food(new Cell(new Vector2(0, 0), worldObserver));
            Assert.assertTrue(food.isColliding(new Cell(new Vector2(0, 0), worldObserver)));
            Assert.assertFalse(food.isColliding(new Cell(new Vector2(1, 0), worldObserver)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }
}
