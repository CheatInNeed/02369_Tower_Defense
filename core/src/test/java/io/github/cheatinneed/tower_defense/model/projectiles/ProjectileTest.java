package io.github.cheatinneed.tower_defense.model.projectiles;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    // Simple fake enemy, no LibGDX needed
    static class DummyEnemy implements Enemy {
        private float x, y;
        private float speed = 3f;
        private float currentHealth;
        private float maxHealth;
        private boolean dead = false;

        DummyEnemy(float x, float y, float health) {
            this.x = x; this.y = y;
            this.currentHealth = health;
            this.maxHealth = health;
        }

        @Override public float getX() { return x; }
        @Override public float getY() { return y; }
        @Override public float getSpeed() { return speed; }
        @Override public float getRenderSize() { return 48f; }

        @Override public float getCurrentHealth() { return currentHealth; }
        @Override public float getMaxHealth() { return maxHealth; }

        @Override public void setCurrentHealth(float health) {
            currentHealth = health;
            if (currentHealth <= 0) dead = true;
        }

        @Override public void setPosition(float x, float y) { this.x = x; this.y = y; }

        @Override public void damage(float amount) {
            currentHealth -= amount;
            if (currentHealth <= 0) dead = true;
        }

        @Override public void setSpeed(float factor) { speed *= factor; }

        @Override public boolean isDead() { return dead; }
        @Override public void update() {}
        @Override public io.github.cheatinneed.tower_defense.model.enemies.EnemyType getType() {
            return io.github.cheatinneed.tower_defense.model.enemies.EnemyType.BASIC;
        }
        @Override public boolean hasReachedGoal() { return false; }
        @Override public int getMoneyYield() { return 0; }
    }

    @BeforeEach
    void resetManagers() {
        EnemyManager.getInstance().getEnemies().clear();
        ProjectileManager.getInstance().getProjectiles().clear();
    }

    @Test
    void projectileMovesTowardTarget() {
        DummyEnemy enemy = new DummyEnemy(100, 0, 10);
        Projectile p = new CannonProjectile(0, 0, enemy, 50f, 1f);

        p.update(1f); // 1 second

        assertTrue(p.getX() > 0, "Projectile should move along +X toward target");
        assertEquals(0f, p.getY(), 0.0001f);
        assertTrue(p.isAlive(), "Projectile should still be alive while far away");
    }

    @Test
    void projectileHitsTargetAndDies() {
        DummyEnemy enemy = new DummyEnemy(5, 0, 10); // within hit radius (12px)
        AbstractProjectile p = new CannonProjectile(0, 0, enemy, 50f, 3f);

        p.update(0.1f);

        assertFalse(p.isAlive(), "Projectile should die on hit");
        assertEquals(7f, enemy.getCurrentHealth(), 0.0001f,
            "Enemy should take damage when hit");
    }

    @Test
    void projectileDiesIfTargetIsDeadOrNull() {
        DummyEnemy enemy = new DummyEnemy(5, 0, 10);
        enemy.damage(999); // kill it
        assertTrue(enemy.isDead());

        Projectile p1 = new CannonProjectile(0, 0, enemy, 50f, 3f);
        p1.update(0.1f);
        assertFalse(p1.isAlive(), "Projectile should die if target already dead");

        Projectile p2 = new CannonProjectile(0, 0, null, 50f, 3f);
        p2.update(0.1f);
        assertFalse(p2.isAlive(), "Projectile should die if target is null");
    }

    @Test
    void stickyProjectileAppliesSlowOnDeath() {
        DummyEnemy enemy = new DummyEnemy(5, 0, 10);
        float beforeSpeed = enemy.getSpeed();

        StickyProjectile p = new StickyProjectile(0, 0, enemy, 50f, 1f);
        p.update(0.1f);   // hit -> dead
        assertFalse(p.isAlive());

        p.onDeath();      // manager would call this
        assertTrue(enemy.getSpeed() < beforeSpeed,
            "Sticky projectile should reduce target speed on death");
    }

    @Test
    void projectileManagerRemovesDeadProjectiles() {
        DummyEnemy enemy = new DummyEnemy(5, 0, 10);

        ProjectileManager pm = ProjectileManager.getInstance();
        pm.spawn(0, 0, enemy, 50f, 1f, "cannon");

        assertEquals(1, pm.getProjectiles().size);

        pm.update(0.1f); // should hit and remove
        assertEquals(0, pm.getProjectiles().size,
            "Dead projectile should be removed by manager");
    }
}
