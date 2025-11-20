package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.Player;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyManagerEconomyTest {

    private EnemyManager enemyManager;
    private Player player;

    private Path makePath(float x, float y) {
        return new Path(List.of(
            new PathPoint(x, y),
            new PathPoint(x + 1, y)
        ));
    }

    @BeforeEach
    void setup() {
        enemyManager = EnemyManager.getInstance();
        enemyManager.getEnemies().clear();

        player = new Player();
    }

    @Test
    void playerGetsMoneyWhenEnemyDies() {
        Enemy e = new BasicEnemy(makePath(0, 0));
        int reward = e.getMoneyYield();

        enemyManager.addEnemy(e);

        // Kill enemy
        e.damage(999);

        int before = player.getMoney();
        enemyManager.update(player);
        int after = player.getMoney();

        assertEquals(before + reward, after);
        assertTrue(enemyManager.isEmpty());
    }
}
