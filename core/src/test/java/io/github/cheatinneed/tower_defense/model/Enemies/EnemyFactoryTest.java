package io.github.cheatinneed.tower_defense.model.Enemies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyFactoryTest {

    @Test
    void createsBasicEnemyIgnoringCase() {
        Enemy e1 = EnemyFactory.createEnemy("basic", 1f, 2f, 999);
        Enemy e2 = EnemyFactory.createEnemy("BaSiC", -3.5f, 4.25f, 999);
        assertTrue(e1 instanceof BasicEnemy);
        assertTrue(e2 instanceof BasicEnemy);
        assertEquals(1f, e1.getX(), 0.0001f);
        assertEquals(2f, e1.getY(), 0.0001f);
        assertEquals(-3.5f, e2.getX(), 0.0001f);
        assertEquals(4.25f, e2.getY(), 0.0001f);
        // Basic enemies have fixed health of 1
        assertEquals(1, e1.getCurrentHealth());
        assertEquals(1, e1.getMaxHealth());
    }

    @Test
    void createsCustomEnemyWithProvidedMaxHealth() {
        Enemy e = EnemyFactory.createEnemy("custom", 10f, 20f, 7);
        assertTrue(e instanceof CustomEnemy);
        assertEquals(10f, e.getX(), 0.0001f);
        assertEquals(20f, e.getY(), 0.0001f);
        assertEquals(7, e.getCurrentHealth());
        assertEquals(7, e.getMaxHealth(), "Custom enemy should report the same max health that was provided");
    }

    @Test
    void throwsOnUnknownType() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> EnemyFactory.createEnemy("unknown", 0f, 0f, 1));
        assertTrue(ex.getMessage().contains("Unknown enemy type"));
    }
}
