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

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id, save_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.setString(4, state.getSaveName());
            statement.execute();

        } catch (SQLException e) {
            System.out.println("Eroare din GameStateDao Add");
            System.out.println(e);
            System.exit(1);
        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        String currentMap;
        java.sql.Date savedAt;
        int playerId;
        String saveName;

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT current_map, saved_at, player_id, save_name FROM game_state WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(!result.next()) return null;

            currentMap = result.getString(1);
            savedAt = result.getDate(2);
            playerId = result.getInt(3);
            saveName = result.getString(4);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PlayerModel pm = playerDao.get(playerId);

        return new GameState(currentMap, savedAt, saveName, pm);
    }

    @Override
    public List<GameState> getAll() {
        try (Connection connecion = dataSource.getConnection()) {
            String sql = "SELECT id, current_map, saved_at, player_id, save_name FROM game_state";
            ResultSet rs = connecion.createStatement().executeQuery(sql);

            List<GameState> result = new ArrayList<>();

            while (rs.next()) {
                int saveId = rs.getInt(1);
                String currentMap = rs.getString(2);
                Date savedAt = rs.getDate(3);
                int playerId = rs.getInt(4);
                String saveName = rs.getString(5);

                PlayerModel pm = playerDao.get(playerId);

                GameState gameState = new GameState(
                    currentMap,
                    savedAt,
                    saveName,
                    pm);

                gameState.setId(saveId);

                result.add(gameState);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting all Saves");
        }
    }
}
