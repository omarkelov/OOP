package ru.nsu.fit.markelov.managers.levelmanager;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

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
     * Returns level gotten by specified name.
     *
     * @param levelName level name.
     * @return level gotten by specified name.
     * @throws IllegalInputException if there is no level with specified name.
     */
    public Level getLevel(String levelName) throws IllegalInputException {
        return requireNonNull(levels).get(requireNonNull(levelName));
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
