package sample.java.game;

import javafx.scene.layout.Region;
import sample.SnakeGame;
import sample.java.game.gameobjects.multicelled.Obstacle;
import sample.java.game.gameobjects.multicelled.Snake;
import sample.java.game.gameobjects.singlecelled.Food;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static sample.java.game.gameobjects.multicelled.Snake.Direction.DOWN;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.LEFT;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.RIGHT;
import static sample.java.game.gameobjects.multicelled.Snake.Direction.UP;

public class World {

    private Region[][] regions;

    private int width;
    private int height;

    Snake snake;
    Obstacle obstacle;
    Food food;

    public World(Region[][] regions, WorldProperties worldProperties) {
        this.regions = regions;

        width = worldProperties.getWidth();
        height = worldProperties.getHeight();

        Deque<Cell> snakeCells = new LinkedList<>();
        snakeCells.addFirst(new Cell(0, 0));
        snakeCells.addFirst(new Cell(0, 1));
        snakeCells.addFirst(new Cell(0, 2));

        List<Cell> obstacleCells = new LinkedList<>();
        obstacleCells.add(new Cell(7, 10));
        obstacleCells.add(new Cell(7, 11));
        obstacleCells.add(new Cell(7, 12));
        obstacleCells.add(new Cell(8, 12));
        obstacleCells.add(new Cell(9, 12));
        obstacleCells.add(new Cell(7, 13));
        obstacleCells.add(new Cell(7, 14));
        obstacleCells.add(new Cell(4, 22));
        obstacleCells.add(new Cell(5, 22));
        obstacleCells.add(new Cell(6, 22));
        obstacleCells.add(new Cell(7, 22));
        obstacleCells.add(new Cell(8, 22));
        obstacleCells.add(new Cell(9, 22));

        snake = new Snake(regions, snakeCells);
        obstacle = new Obstacle(regions, obstacleCells);
        food = generateFood();
    }

    public void update() {
        snake.updateDirection();
        Cell newHeadCell = snake.getNewHeadCell();
        if (newHeadCell.getColumn() < 0 ||
            newHeadCell.getColumn() >= width ||
            newHeadCell.getRow() < 0 ||
            newHeadCell.getRow() >= height ||
            snake.isColliding(newHeadCell) ||
            obstacle.isColliding(newHeadCell)
        ) {
            snake.kill();
            //
            //
            SnakeGame.getInstance().getExecutor().shutdown();
//            gameFinished = true;
            //
            //
            return;
        }

        boolean isFoodEaten = food.isColliding(newHeadCell);
        if (!isFoodEaten) {
            snake.removeTail();
        }

        snake.addHead(newHeadCell);

        if (isFoodEaten) {
            food = generateFood();
        }
    }

    private Food generateFood() {
        Cell cell = new Cell(-1, -1);
        do {
            cell.setRow(ThreadLocalRandom.current().nextInt(height));
            cell.setColumn(ThreadLocalRandom.current().nextInt(width));
        } while (snake.isColliding(cell) || obstacle.isColliding(cell));

        return new Food(regions, cell);
    }

    public void moveSnakeUp() {
        snake.addDirection(UP);
    }

    public void moveSnakeDown() {
        snake.addDirection(DOWN);
    }

    public void moveSnakeRight() {
        snake.addDirection(RIGHT);
    }

    public void moveSnakeLeft() {
        snake.addDirection(LEFT);
    }
}
