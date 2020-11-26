package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.ItemModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {

    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel itemModel, int gameStateId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (item_name, x, y, is_picked, game_state_id, is_used) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, itemModel.getItemName());
            statement.setInt(2, itemModel.getX());
            statement.setInt(3, itemModel.getY());
            statement.setBoolean(4, itemModel.isPicked());
            statement.setInt(5, gameStateId);
            statement.setBoolean(6, itemModel.isUsed());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            itemModel.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemModel> get(int gameStateId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, item_name, x, y, is_picked, is_used FROM item WHERE game_state_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, gameStateId);
            ResultSet rs = statement.executeQuery();
            List<ItemModel> result = new ArrayList<>();

            while (rs.next()) {
                int itemId = rs.getInt(1);

                ItemModel itemModel = new ItemModel(
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getBoolean(5),
                    rs.getBoolean(6)
                );

                itemModel.setId(itemId);

                result.add(itemModel);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while getting Items");
        }
    }
}
