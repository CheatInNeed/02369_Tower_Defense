package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicEnemyTest {

    private Path makePath(float x, float y) {
        return new Path(List.of(
            new PathPoint(x, y),
            new PathPoint(x + 1, y)
        ));
    }

    @Test
    void constructorInitializesDefaults() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(2.5f, 7.75f));

        assertTrue(enemy instanceof BasicEnemy);
        assertEquals(2.5f, enemy.getX(), 0.0001f);
        assertEquals(7.75f, enemy.getY(), 0.0001f);
        assertEquals(50, enemy.getCurrentHealth(), "BasicEnemy should start with currentHealth = 50");
        assertEquals(50, enemy.getMaxHealth(), "BasicEnemy should have maxHealth = 50");
    }

    @Test
    void setPositionUpdatesCoordinates() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(0f, 0f));
        enemy.setPosition(10.0f, 20.0f);
        assertEquals(10.0f, enemy.getX(), 0.0001f);
        assertEquals(20.0f, enemy.getY(), 0.0001f);
    }

    @Test
    void setCurrentHealthChangesHealth() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(0f, 0f));
        enemy.setCurrentHealth(0);
        assertEquals(0, enemy.getCurrentHealth());
    }

    @Test
    void damagingEnemyChangesHealth() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(0f, 0f));
        enemy.damage(1);
        assertEquals(49, enemy.getCurrentHealth());
    }

    @Test
    void EnemyCanDied() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(0f, 0f));
        enemy.damage(50);
        enemy.update();
        assertTrue(enemy.isDead());
    }

    @Test
    void updateKeepsEnemyAliveWhenHealthAboveZero() {
        Enemy enemy = EnemyFactory.createEnemy("basic", makePath(0f, 0f));
        enemy.update();
        assertFalse(enemy.isDead(), "Enemy should not be dead when health > 0");
    }
}
