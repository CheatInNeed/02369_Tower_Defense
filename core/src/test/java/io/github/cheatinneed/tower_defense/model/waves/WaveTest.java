package io.github.cheatinneed.tower_defense.model.waves;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

public class WaveTest {

    @Test
    public void waveSpawnsCorrectNumberOfEnemies() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(10, 0)
        ));

        Wave wave = new Wave(3, 1f, "basic", path);

        assertTrue(wave.hasMoreEnemies());
        assertFalse(wave.isDone());

        Enemy e1 = wave.spawnEnemy();
        assertNotNull(e1);
        assertEquals(1, wave.getSpawned());

        wave.spawnEnemy();
        wave.spawnEnemy();

        assertFalse(wave.hasMoreEnemies());
        assertTrue(wave.isDone());
    }

    @Test
    public void waveRespectsSpawnInterval() {
        Wave wave = new Wave(5, 2.5f, "basic", null);
        assertEquals(2.5f, wave.getSpawnInterval(), 0.001f);
    }
}
