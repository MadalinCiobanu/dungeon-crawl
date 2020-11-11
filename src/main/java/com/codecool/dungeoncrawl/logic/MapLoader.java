package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Scorpion;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.DoorDown;
import com.codecool.dungeoncrawl.logic.items.DoorUp;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
//    private static boolean gameStarted = false;

    public static GameMap loadMap(String mapPath) {

        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();

            for (int x = 0; x < width; x++) {

                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);

                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'w':
                            cell.setType(CellType.WATER);
                            break;
                        case 'b':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Scorpion(cell);
                            break;
                        case '/':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            new Enemy(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            new DoorDown(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            new DoorUp(cell);
                            break;
                        case '@':
//                            if (!gameStarted) {
                                cell.setType(CellType.FLOOR);
                                map.setPlayer(new Player(cell));
//                            } else {
//                                cell.setType(CellType.FLOOR);
//                                map.setPlayer(pl);
//                            }
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
//        gameStarted = true;
        return map;
    }

}
