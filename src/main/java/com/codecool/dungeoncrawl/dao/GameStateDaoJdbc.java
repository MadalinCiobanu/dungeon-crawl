package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc (DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(GameState state) {
        PlayerModel p = state.getPlayer();
        playerDao.add(p);
        int playerId = playerDao.getIdByName(p.getPlayerName());
        System.out.println(playerId);
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "INSERT INTO game_state (current_map, saved_at, x, y) VALUES (?, ?, ?, ?)";
//            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, player.getPlayerName());
//            statement.setInt(2, player.getHp());
//            statement.setInt(3, player.getX());
//            statement.setInt(4, player.getY());
//            statement.executeUpdate();
//            ResultSet resultSet = statement.getGeneratedKeys();
//            resultSet.next();
//            player.setId(resultSet.getInt(1));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }
}
