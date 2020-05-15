package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;

import java.util.Collection;
import java.util.List;

import static ru.nsu.fit.markelov.game.Cell.Type.OBSTACLE;

public class Obstacle extends MultiCelledGameObject {

    List<Cell> obstacleCells;

    public Obstacle(List<Cell> obstacleCells) {
        this.obstacleCells = obstacleCells;

        changeObjectCellsType(OBSTACLE);
    }

    @Override
    protected Collection<Cell> getGameObjectCells() {
        return obstacleCells;
    }
}
