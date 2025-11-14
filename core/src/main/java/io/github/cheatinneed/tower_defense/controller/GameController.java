package io.github.cheatinneed.tower_defense.controller;

import io.github.cheatinneed.tower_defense.model.Player;
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
    private final Player player = new Player();

    public GameController(WaveManager waveManager) {
        this.waveManager = waveManager;
        this.enemyManager = EnemyManager.getInstance();
        this.towerManager = TowerManager.getInstance();
        this.projectileManager = ProjectileManager.getInstance();
    }

    public void update(float dt) {
        if (paused) return;

        waveManager.update(dt);
        enemyManager.update(player);
        towerManager.update(dt);
        projectileManager.update(dt);

        //System.out.println(player.getLives());
        if (player.isDead()) {
            System.out.println("PLAYER IS DEAD");
            // TODO - Handle game over. Game over screen skal vises, skal spillet pauses her.
            //pause();
        }
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
