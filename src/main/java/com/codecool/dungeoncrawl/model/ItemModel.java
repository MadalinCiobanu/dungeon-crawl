package com.codecool.dungeoncrawl.model;

import java.util.Collection;

public class ItemModel extends BaseModel {

    private String itemName;
    private boolean isPicked;



    private boolean isUsed;
    private int x;
    private int y;

    public ItemModel(String itemName, int x, int y, boolean isPicked, boolean isUsed) {
        this.itemName = itemName;
        this.x = x;
        this.y = y;
        this.isPicked = isPicked;
        this.isUsed = isUsed;
    }

    public ItemModel(String itemName, boolean isPicked, boolean isUsed) {
        this(itemName, -1, -1, isPicked, isUsed);
    }

    public ItemModel(String itemName, boolean isPicked) {
        this(itemName, -1, -1, true, false);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
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

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
