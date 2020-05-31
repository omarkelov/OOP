package ru.nsu.fit.markelov.managers.levelmanager;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * LevelManager class is used for adding and holding the levels.
 *
 * @author Oleg Markelov
 */
public class LevelManager {

    private final Map<String, Level> levels;

    /**
     * Creates new LevelManager.
     */
    public LevelManager() {
        levels = new TreeMap<>();
    }

    /**
     * Adds level to a map [levelName -> level].
     *
     * @param levelName level name.
     * @param level     level.
     * @throws IllegalInputException if one of the input parameters is null.
     */
    public void addLevel(String levelName, Level level) throws IllegalInputException {
        levels.put(requireNonNull(levelName), requireNonNull(level));
    }

    /**
     * Returns level gotten by specified name.
     *
     * @param levelName level name.
     * @return level gotten by specified name.
     * @throws IllegalInputException if there is no level with specified name.
     */
    public Level getLevel(String levelName) throws IllegalInputException {
        return levels.get(requireNonNull(levelName));
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
