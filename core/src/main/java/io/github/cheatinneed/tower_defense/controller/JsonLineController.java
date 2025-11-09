/*
package io.github.cheatinneed.tower_defense.controller;
import com.google.gson.JsonObject;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JsonLineController {
    // TODO: change/remove!
    // The following list and getter is just for test purposes.
    // When the factory creates and enemy it should be passed to the game in some way.
    private final List<Enemy> createdEnemies = new ArrayList<>();
    private final Path path;

    public JsonLineController(Path path) {
        this.path = path;
    }

    public List<Enemy> getCreatedEnemies() {
        return createdEnemies;
    }

    public void handle(JsonObject json) {

        String action = json.get("action").getAsString();

        switch (action) {
            case "spawnEnemy" -> {
                String enemyType = json.get("enemyType").getAsString();
                int delay = json.get("delay").getAsInt();
                float x = json.get("x").getAsFloat();
                float y = json.get("y").getAsFloat();
                int health = json.get("health").getAsInt();
                int speed = json.get("speed").getAsInt();
                try {
                    if (delay > 0) {

                        // TODO: implement som logic with tickrate or delta time instead of delays
                        */
/*Thread.sleep(delay * 1000L);  // wait before spawning *1000L to convert to seconds*//*

                        Thread.sleep(delay * 1000L);   // Faster for testing.
                    }
                    Enemy enemy = createEnemy(enemyType, x, y, health, speed);
                    createdEnemies.add(enemy);
                    spawnEnemy(enemy);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // restore interrupted flag
                    System.err.println("Spawn delay interrupted: " + e.getMessage());
                }
            }

            case "giveGold" -> {
                int amount = json.get("amount").getAsInt();
                giveGold(amount);
            }

            default -> {
                System.err.println("Unknown action: " + action);
            }
        }
    }

    private Enemy createEnemy(String type, int health, float speed) {
        Enemy e = EnemyFactory.createEnemy(type, path);
        e.setCurrentHealth(health);
        e.setPosition(path.getPoint(0).x(), path.getPoint(0).y());
        // if speed is customizable, add setter in enemy or adjust factory
        return e;
    }

    private void spawnEnemy(Enemy enemy) {
        // TODO: pass the enemy onto the game here.
        System.out.println("An enemy has been spawned!");
    }

    private void giveGold(int amount) {
        // Call your game methods here
        //System.out.println("Giving player " + amount + " gold");
    }
}
*/
