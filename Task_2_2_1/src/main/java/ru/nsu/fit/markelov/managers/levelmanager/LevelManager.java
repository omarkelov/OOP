package ru.nsu.fit.markelov.managers.levelmanager;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.AlertBuilder.buildErrorAlert;

public class LevelManager {

    public static final String DIRECTORY = "src/main/resources/ru/nsu/fit/markelov/levels/";
    public static final String IMAGE_EXTENSION = ".png";
    public static final String DESCRIPTION_EXTENSION = ".json";

    private final Map<String, Level> levels;

    public LevelManager() {
        levels = new TreeMap<>();

        try {
            Files
                .walk(Paths.get(DIRECTORY))
                .filter(path -> path.toString().endsWith(IMAGE_EXTENSION))
                .forEach(imagePath -> {
                    String levelName = getFileBaseName(imagePath.getFileName());

                    try {
                        levels.put(levelName, new Level(DIRECTORY + levelName + IMAGE_EXTENSION,
                            DIRECTORY + levelName + DESCRIPTION_EXTENSION).validate());
                    } catch (IOException|IllegalInputException e) {
                        e.printStackTrace();
                        buildErrorAlert("\"" + levelName + "\"-level loading").showAndWait();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
            buildErrorAlert("levels loading").showAndWait();
        }
    }

    private String getFileBaseName(Path singleFileName) {
        return singleFileName.toString().replaceFirst("[.][^.]+$", "");
    }

    public Level getLevel(String levelName) {
        return levels.get(levelName);
    }

    public Map<String, Level> getLevels() {
        return levels;
    }
}
