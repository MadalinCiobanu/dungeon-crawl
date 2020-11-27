package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertNull(cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertNull(cell.getNeighbor(0, 1));
    }

    @Test
    void checkGetTileNameMethod () {
        Cell cell = map.getCell(1, 1);

        assertEquals("floor", cell.getTileName());
    }
 }