package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Enemy;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class GameState extends BaseModel {
    private Date savedAt;
    private String currentMap;
    private List<String> discoveredMaps = new ArrayList<>();
    private PlayerModel player;
    private List<ItemModel> items;
    private List<EnemyModel> enemies;
    private String saveName;

    public GameState(String currentMap,
                     Date savedAt,
                     String saveName,
                     PlayerModel player,
                     List<EnemyModel> enemyModels,
                     List<ItemModel> items) {
        this.currentMap = currentMap;
        this.savedAt = savedAt;
        this.player = player;
        this.saveName = saveName;
        this.enemies = enemyModels;
        this.items = items;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public List<String> getDiscoveredMaps() {
        return discoveredMaps;
    }

    public void addDiscoveredMap(String map) {
        this.discoveredMaps.add(map);
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public List<EnemyModel> getEnemies() {
        return enemies;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
