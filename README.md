# Dungeon Crawl

## Story

[Roguelikes](https://en.wikipedia.org/wiki/Roguelike) are one of the oldest
types of video games, the earliest ones were made in the 70s, they were inspired
a lot by tabletop RPGs. Roguelikes have the following in common usually:

- They are tile-based.
- The game is divided into turns, e.g. you make one action, then the other
  entities (monsters, allies, etc. controlled by the CPU) make one.
- Usually the task is to explore a labyrinth and retrieve some treasure from its
  bottom.
- They feature permadeath: if you die its game over, you need to start from the
  beginning again.
- Are heavily using procedural generation: Levels, monster placement, items,..
  are randomized, so the game does not get boring.

## What we've learned?

- Get more practice in OOP
- Design patterns: layer separation (All of the game logic, i.e., player
  movement, game rules, and so on), is in the `logic` package, completely
  independent of user interface code.
- Serialization of objects
- Communicating with database
- Write unit tests

## How to play?

Clone the repository from:

[GitHub Link](https://github.com/MadalinCiobanu/dungeon-crawl)

Go to the clone folder into the Terminal and run:

```
mvn javafx:run
```

## Game info and features

- The hero can `move` around the map using arrow buttons.

![move_char](https://user-images.githubusercontent.com/62752342/115404479-0b630e80-a1f6-11eb-9bf6-a3ea84a306e0.png)

- The items from dungeon can be picked up by the player using `Pick Up` button.

![pick_up](https://user-images.githubusercontent.com/62752342/115404593-259cec80-a1f6-11eb-8f2d-1e1706e5c8ad.png)

- The items are shown in the player's `inventory`.

![inventory](https://user-images.githubusercontent.com/62752342/115404670-38afbc80-a1f6-11eb-85f5-1321e760e1fa.png)

- The hero is able to `attack` monsters by moving into them.

![fight](https://user-images.githubusercontent.com/62752342/115404730-47966f00-a1f6-11eb-8728-99d31be40a7c.png)

- When fighting, both the player and the monster lose hp depending on the strength of the enemy's attack.

![health](https://user-images.githubusercontent.com/62752342/115404797-54b35e00-a1f6-11eb-9b5c-7c107707b771.png)

- If the `monster`'s health points drop to 0 then it `disappears`.

![empty_spot](https://user-images.githubusercontent.com/62752342/115404857-61d04d00-a1f6-11eb-9efb-db72bd2f14f7.png)

- If the `hero`'s health points drop to 0 then he `dies`.

![game_over](https://user-images.githubusercontent.com/62752342/115404943-74e31d00-a1f6-11eb-8735-20c17779628d.png)

- The player can collect keys to open the doors and move on to the `next levels`.

![finish_lvl](https://user-images.githubusercontent.com/62752342/115405151-ab209c80-a1f6-11eb-8e04-c0e6c0b8893d.png)

![new_lvl](https://user-images.githubusercontent.com/62752342/115405216-ba074f00-a1f6-11eb-8927-6ed1300a6aca.png)

- The player can `save` the current state of the game.

![save_game](https://user-images.githubusercontent.com/62752342/115405281-c68ba780-a1f6-11eb-92f3-9fcf232d9cb4.png)

- Take the crown to `win` the game.

![dungeon_crown](https://user-images.githubusercontent.com/62752342/115405324-d1ded300-a1f6-11eb-8705-35be23bedd10.png)

![win_game](https://user-images.githubusercontent.com/62752342/115405370-dacfa480-a1f6-11eb-94b0-f8b0d6bc6963.png)


## Contact

#### Email: CiobanuMadalinFlorin@Gmail.com