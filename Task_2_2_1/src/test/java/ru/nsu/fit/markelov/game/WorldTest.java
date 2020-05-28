package ru.nsu.fit.markelov.game;

import javafx.scene.image.Image;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.managers.levelmanager.Level;
import ru.nsu.fit.markelov.managers.levelmanager.LevelImageParser;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        String levelName = "Level 1";
        String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
        String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

        try (InputStream imageStream = new FileInputStream(imagePathName)) {
            Image image = new Image(imageStream);
            JSONObject jsonLevel = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonPathName))));

            new World(LevelImageParser.createLevelFromImage(image, jsonLevel), worldObserver);
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
        String levelName = "Level 1";
        String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
        String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

        boolean exceptionCaught = false;

        try (InputStream imageStream = new FileInputStream(imagePathName)) {
            Image image = new Image(imageStream);
            JSONObject jsonLevel = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonPathName))));

            new World(LevelImageParser.createLevelFromImage(image, jsonLevel), null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        } catch (IOException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }
}
