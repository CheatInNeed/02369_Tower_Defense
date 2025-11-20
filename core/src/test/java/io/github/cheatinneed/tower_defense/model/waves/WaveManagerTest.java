package io.github.cheatinneed.tower_defense.model.waves;

import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WaveManagerTest {

    @BeforeEach
    void resetEnemyManager() {
        EnemyManager.getInstance().getEnemies().clear();
    }

    @Test
    void waveManagerSpawnsNoEnemiesBeforeInterval() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(10, 0)
        ));

        WaveManager wm = new WaveManager(path);
        EnemyManager em = EnemyManager.getInstance();

        wm.update(0.5f);

        assertEquals(0, em.getEnemies().size);
    }

    @Test
    void waveManagerSpawnsEnemiesAfterInterval() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(10, 0)
        ));

        WaveManager wm = new WaveManager(path);
        EnemyManager em = EnemyManager.getInstance();

        assertEquals(0, em.getEnemies().size);

        wm.update(1.1f);

        assertEquals(1, em.getEnemies().size);
    }

    @Test
    void waveManagerMovesToNextWaveWhenEnemiesAreCleared() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(10, 0)
        ));

        WaveManager wm = new WaveManager(path);
        EnemyManager em = EnemyManager.getInstance();

        for (int i = 0; i < 5; i++) {
            wm.update(1.1f);
        }

        assertEquals(3, em.getEnemies().size);

        em.getEnemies().clear();
        assertEquals(0, em.getEnemies().size);

        wm.update(0.1f);

        for (int i = 0; i < 5; i++) {
            wm.update(2.1f);
        }

        assertEquals(3, em.getEnemies().size);
    }
}
