package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
//    private int attack = 2;

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
}
