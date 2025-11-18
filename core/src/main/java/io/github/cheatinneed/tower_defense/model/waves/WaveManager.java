package io.github.cheatinneed.tower_defense.model.waves;

import com.badlogic.gdx.utils.Array;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;

public class WaveManager {
    private final Array<Wave> waves = new Array<>();
    private Wave currentWave;
    private float spawnTimer = 0f;
    private final Path path;

    public WaveManager(Path path) {
        this.path = path;

        // example waves
        waves.add(new Wave(3, 1.0f, "basic", path));
        waves.add(new Wave(3, 2f, "custom", path));
    }
    public boolean isWaveRunning(){
        return currentWave != null;
    }
    public boolean hasMoreWaves(){
        return waves.notEmpty();
    }
    public boolean canStartNextWave() {
        return !isWaveRunning()
            && waves.notEmpty()
            && EnemyManager.getInstance().isEmpty();
    }

    public boolean startNextWave() {
        if (!canStartNextWave()) {
            return false;
        }
        spawnTimer = 0f;
        currentWave = waves.removeIndex(0);
        return true;
    }

    public void update(float dt) {
        if (currentWave == null){
            return;
        }

        spawnTimer += dt;


        if (spawnTimer >= currentWave.getSpawnInterval() && currentWave.hasMoreEnemies()) {
            spawnTimer = 0;
            Enemy e = currentWave.spawnEnemy();
            EnemyManager.getInstance().addEnemy(e);
        }

        // only go to next wave when this one is done AND all enemies died/finished
        if (currentWave.isDone() && EnemyManager.getInstance().isEmpty()) {
            currentWave = null;
        }
    }
}
