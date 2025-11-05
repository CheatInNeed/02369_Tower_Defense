package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyFactoryTest {

    private Path makePath(float x, float y) {
        return new Path(List.of(
            new PathPoint(x, y),
            new PathPoint(x + 1, y)
        ));
    }

    @Test
    void createsBasicEnemyIgnoringCase() {
        Enemy e1 = EnemyFactory.createEnemy("basic", makePath(1f, 2f));
        Enemy e2 = EnemyFactory.createEnemy("BaSiC", makePath(-3.5f, 4.25f));

        assertTrue(e1 instanceof BasicEnemy);
        assertTrue(e2 instanceof BasicEnemy);
        assertEquals(1f, e1.getX(), 0.0001f);
        assertEquals(2f, e1.getY(), 0.0001f);
        assertEquals(-3.5f, e2.getX(), 0.0001f);
        assertEquals(4.25f, e2.getY(), 0.0001f);
        assertEquals(50, e1.getCurrentHealth());
        assertEquals(50, e1.getMaxHealth());
    }

    @Test
    void createsCustomEnemyWithProvidedMaxHealth() {
        Enemy e = new CustomEnemy(makePath(10f, 20f), 10, 10);

        assertTrue(e instanceof CustomEnemy);
        assertEquals(10f, e.getX(), 0.0001f);
        assertEquals(20f, e.getY(), 0.0001f);
        assertEquals(10, e.getCurrentHealth());
        assertEquals(10, e.getMaxHealth());
    }

    @Test
    void throwsOnUnknownType() {
        assertThrows(IllegalArgumentException.class,
            () -> EnemyFactory.createEnemy("unknown", makePath(0f, 0f)));
    }
}
