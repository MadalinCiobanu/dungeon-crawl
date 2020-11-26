package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyDaoJdbc implements EnemyDao {

    private DataSource dataSource;

    public EnemyDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(EnemyModel enemyModel, int saveId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO enemy (enemy_name, hp, x, y, game_state_id, level) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, enemyModel.getEnemyName());
            statement.setInt(2, enemyModel.getHp());
            statement.setInt(3, enemyModel.getX());
            statement.setInt(4, enemyModel.getY());
            statement.setInt(5, saveId);
            statement.setInt(6, enemyModel.getLevel());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            enemyModel.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EnemyModel state) {

    }

    @Override
    public List<EnemyModel> get(int gameId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, enemy_name, hp, x, y, level FROM enemy WHERE game_state_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, gameId);

            ResultSet rs = statement.executeQuery();

            List<EnemyModel> result = new ArrayList<>();

            while (rs.next()) {
                int enemyId = rs.getInt(1);
                String enemyName = rs.getString(2);
                int enemyHp = rs.getInt(3);
                int enemyX = rs.getInt(4);
                int enemyY = rs.getInt(5);
                int enemyLevel = rs.getInt(6);

                EnemyModel enemyModel = new EnemyModel(
                    enemyName,
                    enemyHp,
                    enemyX,
                    enemyY,
                    enemyLevel
                );

                enemyModel.setId(enemyId);

                result.add(enemyModel);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting Enemies");
        }
    }

    @Override
    public List<EnemyModel> getAll() {
        return null;
    }
}
