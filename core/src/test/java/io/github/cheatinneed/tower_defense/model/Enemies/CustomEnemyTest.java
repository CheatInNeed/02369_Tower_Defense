package io.github.cheatinneed.tower_defense.model.Enemies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomEnemyTest {

    @Test
    void constructorSetsMaxAndCurrentHealth() {
        Enemy enemy = EnemyFactory.createEnemy("custom", 1.25f, -3.5f, 42,15);
        assertTrue(enemy instanceof CustomEnemy);
        assertEquals(1.25f, enemy.getX(), 0.0001f);
        assertEquals(-3.5f, enemy.getY(), 0.0001f);
        assertEquals(42, enemy.getCurrentHealth(), "CustomEnemy should start with currentHealth = maxHealth");
        // This is the expected behavior; if this assertion fails, there may be a bug in getMaxHealth implementation
        assertEquals(42, enemy.getMaxHealth(), "CustomEnemy should report its maxHealth value");
    }

    @Test
    void canChangeHealth() {
        Enemy enemy = EnemyFactory.createEnemy("custom", 0f, 0f, 5,15);
        enemy.setCurrentHealth(3);
        assertEquals(3, enemy.getCurrentHealth());
    }

    @Test
    void setPositionWorks() {
        Enemy enemy = EnemyFactory.createEnemy("custom", 0f, 0f, 10,15);
        enemy.setPosition(4.5f, 9.0f);
        assertEquals(4.5f, enemy.getX(), 0.0001f);
        assertEquals(9.0f, enemy.getY(), 0.0001f);
    }
}
