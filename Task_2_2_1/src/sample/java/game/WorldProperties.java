package sample.java.game;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static sample.java.util.ErrorBuilder.buildErrorAlert;

public class WorldProperties {

    private static final String LEVELS_DIRECTORY = "src/sample/resources/levels/";
    private static final String LEVEL_IMAGE_EXTENSION = ".png";
    private static final String LEVEL_DESCRIPTION_EXTENSION = ".json";

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

    private int goal;

    Deque<Cell> snakeCells;
    List<Cell> obstacleCells;

    public WorldProperties(int level) {
        snakeCells = new LinkedList<>();
        obstacleCells = new LinkedList<>();

        try (FileInputStream iStream = new FileInputStream(LEVELS_DIRECTORY + level + LEVEL_IMAGE_EXTENSION)) {
            Image levelImage = new Image(iStream);
//            JSONObject levelJSONObject = new JSONObject(new String(Files.readAllBytes(Paths.get(LEVEL_DESCRIPTION_EXTENSION))));

            width = (int) levelImage.getWidth();
            height = (int) levelImage.getHeight();

            goal = 5;//levelJSONObject.getInteger("goal");

            PixelReader pixelReader = levelImage.getPixelReader();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color currentPixel = pixelReader.getColor(x, y);

                    if (CellType.SNAKE.isSameColor(currentPixel)) {
                        snakeCells.addFirst(new Cell(y, x));
                    } else if (CellType.OBSTACLE.isSameColor(currentPixel)) {
                        obstacleCells.add(new Cell(y, x));
                    } else if (!CellType.EMPTY.isSameColor(currentPixel)) {
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

    public int getGoal() {
        return goal;
    }

    public Deque<Cell> getSnakeCells() {
        return snakeCells;
    }

    public List<Cell> getObstacleCells() {
        return obstacleCells;
    }
}
