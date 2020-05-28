package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
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

import static ru.nsu.fit.markelov.javafxutil.AlertBuilder.buildErrorAlert;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * LevelManagerFiller class is used for loading the levels from the directory and adding them to
 * specified Map.
 *
 * @author Oleg Markelov
 */
public class LevelManagerFiller {

    private Map<String, Level> levels;

    /**
     * Creates new LevelManagerFiller with specifies Map.
     *
     * @param levels a map [levelName -> level].
     */
    public LevelManagerFiller(Map<String, Level> levels) {
        try {
            this.levels = requireNonNull(levels);
        } catch (IllegalInputException e) {
            e.printStackTrace();
            buildErrorAlert("levels loading").showAndWait();
        }
    }

    /**
     * Scans the directory looking for images, parses them as levels and adds to a map.
     *
     * @param directory            directory to scan.
     * @param imageExtension       image extension.
     * @param descriptionExtension description extension.
     */
    public void addLevelsFromDirectory(String directory, String imageExtension,
                                       String descriptionExtension) {
        try {
            requireNonNull(directory);
            requireNonNull(imageExtension);
            requireNonNull(descriptionExtension);

            URI uri = getClass().getResource(directory).toURI();
            Path levelsPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                levelsPath = fileSystem.getPath(directory);
            } else {
                levelsPath = Paths.get(uri);
            }

            Files
                .walk(levelsPath)
                .filter(path -> path.toString().endsWith(imageExtension))
                .forEach(imagePath -> {
                    String levelName = getFileBaseName(imagePath.getFileName());
                    String imagePathName = directory + levelName + imageExtension;
                    String jsonPathName = directory + levelName + descriptionExtension;

                    try (InputStream imageStream = getClass().getResourceAsStream(imagePathName);
                         InputStream jsonStream = getClass().getResourceAsStream(jsonPathName)) {
                        Image image = new Image(imageStream);
                        JSONObject jsonLevel = new JSONObject(convertStreamToString(jsonStream));

                        levels.put(levelName,
                            LevelImageParser.createLevelFromImage(image, jsonLevel).validate());
                    } catch (IOException|IllegalInputException e) {
                        e.printStackTrace();
                        buildErrorAlert("\"" + levelName + "\"-level loading").showAndWait();
                    }
                });
        } catch (IllegalInputException|URISyntaxException|IOException e) {
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
}
