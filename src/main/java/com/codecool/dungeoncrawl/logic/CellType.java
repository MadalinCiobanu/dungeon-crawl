package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    BROKEN_WALL("brokenWall"),
    WATER("water"),
    TREE("tree"),
    GRASS("grass"),
    BRIDGE("bridge"),
    SKULL("skull"),
    HUMAN_SKULL("humanSkull"),
    ROCKS("rocks"),
    HOUSE("house");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
