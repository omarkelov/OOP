package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
import org.json.JSONException;
import org.json.JSONObject;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;

/**
 * LevelManager class is used for loading and holding all the available levels.
 *
 * @author Oleg Markelov
 */
public class LevelManager {

    private static final String DIRECTORY = "/ru/nsu/fit/markelov/levels/";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String DESCRIPTION_EXTENSION = ".json";

    private final Map<String, Level> levels;

    /**
     * Creates new LevelManager loading all the available levels.
     */
    public LevelManager() {
        levels = new TreeMap<>();

        try {
            URI uri = getClass().getResource(DIRECTORY).toURI();
            Path levelsPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                levelsPath = fileSystem.getPath(DIRECTORY);
            } else {
                levelsPath = Paths.get(uri);
            }

            Files
                .walk(levelsPath)
                .filter(path -> path.toString().endsWith(IMAGE_EXTENSION))
                .forEach(imagePath -> {
                    String levelName = getFileBaseName(imagePath.getFileName());
                    String imagePathName = DIRECTORY + levelName + IMAGE_EXTENSION;
                    String jsonPathName = DIRECTORY + levelName + DESCRIPTION_EXTENSION;

                    try (InputStream imageStream = getClass().getResourceAsStream(imagePathName);
                         InputStream jsonStream = getClass().getResourceAsStream(jsonPathName)) {
                        Image image = new Image(imageStream);
                        JSONObject jsonLevel = new JSONObject(convertStreamToString(jsonStream));

                        levels.put(levelName, new Level(image, jsonLevel).validate());
                    } catch (IOException|IllegalInputException e) {
                        e.printStackTrace();
                        buildErrorAlert("\"" + levelName + "\"-level loading").showAndWait();
                    }
                });
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            buildErrorAlert("levels loading").showAndWait();
        }
    }

    private String getFileBaseName(Path singleFileName) {
        return singleFileName.toString().replaceFirst("[.][^.]+$", "");
    }

    private String convertStreamToString(InputStream iStream) {
        Scanner s = new Scanner(iStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Returns level gotten by specified name.
     *
     * @param levelName level name.
     * @return level gotten by specified name.
     * @throws IllegalInputException if there is no level with specified name.
     */
    public Level getLevel(String levelName) throws IllegalInputException {
        Level level = levels.get(levelName);

        if (level == null) {
            throw new IllegalInputException();
        }

        return level;
    }

    /**
     * Returns map of name into level.
     *
     * @return map of name into level.
     */
    public Map<String, Level> getLevels() {
        return levels;
    }
}
