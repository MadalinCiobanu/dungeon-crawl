package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class DoorUp extends Item {
    public DoorUp(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "doorUp";
    }
}
