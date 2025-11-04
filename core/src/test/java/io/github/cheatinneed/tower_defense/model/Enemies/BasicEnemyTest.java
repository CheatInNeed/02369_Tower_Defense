package io.github.cheatinneed.tower_defense.model.Enemies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicEnemyTest {

    @Test
    void constructorInitializesDefaults() {
        Enemy enemy = EnemyFactory.createEnemy("basic", 2.5f, 7.75f, 0,0);
        assertTrue(enemy instanceof BasicEnemy);
        assertEquals(2.5f, enemy.getX(), 0.0001f);
        assertEquals(7.75f, enemy.getY(), 0.0001f);
        assertEquals(1, enemy.getCurrentHealth(), "BasicEnemy should start with currentHealth = 1");
        assertEquals(1, enemy.getMaxHealth(), "BasicEnemy should have maxHealth = 1");
    }

    @Test
    void setPositionUpdatesCoordinates() {
        Enemy enemy = EnemyFactory.createEnemy("basic", 0f, 0f, 0,0);
        enemy.setPosition(10.0f, 20.0f);
        assertEquals(10.0f, enemy.getX(), 0.0001f);
        assertEquals(20.0f, enemy.getY(), 0.0001f);
    }

    @Test
    void setCurrentHealthChangesHealth() {
        Enemy enemy = EnemyFactory.createEnemy("basic", 0f, 0f, 0,0);
        enemy.setCurrentHealth(0);
        assertEquals(0, enemy.getCurrentHealth());
    }
}
