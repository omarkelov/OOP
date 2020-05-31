package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.json.JSONException;
import org.json.JSONObject;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * LevelImageParser class is used for creating a level parsing the data from the specified image and
 * description file.
 *
 * @author Oleg Markelov
 */
public class LevelImageParser {

    private enum CellType {
        EMPTY(Color.WHITE),
        SNAKE(Color.BLUE),
        OBSTACLE(Color.BLACK);

        private final Color color;

        CellType(Color color) {
            this.color = color;
        }

        public boolean isSameColor(Color color) {
            return this.color.equals(color);
        }
    }

    /**
     * Creates level parsing the data from the specified image and description file.
     *
     * Each pixel of the image stands for a single cell. Blue stands for snake, black – obstacle,
     * white – empty space.
     *
     * @param levelImage      level image.
     * @param levelJSONObject level description file.
     * @return new level.
     * @throws IllegalInputException if one of the input parameters is null or 'levelJSONObject' is
     *                               invalid.
     */
    public static Level createLevelFromImage(Image levelImage,
                                             JSONObject levelJSONObject) throws IllegalInputException {
        requireNonNull(levelImage);
        requireNonNull(levelJSONObject);

        Level level = new Level();
        int emptyCellsCount = 0;

        try {
            level.setGoalScore(levelJSONObject.getInt("goalScore"));
            level.setDelayBetweenMoves(levelJSONObject.getInt("delayBetweenMoves"));
        } catch (JSONException e) {
            throw new IllegalInputException();
        }

        level.setWidth((int) levelImage.getWidth());
        level.setHeight((int) levelImage.getHeight());

        PixelReader pixelReader = levelImage.getPixelReader();
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                Color currentPixel = pixelReader.getColor(x, y);

                if (CellType.SNAKE.isSameColor(currentPixel)) {
                    level.addSnakeCellPosition(new Vector2(x, y));
                } else if (CellType.OBSTACLE.isSameColor(currentPixel)) {
                    level.addObstacleCellPosition(new Vector2(x, y));
                } else if (CellType.EMPTY.isSameColor(currentPixel)) {
                    emptyCellsCount++;
                } else {
                    System.out.println("Unknown object at (" + x + "; " + y + ").");
                }
            }
        }

        level.setEmptyCellsCount(emptyCellsCount);

        return level;
    }
}
