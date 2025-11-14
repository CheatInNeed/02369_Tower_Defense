package io.github.cheatinneed.tower_defense.model.waves;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyFactory;
import io.github.cheatinneed.tower_defense.model.path.Path;

public class Wave {
    private final int enemyCount;
    private final float spawnInterval;
    private final String enemyType;
    private final Path path;

    private int spawned = 0;

    public Wave(int enemyCount, float spawnInterval, String enemyType, Path path) {
        this.enemyCount = enemyCount;
        this.spawnInterval = spawnInterval;
        this.enemyType = enemyType;
        this.path = path;
    }

    public boolean hasMoreEnemies() {
        return spawned < enemyCount;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getSpawned() {
        return spawned;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }

    public Enemy spawnEnemy() {
        spawned++;
        return EnemyFactory.createEnemy(enemyType, path);
    }

    public boolean isDone() {
        return spawned >= enemyCount;
    }
}
