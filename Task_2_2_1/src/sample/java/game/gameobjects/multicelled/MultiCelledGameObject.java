package sample.java.game.gameobjects.multicelled;

import javafx.scene.layout.Region;
import sample.java.game.Cell;

import java.util.Collection;

public abstract class MultiCelledGameObject {

    private Region[][] regions;

    public MultiCelledGameObject(Region[][] regions) {
        this.regions = regions;
    }

    public boolean isColliding(Cell cell) {
        return getGameObjectCells().stream().anyMatch(objectCell -> objectCell.hasSamePosition(cell));
    }

    protected void drawObjectCells(String className) {
        for (Cell cell : getGameObjectCells()) {
            cell.draw(regions, className);
        }
    }

    protected Region[][] getRegions() {
        return regions;
    }

    protected abstract Collection<Cell> getGameObjectCells();
}
