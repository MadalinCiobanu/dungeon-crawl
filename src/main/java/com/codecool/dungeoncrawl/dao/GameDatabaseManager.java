package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDaoJdbc gameStateDaoJdbc;
    private EnemyDao enemyDao;
    private ItemDao itemDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        enemyDao = new EnemyDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
        gameStateDaoJdbc = new GameStateDaoJdbc(dataSource, playerDao, enemyDao, itemDao);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public void saveGame(GameState gs) {
        gameStateDaoJdbc.add(gs);
    }

    public GameState loadGame(int id) {
        return gameStateDaoJdbc.get(id);
    }

    public List<GameState> getAllSavedGames() {
        return gameStateDaoJdbc.getAll();
    }


    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeon";
        String user = "iuli";
        String password = "parola_sql";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
