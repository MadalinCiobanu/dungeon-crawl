package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class DoorDown extends Item{
    public DoorDown(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "doorDown";
    }
}
