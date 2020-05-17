package ru.nsu.fit.markelov.managers.levelmanager;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;

public class LevelTest {

    private static final String DIRECTORY = "src/test/resources/ru/nsu/fit/markelov/levels/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String DESCRIPTION_EXTENSION = ".json";

    @Test
    public void testCorrectLevel() {
        try {
            String levelName = "Level 1";
            String imageFileName = DIRECTORY + levelName + IMAGE_EXTENSION;
            String jsonFileName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;
            new Level(imageFileName, jsonFileName);
        } catch (IOException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCorruptedLevel() {
        boolean exceptionCaught = false;

        try {
            String levelName = "Corrupted";
            String imageFileName = DIRECTORY + levelName + IMAGE_EXTENSION;
            String jsonFileName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;
            new Level(imageFileName, jsonFileName);
        } catch (IOException e) {
            exceptionCaught = true;
        } catch (IllegalInputException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testIllegalLevel() {
        boolean exceptionCaught = false;

        try {
            String levelName = "Illegal";
            String imageFileName = DIRECTORY + levelName + IMAGE_EXTENSION;
            String jsonFileName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;
            new Level(imageFileName, jsonFileName).validate();
        } catch (IOException e) {
            Assert.fail();
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullImage() {
        boolean exceptionCaught = false;

        try {
            new Level(null, "").validate();
        } catch (IOException e) {
            Assert.fail();
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullJson() {
        boolean exceptionCaught = false;

        try {
            new Level("", null).validate();
        } catch (IOException e) {
            Assert.fail();
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
