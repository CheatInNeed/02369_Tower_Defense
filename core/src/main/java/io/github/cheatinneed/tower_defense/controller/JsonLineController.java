package io.github.cheatinneed.tower_defense.controller;
import com.google.gson.JsonObject;
import io.github.cheatinneed.tower_defense.model.Enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.Enemies.EnemyFactory;

import java.util.ArrayList;
import java.util.List;


public class JsonLineController {
    // TODO: change/remove!
    // The following list and getter is just for test purposes.
    // When the factory creates and enemy it should be passed to the game in some way.
    private final List<Enemy> spawnedEnemies = new ArrayList<>();

    public List<Enemy> getSpawnedEnemies() {
        return spawnedEnemies;
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
                        /*Thread.sleep(delay * 1000L);  // wait before spawning *1000L to convert to seconds*/
                        Thread.sleep(delay * 100L);   // Faster for testing.
                    }
                    spawnEnemy(enemyType, x, y, health, speed);
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

    private void spawnEnemy(String type, float x, float y, int health, int speed) {
        Enemy enemy = EnemyFactory.createEnemy(type, x, y, health, speed);

        // For tests only.
        spawnedEnemies.add(enemy);
    }

    private void giveGold(int amount) {
        // Call your game methods here
        //System.out.println("Giving player " + amount + " gold");
    }
}
