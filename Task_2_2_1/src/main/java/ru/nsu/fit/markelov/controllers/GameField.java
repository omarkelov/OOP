package ru.nsu.fit.markelov.controllers;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.game.Cell.Type.DEAD_SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.FOOD;
import static ru.nsu.fit.markelov.game.Cell.Type.OBSTACLE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE;
import static ru.nsu.fit.markelov.game.Cell.Type.SNAKE_HEAD;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class GameField {

    private static final String SNAKE_CLASS_NAME = "snake";
    private static final String SNAKE_HEAD_CLASS_NAME = "snake-head";
    private static final String DEAD_SNAKE_CLASS_NAME = "snake-dead";
    private static final String OBSTACLE_CLASS_NAME = "obstacle";
    private static final String FOOD_CLASS_NAME = "food";
    private static final String DARK_BACKGROUND_CLASS_NAME = "dark-background";

    private final Map<Cell.Type, String> cellTypeToCssClassMap;

    private Region[][] regions;

    public GameField() {
        cellTypeToCssClassMap = new TreeMap<>();
        cellTypeToCssClassMap.put(SNAKE, SNAKE_CLASS_NAME);
        cellTypeToCssClassMap.put(SNAKE_HEAD, SNAKE_HEAD_CLASS_NAME);
        cellTypeToCssClassMap.put(DEAD_SNAKE, DEAD_SNAKE_CLASS_NAME);
        cellTypeToCssClassMap.put(OBSTACLE, OBSTACLE_CLASS_NAME);
        cellTypeToCssClassMap.put(FOOD, FOOD_CLASS_NAME);
    }

    public void init(int w, int h, GridPane gridPane) throws IllegalInputException {
        requireNonNull(gridPane);
        gridPane.getChildren().clear();

        regions = new Region[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Region region = new Region();

                if (i % 2 == j % 2) {
                    region.getStyleClass().add(DARK_BACKGROUND_CLASS_NAME);
                }

                gridPane.add(region, j, i);
                regions[i][j] = region;
            }
        }
    }

    public void draw(Cell cell) throws IllegalInputException {
        validateCell(cell);

        String cssClassName = cellTypeToCssClassMap.get(cell.getType());
        requireNonNull(cssClassName);

        erase(cell);
        regions[cell.getPosition().getY()][cell.getPosition().getX()]
            .getStyleClass().add(cssClassName);
    }

    public void erase(Cell cell) throws IllegalInputException {
        validateCell(cell);

        regions[cell.getPosition().getY()][cell.getPosition().getX()]
            .getStyleClass().removeIf(className -> !className.equals(DARK_BACKGROUND_CLASS_NAME));
    }

    private void validateCell(Cell cell) throws IllegalInputException {
        requireNonNull(cell);
        requireNonNull(cell.getPosition());
        requireNonNull(cell.getType());

        int x = cell.getPosition().getX();
        int y = cell.getPosition().getY();
        if (y < 0 || y >= regions.length || x < 0 || x >= regions[y].length) {
            throw new IllegalInputException();
        }
    }
}
