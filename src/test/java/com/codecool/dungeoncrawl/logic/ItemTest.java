package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    Sword sword = new Sword(map.getCell(1,1));
    Key key = new Key(map.getCell(2,2));
    Crown crown = new Crown(map.getCell(1,2));
    DoorUp doorUp = new DoorUp(map.getCell(0,0));
    DoorDown doorDown = new DoorDown(map.getCell(2,0));

    @Test
    void getCorrectTileName () {

        assertEquals("sword", sword.getTileName());
        assertEquals("key", key.getTileName());
        assertEquals("crown", crown.getTileName());
        assertEquals("doorDown", doorDown.getTileName());
        assertEquals("doorUp", doorUp.getTileName());
    }

    @Test
    void checkItemsPosition () {

        assertEquals(1, sword.getX());
        assertEquals(1, sword.getY());
        assertEquals(2, key.getX());
        assertEquals(2, key.getY());
        assertEquals(0, doorUp.getY());
        assertEquals(2, doorDown.getX());
    }

    @Test
    void checkIfItemIsPickable () {

        assertTrue(sword.isPickable());
        assertTrue(key.isPickable());
        assertFalse(crown.isPickable());
        assertFalse(doorDown.isPickable());
        assertFalse(doorUp.isPickable());
    }

    @Test
    void getAttackOfSword () {

        assertEquals(2, sword.getAttack());
    }

    @Test
    void checkLockAndUnlockOfDoorDown () {

        assertTrue(doorDown.isLocked());
        doorDown.unlock();
        assertFalse(doorDown.isLocked());

    }

    @Test
    void pickUpItem () {
        sword.pickItem();

        assertNull(map.getCell(1,1).getItem());
    }
}
