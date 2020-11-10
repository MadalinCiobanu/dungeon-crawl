package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Enemy extends Actor {
    private int health = 3;
    private int attack = 1;

    public Enemy(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "enemy";
    }

    @Override
    public void move(int dx, int dy) {
        Cell cell = this.getCell();
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            setCell(nextCell);
        }
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
