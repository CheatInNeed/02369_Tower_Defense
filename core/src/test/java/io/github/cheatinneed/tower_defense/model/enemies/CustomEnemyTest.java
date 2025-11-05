package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomEnemyTest {

    private Path makePath(float x, float y) {
        return new Path(List.of(
            new PathPoint(x, y),
            new PathPoint(x + 1, y)
        ));
    }

    @Test
    void constructorSetsMaxAndCurrentHealth() {
        Enemy enemy = new CustomEnemy(makePath(1.25f, -3.5f), 15, 42);

        assertTrue(enemy instanceof CustomEnemy);
        assertEquals(1.25f, enemy.getX(), 0.0001f);
        assertEquals(-3.5f, enemy.getY(), 0.0001f);
        assertEquals(42, enemy.getCurrentHealth());
        assertEquals(42, enemy.getMaxHealth());
    }

    @Test
    void canChangeHealth() {
        Enemy enemy = new CustomEnemy(makePath(0f, 0f), 15, 5);
        enemy.setCurrentHealth(3);
        assertEquals(3, enemy.getCurrentHealth());
    }

    @Test
    void setPositionWorks() {
        Enemy enemy = new CustomEnemy(makePath(0f, 0f), 15, 10);
        enemy.setPosition(4.5f, 9.0f);
        assertEquals(4.5f, enemy.getX(), 0.0001f);
        assertEquals(9.0f, enemy.getY(), 0.0001f);
    }

    @Test
    void damagingEnemyChangesHealth() {
        Enemy enemy = new CustomEnemy(makePath(0f, 0f), 10, 10);
        enemy.damage(5);
        assertEquals(5, enemy.getCurrentHealth());
    }
}
