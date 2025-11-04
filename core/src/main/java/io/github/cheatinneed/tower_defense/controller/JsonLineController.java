package io.github.cheatinneed.tower_defense.controller;
import com.google.gson.JsonObject;


public class JsonLineController {
    public void handle(JsonObject json) {
        String action = json.get("action").getAsString();

        switch (action) {
            case "spawnEnemy" -> {
                String enemyType = json.get("enemyType").getAsString();
                int delay = json.get("delay").getAsInt();
                float x = json.get("x").getAsFloat();
                float y = json.get("y").getAsFloat();
                try {
                    if (delay > 0) {
                        Thread.sleep(delay);  // wait before spawning
                    }
                    spawnEnemy(enemyType, x, y);
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

    private void spawnEnemy(String type, float x, float y) {
        // Call your game methods here
        //System.out.printf("Spawning %s enemy at (%.1f, %.1f)%n", type, x, y);
    }

    private void giveGold(int amount) {
        // Call your game methods here
        //System.out.println("Giving player " + amount + " gold");
    }
}
