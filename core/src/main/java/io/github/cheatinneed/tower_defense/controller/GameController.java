package io.github.cheatinneed.tower_defense.controller;

import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;

public class GameController {

    private final WaveManager waveManager;
    private final EnemyManager enemyManager;
    private final TowerManager towerManager;
    private final ProjectileManager projectileManager;

    private boolean paused = false;

    public GameController(WaveManager waveManager) {
        this.waveManager = waveManager;
        this.enemyManager = EnemyManager.getInstance();
        this.towerManager = TowerManager.getInstance();
        this.projectileManager = ProjectileManager.getInstance();
    }

    public void update(float dt) {
        if (paused) return;

        waveManager.update(dt);
        enemyManager.update();
        towerManager.update(dt);
        projectileManager.update(dt);
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }
}
