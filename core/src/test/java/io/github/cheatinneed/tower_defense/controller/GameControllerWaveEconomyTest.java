package io.github.cheatinneed.tower_defense.controller;

import io.github.cheatinneed.tower_defense.model.Player;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerWaveEconomyTest {

    private GameController controller;
    private WaveManager waveManager;
    private Player player;

    // ---- HELPER METHOD (samme stil som BasicEnemyTest) ----
    private Path makePath(float x, float y) {
        return new Path(List.of(
            new PathPoint(x, y),
            new PathPoint(x + 1, y)
        ));
    }

    @BeforeEach
    void setup() {
        waveManager = new WaveManager(makePath(0, 0));
        controller = new GameController(waveManager);

        player = controller.getPlayer();

        // Clean enemies
        EnemyManager.getInstance().getEnemies().clear();
    }

    // ---- HELPER: Simulate full wave execution ----
    private void forceCompleteWave(Player player) {
        EnemyManager em = EnemyManager.getInstance();

        // Guard against infinite loops
        int guard = 0;

        while (waveManager.isWaveRunning()) {

            // Update wave so it can spawn enemies
            waveManager.update(1f);

            // For all spawned enemies: kill them instantly
            for (Enemy e : em.getEnemies()) {
                e.damage(99999);
            }

            // Remove dead enemies and give money
            em.update(player);

            if (guard++ > 50000) {
                fail("Wave never completed — infinite loop detected");
            }
        }
    }

    @Test
    void playerGetsWaveRewardWhenWaveCompletes() {

        waveManager.startNextWave();
        controller.update(0.1f); // marks wasWaveRunning = true

        // Simulate complete wave spawn + all enemies dying
        forceCompleteWave(player);

        int before = player.getMoney();

        // Now gameController should detect wave completion
        controller.update(0.1f);

        assertEquals(before + 50, player.getMoney(),
            "Player should receive 50 gold for finishing wave");
    }

    @Test
    void rewardIsNotGivenTwice() {
        waveManager.startNextWave();
        controller.update(0.1f);

        forceCompleteWave(player);

        controller.update(0.1f); // reward 1
        int after1 = player.getMoney();

        controller.update(0.1f); // must NOT reward again
        assertEquals(after1, player.getMoney(),
            "Wave reward must only occur once");
    }

    @Test
    void noRewardIfPlayerIsDead() {
        waveManager.startNextWave();
        controller.update(0.1f);

        // Player dies before wave ends
        player.loseLife();
        assertTrue(player.isDead());

        forceCompleteWave(player);

        int before = player.getMoney();
        controller.update(0.1f);

        assertEquals(before, player.getMoney(),
            "Dead player should not earn wave reward");
    }
}
