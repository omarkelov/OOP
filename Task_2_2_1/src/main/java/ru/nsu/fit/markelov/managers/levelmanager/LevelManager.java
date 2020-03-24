package ru.nsu.fit.markelov.managers.levelmanager;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class LevelManager {

    public static final String DIRECTORY = "src/main/resources/ru/nsu/fit/markelov/levels/";
    public static final String IMAGE_EXTENSION = ".png";
    public static final String DESCRIPTION_EXTENSION = ".json";

    private Map<String, Level> levels;

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
                        // just skipping this level
                        // Alert will be shown in MenuController in case no levels are loaded
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
            // Alert will be shown in MenuController
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
