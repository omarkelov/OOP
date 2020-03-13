package sample.java.game.gameobjects.multicelled;

import javafx.scene.layout.Region;
import sample.java.game.Cell;

import java.util.Collection;
import java.util.List;

public class Obstacle extends MultiCelledGameObject {

    private static final String OBSTACLE_CLASS_NAME = "obstacle";

    List<Cell> obstacleCells;

    public Obstacle(Region[][] regions, List<Cell> obstacleCells) {
        super(regions);
        this.obstacleCells = obstacleCells;

        drawObjectCells(OBSTACLE_CLASS_NAME);
    }

    @Override
    protected Collection<Cell> getGameObjectCells() {
        return obstacleCells;
    }
}
