package sample.java.game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class WorldProperties {

    private int width;
    private int height;

    private int goal;

    Deque<Cell> snakeCells;
    List<Cell> obstacleCells;

    public WorldProperties() {
        width = 32;
        height = 16;
        goal = 5;

        snakeCells = new LinkedList<>();
//        snakeCells.addFirst(new Cell(1, 0));
//        snakeCells.addFirst(new Cell(1, 1));
        snakeCells.addFirst(new Cell(1, 2));

        obstacleCells = new LinkedList<>();
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
