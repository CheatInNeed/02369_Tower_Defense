package io.github.cheatinneed.tower_defense.model.Enemies;

import io.github.cheatinneed.tower_defense.controller.JsonLineController;
import io.github.cheatinneed.tower_defense.services.JsonDataLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WaveEnemyTest {

    @Test
    void createEnemiesFromJsonFile() {
        JsonLineController jsonLineController = new JsonLineController();
        JsonDataLoader jsonDataLoader = new JsonDataLoader(jsonLineController);

        try {
            jsonDataLoader.load(
                Path.of("src/main/java/io/github/cheatinneed/tower_defense/assets/waveTest.txt")
            );
        } catch (IOException e) {
            fail("Loading json file failed: " + e.getMessage());
        }

        List<Enemy> enemies = jsonLineController.getSpawnedEnemies();

        // 1) We should have 20 enemies from the file
        assertEquals(20, enemies.size(), "Expected 20 enemies to be spawned");

        // 2) All of them should be BasicEnemy (given your wave file)
        for (Enemy enemy : enemies) {
            assertTrue(enemy instanceof BasicEnemy,
                "Expected all enemies to be BasicEnemy, but found: " + enemy.getClass().getSimpleName());
            assertEquals(0f, enemy.getX(), 0.0001f);
            assertEquals(0f, enemy.getY(), 0.0001f);
        }
    }
}
