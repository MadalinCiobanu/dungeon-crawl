package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class DoorDown extends Item{
    private boolean isLocked = true;
    public DoorDown(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "doorDown";
    }

    public void unlock() {
        isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
