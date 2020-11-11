package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;

public class Player extends Actor {

    private String name;

    ArrayList<String> inventory = new ArrayList<String>();

    public void addToInventory(String item) {
        if (item == "sword") {
            this.setAttack(this.getAttack() + 10);
        }
        inventory.add(item);
    }

    public ArrayList<String> seeInventory() {
        return inventory;
    }

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

//    public int getAttack() {
//        return attack;
//    }
//
//    public void setAttack(int attack) {
//        this.attack = attack;
//    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
