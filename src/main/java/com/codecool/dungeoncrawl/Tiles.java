package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("brokenWall", new Tile(7, 15));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("sword", new Tile(1, 28));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("enemy", new Tile(27, 3));
        tileMap.put("doorDown", new Tile(3, 6));
        tileMap.put("doorUp", new Tile(2, 6));
        tileMap.put("water", new Tile(8, 4));
        tileMap.put("bridge", new Tile(6, 4));
        tileMap.put("crown", new Tile(11, 24));
        tileMap.put("tree", new Tile(0, 1));
        tileMap.put("grass", new Tile(7, 0));
        tileMap.put("house", new Tile(1, 21));
        tileMap.put("humanSkull", new Tile(0, 15));
        tileMap.put("skull", new Tile(1, 15));
        tileMap.put("rocks", new Tile(19, 1));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
