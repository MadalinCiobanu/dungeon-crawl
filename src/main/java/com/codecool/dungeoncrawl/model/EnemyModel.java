package com.codecool.dungeoncrawl.model;

public class EnemyModel extends BaseModel {

    private String enemyName;
    private int hp;
    private int x;
    private int y;

    public int getLevel() {
        return level;
    }

    private int level;

    public EnemyModel(String enemyName, int hp, int x, int y, int level) {
        this.enemyName = enemyName;
        this.hp = hp;
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
