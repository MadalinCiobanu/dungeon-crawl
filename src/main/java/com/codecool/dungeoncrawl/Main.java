package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.DoorDown;
import com.codecool.dungeoncrawl.logic.items.DoorUp;
import com.codecool.dungeoncrawl.logic.items.Key;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

//import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private final double FONT_SIZE = 19.0;

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
        ui.add(pickUpButton, 0, 42, 2 ,1);

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
                int enemyDx = 0;
                if (map.getPlayer().getX() < e.getX())
                    enemyDx = -1;
                else if (map.getPlayer().getX() > e.getX())
                    enemyDx = 1;

                int enemyDy = 0;
                if (map.getPlayer().getY() < e.getY())
                    enemyDy = -1;
                else if (map.getPlayer().getY() > e.getY())
                    enemyDy = 1;
                e.move(enemyDx, enemyDy);
            } else if (e instanceof Scorpion) {
                int scorpionDx = ThreadLocalRandom.current().nextInt(-1, 2);
                int scorpionDy = ThreadLocalRandom.current().nextInt(-1, 2);
                e.move(scorpionDx, scorpionDy);
            } else if (e instanceof Skeleton) {
                e.move(-1, 0);
            }
        }
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
        attackLabel.setText("" + map.getPlayer().getAttack());
        inventoryItems.setText(map.getPlayer().seeInventory().toString());

        changePickUpButton();
        changeLevel();
        isGameOver(map.getPlayer().isDead());
    }

    private void changePickUpButton () {
        if (map.getPlayer().onItem) {
            pickUpButton.setVisible(true);
        } else {
            pickUpButton.setVisible(false);
        }
    }

    private void changeLevel() {
        Player p = map.getPlayer();

        if (p.getCell().getItem() instanceof DoorDown) {
            DoorDown door = (DoorDown) p.getCell().getItem();
            if (p.canUnlock() || !(door.isLocked())) {
                door.unlock();
                map = lvl2;
                p.setCell(map.getCell(23, 18));
                map.setPlayer(p);
            }
        } else if (map.getPlayer().getCell().getItem() instanceof DoorUp) {
            map = lvl1;
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
}
