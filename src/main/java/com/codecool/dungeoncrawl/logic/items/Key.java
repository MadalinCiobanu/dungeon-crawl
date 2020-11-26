package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {

    public Key(Cell cell) {
        super(cell);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
