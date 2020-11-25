package com.codecool.dungeoncrawl.dao;
import com.codecool.dungeoncrawl.model.EnemyModel;


import java.util.List;

public interface EnemyDao {
    void add(EnemyModel state, int saveId);
    void update(EnemyModel state);
    List<EnemyModel> get(int id);
    List<EnemyModel> getAll();
}
