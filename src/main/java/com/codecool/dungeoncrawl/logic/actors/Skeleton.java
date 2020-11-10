package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Skeleton extends Actor {
    private static int direction = -1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void move(int dx, int dy) {

        Cell cell = this.getCell();
        Cell nextCell = cell.getNeighbor(dx * direction, dy * direction);
        if (!(nextCell.getType() == CellType.FLOOR)) {
            direction *= -1;
            nextCell = cell.getNeighbor(dx * direction, dy * direction);
        }
        if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }
}
