package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;

public class Player extends Actor {

    private String name;
    private final int EXTRA_ATTACK = 2;
    private ArrayList<String> inventory = new ArrayList<String>();

    public Player(Cell cell) {
        super(cell);
    }

    public void addToInventory(String item) {
        if (item.equals("sword")) setAttack(getAttack() + EXTRA_ATTACK);
        inventory.add(item);
    }

    public boolean canUnlock() {
        if (inventory.contains("key")) {
            inventory.remove("key");
            return true;
        }
        return false;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public String getTileName() {
        return "player";
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
