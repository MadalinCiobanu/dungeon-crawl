package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.DoorDown;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private String level;
    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.level = level;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Actor> getEnemies() {
        List<Actor> enemies = new ArrayList<>();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Actor cellActor = cells[i][j].getActor();
                if ((cellActor != null) && !(cellActor instanceof Player)) {
                    enemies.add(cellActor);
                }
            }
        }
        return enemies;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void removeEnemies() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!(cells[x][y].getActor() instanceof Player)) {
                    cells[x][y].setActor(null);
                }
            }
        }
    }

    public Item getItemByTileName(String tileName) {
        for (Cell[] row : cells) {
            for (Cell c : row) {
                if (c.getItem() != null) {
                    if (c.getItem().getTileName().equals(tileName)) {
                        return c.getItem();
                    }
                }
            }
        }

        return null;
    }

    public List<Item> getPickableItems() {
        List<Item> result = new ArrayList<>();

        for (Cell[] row: cells) {
            for (Cell c : row) {

                if (c.getItem() != null) {
                    if (c.getItem().isPickable()) {
                        result.add(c.getItem());
                    }
                }

            }
        }

        return result;
    }

    public boolean isPicked(Item item) {
        for (Cell[] row: cells) {
            for (Cell c : row) {

                if (c.getItem() != null) {
                    if (c.getItem().getTileName().equals(item.getTileName())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void removeItemByTileName(String tileName) {
        for (Cell[] row : cells) {
            for (Cell c : row) {
                if (c.getItem() != null) {
                    if (c.getItem().getTileName().equals(tileName)) {
                        c.setItem(null);
                    }
                }
            }
        }
    }

    public DoorDown getDoorDown() {
        for (Cell[] row : cells) {
            for (Cell c : row) {
                if (c.getItem() != null) {
                    if (c.getItem().getTileName().equals("doorDown")) {
                        return (DoorDown) c.getItem();
                    }
                }
            }
        }

        return null;
    }
}
