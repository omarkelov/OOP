package ru.nsu.fit.markelov.managers.levelmanager;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.json.JSONObject;
import ru.nsu.fit.markelov.game.Cell;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static ru.nsu.fit.markelov.util.ErrorBuilder.buildErrorAlert;

public class Level {

    private enum CellType {

        EMPTY(Color.WHITE),
        SNAKE(Color.BLUE),
        OBSTACLE(Color.BLACK);

        private Color color;

        CellType(Color color) {
            this.color = color;
        }

        public boolean isSameColor(Color color) {
            return this.color.equals(color);
        }
    }

    private int width;
    private int height;

    private int goalScore;
    private int delayBetweenMoves;

    private int emptyCellsCount;

    Deque<Cell> snakeCells;
    List<Cell> obstacleCells;

    public Level(String imageFileName, String jsonFileName) {
        emptyCellsCount = 0;
        snakeCells = new LinkedList<>();
        obstacleCells = new LinkedList<>();

        try (FileInputStream iStream = new FileInputStream(imageFileName)) {
            JSONObject levelJSONObject = new JSONObject(new String(Files.readAllBytes(Paths.get(jsonFileName))));

            goalScore = levelJSONObject.getInt("goalScore");
            delayBetweenMoves = levelJSONObject.getInt("delayBetweenMoves");

            Image levelImage = new Image(iStream);

            width = (int) levelImage.getWidth();
            height = (int) levelImage.getHeight();

            PixelReader pixelReader = levelImage.getPixelReader();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color currentPixel = pixelReader.getColor(x, y);

                    if (CellType.SNAKE.isSameColor(currentPixel)) {
                        snakeCells.addFirst(new Cell(y, x));
                    } else if (CellType.OBSTACLE.isSameColor(currentPixel)) {
                        obstacleCells.add(new Cell(y, x));
                    } else if (CellType.EMPTY.isSameColor(currentPixel)) {
                        emptyCellsCount++;
                    } else {
                        System.out.println("Unknown object at (" + x + "; " + y + ").");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            buildErrorAlert("level loading").showAndWait();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGoalScore() {
        return goalScore;
    }

    public int getDelayBetweenMoves() {
        return delayBetweenMoves;
    }

    public Deque<Cell> getSnakeCells() {
        return snakeCells;
    }

    public List<Cell> getObstacleCells() {
        return obstacleCells;
    }
}
