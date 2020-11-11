package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.DoorDown;
import com.codecool.dungeoncrawl.logic.items.DoorUp;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attack = 1;
    public boolean onItem = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) {
            if (nextCell.getItem() != null) {
                this.onItem = !(nextCell.getItem() instanceof DoorDown) && !(nextCell.getItem() instanceof DoorUp);
            } else {
                this.onItem = false;
            }
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.getActor() != null) {
            Actor enemy = nextCell.getActor();
            enemy.setHealth(enemy.getHealth() - this.getAttack());
            this.setHealth(this.getHealth() - enemy.getAttack());
            if (enemy.getHealth() <= 0) nextCell.setActor(null);
        }
    }

    public int getAttack() { return this.attack; }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) { this.cell = cell; }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
