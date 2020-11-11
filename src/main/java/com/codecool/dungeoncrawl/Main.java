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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {
    int level = 1;
    GameMap map = MapLoader.loadMap("/map.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryItems = new Label();
    Label nameLabel = new Label();
    Button pickUpButton = new Button("Pick Up");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Enter your name");

        Label nameLabel = new Label("Name: ");
        TextField textField = new TextField();
        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            map.getPlayer().setName(textField.getText());
            popup.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, textField, submit);
        layout.setAlignment(Pos.CENTER);

        Scene popUpScene = new Scene(layout, 500, 400);
        popup.setScene(popUpScene);
        popup.showAndWait();

        // #########################

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setVgap(10);

        nameLabel.setText(map.getPlayer().getName());

        pickUpButton.setOnAction(e -> {
            map.getPlayer().addToInventory(map.getPlayer().getCell().getItem().getTileName());
            map.getPlayer().getCell().getItem().pickItem();
            refresh();
            pickUpButton.setVisible(false);
        });

        ui.add(new Label("Player: "), 0, 0);
        ui.add(nameLabel, 1, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(new Label("Inventory:"), 0, 5);
        ui.add(inventoryItems, 0, 6);
        ui.add(pickUpButton, 1, 53);

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
        inventoryItems.setText(map.getPlayer().seeInventory().toString());
        getPickUpButton();
        changeLevel();
    }

    private void getPickUpButton () {
        if (map.getPlayer().onItem) {
            pickUpButton.setVisible(true);
        } else {
            pickUpButton.setVisible(false);
        }
    }

    private void changeLevel() {
        if (map.getPlayer().getCell().getItem() instanceof DoorDown) {
            map = MapLoader.loadMap("/map1.txt");
        } else if (map.getPlayer().getCell().getItem() instanceof DoorUp) {
            map = MapLoader.loadMap("/map.txt");
            map.getPlayer().setCell(map.getCell(22, 18));
        }
    }
}
