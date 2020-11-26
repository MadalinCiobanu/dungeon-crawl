package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(1, 1));

    @Test
    void checkPlayerGetNameSetNameAndGetTileName () {
        player.setName("TheGuy");

        assertEquals("TheGuy", player.getName());
        assertEquals("player", player.getTileName());
    }

    @Test
    void addSwordToInventoryIncreaseTheAttack () {
        player.addToInventory("sword");

        assertEquals(3, player.getAttack());
    }

    @Test
    void canUnlockMethodUseKeyWhenUsed () {
        player.addToInventory("key");

        assertTrue(player.canUnlock());
        assertEquals(player.getInventory(), new ArrayList<>());
        assertFalse(player.canUnlock());
    }
}
