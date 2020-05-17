package ru.nsu.fit.markelov.game;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;

public class WorldTest {

    private static final String DIRECTORY = "src/test/resources/ru/nsu/fit/markelov/levels/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String DESCRIPTION_EXTENSION = ".json";

    private final WorldObserver worldObserver = new WorldObserver() {
        @Override
        public void onFoodEaten() {}
        @Override
        public void onSnakeDeath() {}
        @Override
        public void onCellChanged(Cell cell) {}
    };

    @Test
    public void testWorld() {
        try {
            String levelName = "Level 1";
            String imageFileName = DIRECTORY + levelName + IMAGE_EXTENSION;
            String jsonFileName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;
            new World(new Level(imageFileName, jsonFileName), worldObserver);
        } catch (IOException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullLevel() {
        boolean exceptionCaught = false;

        try {
            new World(null, worldObserver);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullWorldObserver() {
        boolean exceptionCaught = false;

        try {
            String levelName = "Level 1";
            String imageFileName = DIRECTORY + levelName + IMAGE_EXTENSION;
            String jsonFileName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;
            new World(new Level(imageFileName, jsonFileName), null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        } catch (IOException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }
}
