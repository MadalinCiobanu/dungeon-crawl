package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Scorpion;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemiesTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    Scorpion scorpion = new Scorpion(gameMap.getCell(0,0));
    Skeleton skeleton = new Skeleton(gameMap.getCell(1,1));
    Enemy enemy = new Enemy(gameMap.getCell(0,1));

    @Test
    void getTileNames () {

        assertEquals("scorpion", scorpion.getTileName());
        assertEquals("skeleton", skeleton.getTileName());
        assertEquals("enemy", enemy.getTileName());
    }

    @Test
    void setHealthAndGetHealth () {
        scorpion.setHealth(5);
        skeleton.setHealth(10);
        enemy.setHealth(15);

        assertEquals(5, scorpion.getHealth());
        assertEquals(10,skeleton.getHealth());
        assertEquals(15, enemy.getHealth());
    }

    @Test
    void setAttackAndGetAttack () {
        scorpion.setAttack(5);
        skeleton.setAttack(10);
        enemy.setAttack(15);

        assertEquals(5, scorpion.getAttack());
        assertEquals(10,skeleton.getAttack());
        assertEquals(15, enemy.getAttack());
    }

    @Test
    void checkMoveMethods () {
        gameMap.getCell(1,0).setType(CellType.WALL);
        skeleton.move(0 ,1);
        scorpion.move(2,2);
        enemy.move(0, 1);

        assertEquals(skeleton, gameMap.getCell(1, 2).getActor());
        assertEquals(scorpion, gameMap.getCell(2, 2).getActor());
        assertEquals(enemy, gameMap.getCell(0, 2).getActor());
    }
 }
