package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;

import javax.sql.DataSource;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao {

    private DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(EnemyModel state) {

    }

    @Override
    public void update(EnemyModel state) {

    }

    @Override
    public EnemyModel get(int id) {
        return null;
    }

    @Override
    public List<EnemyModel> getAll() {
        return null;
    }
}
