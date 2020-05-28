package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LevelTest {

    private static final String DIRECTORY = "src/test/resources/ru/nsu/fit/markelov/levels/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String DESCRIPTION_EXTENSION = ".json";

    @Test
    public void testCorrectLevel() {
        String levelName = "Level 1";
        String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
        String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

        try (InputStream imageStream = new FileInputStream(imagePathName)) {
            Image image = new Image(imageStream);
            JSONObject jsonLevel = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonPathName))));

            LevelImageParser.createLevelFromImage(image, jsonLevel);
        } catch (IOException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCorruptedLevel() {
        String levelName = "Corrupted";
        String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
        String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

        boolean exceptionCaught = false;

        try (InputStream imageStream = new FileInputStream(imagePathName)) {
            Image image = new Image(imageStream);
            JSONObject jsonLevel = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonPathName))));

            LevelImageParser.createLevelFromImage(image, jsonLevel);
        } catch (IOException e) {
            exceptionCaught = true;
        } catch (IllegalInputException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testIllegalLevel() {
        String levelName = "Illegal";
        String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
        String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

        boolean exceptionCaught = false;

        try (InputStream imageStream = new FileInputStream(imagePathName)) {
            Image image = new Image(imageStream);
            JSONObject jsonLevel = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonPathName))));

            LevelImageParser.createLevelFromImage(image, jsonLevel).validate();
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
            LevelImageParser.createLevelFromImage(null, new JSONObject()).validate();
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullJson() {
        boolean exceptionCaught = false;

        try {
            LevelImageParser.createLevelFromImage(new Image(new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            }), null).validate();
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
