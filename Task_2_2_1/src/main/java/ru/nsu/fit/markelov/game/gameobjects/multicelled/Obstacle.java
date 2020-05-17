package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Collection;
import java.util.List;

import static ru.nsu.fit.markelov.game.Cell.Type.OBSTACLE;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Obstacle extends MultiCelledGameObject {

    List<Cell> obstacleCells;

    public Obstacle(List<Cell> obstacleCells) throws IllegalInputException {
        this.obstacleCells = requireNonNull(obstacleCells);

        changeObjectCellsType(OBSTACLE);
    }

    @Override
    protected Collection<Cell> getGameObjectCells() {
        return obstacleCells;
    }
}
