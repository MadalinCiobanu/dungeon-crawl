package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Crown;
import com.codecool.dungeoncrawl.logic.items.DoorDown;
import com.codecool.dungeoncrawl.logic.items.DoorUp;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

//import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main extends Application {
    GameMap lvl1 = MapLoader.loadMap("/map.txt");
    GameMap lvl2 = MapLoader.loadMap("/map1.txt");

    GameMap map = lvl1;
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryItems = new Label();
    Label nameLabel = new Label();
    Label attackLabel = new Label();
    Button pickUpButton = new Button("Pick Up");
    Button saveButton = new Button("Save game");
    Button loadButton = new Button("Load game");
    private final double FONT_SIZE = 19.0;
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        getName();

        GridPane ui = new GridPane();
        ui.setPrefWidth(220);
        ui.setPadding(new Insets(10));
        ui.setVgap(10);

        pickUpButton.setOnAction(e -> {
            map.getPlayer().addToInventory(map.getPlayer().getCell().getItem().getTileName());
            map.getPlayer().getCell().getItem().pickItem();
            refresh();
            pickUpButton.setVisible(false);
        });

        // Name label
        nameLabel.setText("Player:      " + map.getPlayer().getName());
        nameLabel.setFont(new Font(FONT_SIZE));
        ui.add(nameLabel, 0, 1, 2, 1);

        // Health labels
        Label health = new Label("Health: ");
        health.setFont(new Font(FONT_SIZE));
        healthLabel.setFont(new Font(FONT_SIZE));
        ui.add(health, 0, 2);
        ui.add(healthLabel, 1, 2);

        // Attack labels
        Label att = new Label("Attack: ");
        att.setFont(new Font(FONT_SIZE));
        attackLabel.setFont(new Font(FONT_SIZE));
        ui.add(att, 0, 3);
        ui.add(attackLabel, 1, 3);

        // Inventory labels
        Label inv = new Label("Inventory: ");
        inv.setFont(new Font(FONT_SIZE));
        inventoryItems.setFont(new Font(FONT_SIZE));
        ui.add(inv, 0, 4);
        ui.add(inventoryItems, 0, 5, 2, 2);

        // Pick Up button
        pickUpButton.setStyle("-fx-font-size:20");
        pickUpButton.setMinWidth(190);
        pickUpButton.setFocusTraversable(false);
        ui.add(pickUpButton, 0, 42, 2 ,1);

        // Save button
        saveButton.setStyle("-fx-font-size:20");
        saveButton.setMinWidth(190);
        saveButton.setFocusTraversable(false);
        saveButton.setOnAction(e -> saveGameModal());
        ui.add(saveButton, 0, 40, 2 ,1);

        // load button
        loadButton.setStyle("-fx-font-size:20");
        loadButton.setMinWidth(190);
        loadButton.setFocusTraversable(false);
        loadButton.setOnAction(e -> loadGameModal());
        ui.add(loadButton, 0, 41, 2 ,1);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

    }

    private void onKeyPressed(KeyEvent keyEvent) {
        moveEnemies();

        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                refresh();

                break;
        }
    }

    private void moveEnemies() {
        List<Actor> enemies = map.getEnemies();
        for (Actor e : enemies) {
            if (e instanceof Enemy) {

                Map<String, Integer> moves = followPlayer(e, map.getPlayer());
                e.move(moves.get("dx"), moves.get("dy"));
            } else if (e instanceof Scorpion) {
                int scorpionDx = ThreadLocalRandom.current().nextInt(-1, 2);
                int scorpionDy = ThreadLocalRandom.current().nextInt(-1, 2);
                e.move(scorpionDx, scorpionDy);
            } else if (e instanceof Skeleton) {
                e.move(-1, 0);
            }
        }
    }

    private Map<String, Integer> followPlayer(Actor e, Player p) {
        Map<String, Integer> result = new HashMap<>();
        int enemyDx = 0;
        if (p.getX() < e.getX())
            enemyDx = -1;
        else if (p.getX() > e.getX())
            enemyDx = 1;

        int enemyDy = 0;
        if (p.getY() < e.getY())
            enemyDy = -1;
        else if (p.getY() > e.getY())
            enemyDy = 1;

        result.put("dx", enemyDx);
        result.put("dy", enemyDy);

        return result;
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        nameLabel.setText("Player:      " + map.getPlayer().getName());
        attackLabel.setText("" + map.getPlayer().getAttack());
        inventoryItems.setText(map.getPlayer().getInventory().toString());

        changePickUpButton();
        changeLevel();
        isGameOver(map.getPlayer().isDead());
        canvas.requestFocus();
    }

    private void changePickUpButton () {
        if (map.getPlayer().onItem) {
            pickUpButton.setVisible(true);
        } else {
            pickUpButton.setVisible(false);
            if (map.getPlayer().getCell().getItem() instanceof Crown) {
                youWin();
                map.getPlayer().getCell().setActor(null);
            }
        }
    }

    private void changeLevel() {
        Player p = map.getPlayer();

        if (p.getCell().getItem() instanceof DoorDown) {
            DoorDown door = (DoorDown) p.getCell().getItem();
            if (p.canUnlock() || !(door.isLocked())) {
                door.unlock();
                map.getPlayer().getCell().setActor(null);
                map = lvl2;
                p.setCell(map.getCell(23, 18));
                map.setPlayer(p);
            }
        } else if (map.getPlayer().getCell().getItem() instanceof DoorUp) {
            map = lvl1;
            map.getPlayer().getCell().setActor(null);
            p.setCell(map.getCell(23, 18));
            map.setPlayer(p);
        }
    }

    private void isGameOver(boolean isDead) {
        if (isDead) {
            map.setPlayer(null);
            Stage popup = new Stage();

            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("Game Over!");

            Label gameOverLabel = new Label("GAME OVER!");
            gameOverLabel.setFont(new Font(30.0));

            VBox layout = new VBox(10);
            layout.getChildren().add(gameOverLabel);
            layout.setAlignment(Pos.CENTER);

            Scene popUpScene = new Scene(layout, 250, 150);
            popup.setScene(popUpScene);
            popup.showAndWait();
        }
    }

    private void getName() {
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Name");

        Label nameLabel = new Label("Name: ");
        TextField textField = new TextField();
        textField.setPrefWidth(2);
        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            map.getPlayer().setName(textField.getText());
            popup.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, textField, submit);
        layout.setAlignment(Pos.CENTER);

        Scene popUpScene = new Scene(layout, 200, 150);
        popup.setScene(popUpScene);
        popup.showAndWait();
    }

    private void youWin() {
        map.setPlayer(null);
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("You win!");

        Label gameOverLabel = new Label("YOU WIN!");
        gameOverLabel.setFont(new Font(30.0));

        VBox layout = new VBox(10);
        layout.getChildren().add(gameOverLabel);
        layout.setAlignment(Pos.CENTER);

        Scene popUpScene = new Scene(layout, 250, 150);
        popup.setScene(popUpScene);
        popup.showAndWait();
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void saveGame(String saveName) {
        setupDbManager();
        PlayerModel pm = new PlayerModel(map.getPlayer());
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        List<EnemyModel> enemyModels = getAllEnemyModels();
        List<ItemModel> itemModels = getAllItemModels();
        GameState gs = new GameState(map.getLevel(), date, saveName, pm, enemyModels, itemModels);

        String save = new Gson().toJson(gs);
        System.out.println(save);
        dbManager.saveGame(gs);
    }

    private void loadGame(GameState gs) {
        setupDbManager();
//        GameState gs = dbManager.loadGame(saveId);
        System.out.println("Loading game");

       // Reload both levels
        lvl1 = MapLoader.loadMap("/map.txt");
        lvl2 = MapLoader.loadMap("/map1.txt");

        // Select correct level
        map = gs.getCurrentMap().equals("1")
            ? lvl1
            : lvl2;

        // Instantiate new player with saved data

        Player savedPlayer = new Player(map.getCell(gs.getPlayer().getX(), gs.getPlayer().getY()));
        savedPlayer.setName(gs.getPlayer().getPlayerName());
        savedPlayer.setHealth(gs.getPlayer().getHp());

        // Remove enemies
        lvl1.removeEnemies();
        lvl2.removeEnemies();

        // Add saved enemies
        List<EnemyModel> enemyModels = gs.getEnemies();
        for (EnemyModel em : enemyModels) {
            Cell c = em.getLevel() == 1
                ? lvl1.getCell(em.getX(), em.getY())
                : lvl2.getCell(em.getX(), em.getY());

            if (em.getEnemyName().equals("skeleton"))
            {
                Skeleton s = new Skeleton(c);
                s.setHealth(em.getHp());
            }
            else if (em.getEnemyName().equals("enemy"))
            {
                Enemy e = new Enemy(c);
                e.setHealth(em.getHp());
            }
            else if (em.getEnemyName().equals("scorpion"))
            {
                Scorpion s = new Scorpion(c);
                s.setHealth(em.getHp());
            }
        }


        // Get saved items
        List<ItemModel> itemModels = gs.getItems();
        for (ItemModel im : itemModels) {
            if (im.getItemName().equals("sword")) {
                if (im.isPicked()) {
                    lvl1.removeItemByTileName(im.getItemName());
                    savedPlayer.addToInventory("sword");
                }
            }

            if (im.getItemName().equals("key")) {
                if (im.isPicked()) {
                    System.out.println("Key was picked up");

                    if (!im.isUsed()) {
                        System.out.println("Key was not used");

                        savedPlayer.addToInventory("key");
                        System.out.println("Adding key to inventory");

                        lvl1.removeItemByTileName(im.getItemName());
                        System.out.println("Removed Key from map");


                    } else {
                        System.out.println("Key was used");

                        lvl1.removeItemByTileName(im.getItemName());
                        System.out.println("Removed Key from map");

                        lvl1.getDoorDown().unlock();
                        System.out.println("Unlocking door");
                    }
                }
            }
        }


        // Delete original player if level is 1
        if (gs.getCurrentMap().equals("1")) {
            map.getPlayer().getCell().setActor(null);
        }

        // Put saved player on the map
        map.setPlayer(savedPlayer);
        refresh();
    }

    private void saveGameModal() {
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Save");

        Label saveLabel = new Label("Save: ");
        TextField textField = new TextField();
        textField.setPrefWidth(2);
        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            saveGame(textField.getText());
            popup.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(saveLabel, textField, submit);
        layout.setAlignment(Pos.CENTER);

        Scene popUpScene = new Scene(layout, 200, 150);
        popup.setScene(popUpScene);
        popup.showAndWait();
    }

    private void loadGameModal() {
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Load");

        Label saveLabel = new Label("Load: ");

        ListView listView = new ListView();
        setupDbManager();
        List<GameState> allSaves = dbManager.getAllSavedGames();

        for (GameState save : allSaves) {
            listView.getItems().add(save.getId() + "# " + save.getSaveName() + " (" + save.getPlayer().getPlayerName() + ")");
        }

        Button loadFromFile = new Button("Select a file");
        loadFromFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.showOpenDialog(popup);
        });
        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
            String selectedRow = (String) listView.getSelectionModel().getSelectedItem();

            int saveId = Integer.parseInt(selectedRow.split("#")[0]);
            GameState toBeLoaded = dbManager.loadGame(saveId);
            loadGame(toBeLoaded);
            popup.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(saveLabel, listView, submit, loadFromFile);
        layout.setAlignment(Pos.CENTER);

        Scene popUpScene = new Scene(layout, 300, 400);
        popup.setScene(popUpScene);
        popup.showAndWait();
    }

    private List<EnemyModel> getAllEnemyModels() {
        List<EnemyModel> enemiesLvl1 = lvl1.getEnemies().stream()
            .map(enemy -> { return new EnemyModel(
                    enemy.getTileName(),
                    enemy.getHealth(),
                    enemy.getX(),
                    enemy.getY(),
                    1);
            })
            .collect(Collectors.toList());

        List<EnemyModel> enemiesLvl2 = lvl2.getEnemies().stream()
            .map(enemy -> { return new EnemyModel(
                enemy.getTileName(),
                enemy.getHealth(),
                enemy.getX(),
                enemy.getY(),
                2);
            })
            .collect(Collectors.toList());

        enemiesLvl1.addAll(enemiesLvl2);
        return enemiesLvl1;
    }

    private List<ItemModel> getAllItemModels() {
        List<ItemModel> result = new ArrayList<>();

        Item sword = lvl1.getItemByTileName("sword");
        Item key = lvl1.getItemByTileName("key");

        ItemModel swordModel = sword != null
            ? new ItemModel(sword.getTileName(), sword.getX(), sword.getY(), false, false)
            : new ItemModel("sword", true, false);

        ItemModel keyModel = key != null
            ? new ItemModel(key.getTileName(), key.getX(), key.getY(), false, !lvl1.getDoorDown().isLocked())
            : new ItemModel("key", true, !lvl1.getDoorDown().isLocked());

        result.add(swordModel);
        result.add(keyModel);

        return result;
    }
}
