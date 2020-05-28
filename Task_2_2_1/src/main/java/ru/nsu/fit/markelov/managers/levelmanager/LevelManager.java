package ru.nsu.fit.markelov.managers.levelmanager;

import java.util.Map;
import java.util.TreeMap;

/**
 * LevelManager class is used for loading and holding all the available levels.
 *
 * @author Oleg Markelov
 */
public class LevelManager {

    private final Map<String, Level> levels;

    /**
     * Creates new LevelManager loading all the available levels.
     */
    public LevelManager() {
        levels = new TreeMap<>();
        LevelManagerFiller levelManagerFiller = new LevelManagerFiller(levels);
        levelManagerFiller.addLevelsFromDirectory(
            "/ru/nsu/fit/markelov/levels/", ".png", ".json");
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
