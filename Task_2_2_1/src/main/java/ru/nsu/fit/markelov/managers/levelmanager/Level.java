package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.json.JSONException;
import org.json.JSONObject;
import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Level class is used for holding level data after parsing it from an image and description file.
 *
 * @author Oleg Markelov
 */
public class Level implements Validatable<Level> {

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

    private final int width;
    private final int height;

    private final int goalScore;
    private final int delayBetweenMoves;

    private int emptyCellsCount;

    Deque<Vector2> snakeCellPositions;
    List<Vector2> obstacleCellPositions;

    /**
     * Creates Level from the specified image and description file.
     *
     * Each pixel of the image stands for a single cell. Blue stands for snake, black – obstacle,
     * white – empty space.
     *
     * @param levelImage      level image.
     * @param levelJSONObject level description file.
     * @throws IllegalInputException if one of the input parameters is null or 'levelJSONObject' is
     *                               invalid.
     */
    public Level(Image levelImage, JSONObject levelJSONObject) throws IllegalInputException {
        requireNonNull(levelImage);
        requireNonNull(levelJSONObject);

        emptyCellsCount = 0;
        snakeCellPositions = new LinkedList<>();
        obstacleCellPositions = new LinkedList<>();

        try {
            goalScore = levelJSONObject.getInt("goalScore");
            delayBetweenMoves = levelJSONObject.getInt("delayBetweenMoves");
        } catch (JSONException e) {
            throw new IllegalInputException();
        }

        width = (int) levelImage.getWidth();
        height = (int) levelImage.getHeight();

        PixelReader pixelReader = levelImage.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color currentPixel = pixelReader.getColor(x, y);

                if (CellType.SNAKE.isSameColor(currentPixel)) {
                    snakeCellPositions.addFirst(new Vector2(x, y));
                } else if (CellType.OBSTACLE.isSameColor(currentPixel)) {
                    obstacleCellPositions.add(new Vector2(x, y));
                } else if (CellType.EMPTY.isSameColor(currentPixel)) {
                    emptyCellsCount++;
                } else {
                    System.out.println("Unknown object at (" + x + "; " + y + ").");
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level validate() throws IllegalInputException {
        if (width < 2 ||
            height < 2 ||
            goalScore < 2 ||
            emptyCellsCount < 3 ||
            emptyCellsCount < goalScore ||
            delayBetweenMoves < 20 ||
            snakeCellPositions.size() != 1
        ) {
            throw new IllegalInputException();
        }

        return this;
    }

    /**
     * Returns level width.
     *
     * @return level width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns level height.
     *
     * @return level height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns goal score.
     *
     * @return goal score.
     */
    public int getGoalScore() {
        return goalScore;
    }

    /**
     * Returns delay between moves.
     *
     * @return delay between moves.
     */
    public int getDelayBetweenMoves() {
        return delayBetweenMoves;
    }

    /**
     * Returns snake cell positions.
     *
     * @return snake cell positions.
     */
    public Deque<Vector2> getSnakeCellPositions() {
        return snakeCellPositions;
    }

    /**
     * Returns obstacle cell positions.
     *
     * @return obstacle cell positions.
     */
    public List<Vector2> getObstacleCellPositions() {
        return obstacleCellPositions;
    }
}
