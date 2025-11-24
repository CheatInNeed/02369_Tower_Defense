package io.github.cheatinneed.tower_defense.model.towers;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.projectiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {

    static class DummyEnemy implements Enemy {
        private float x, y;
        private float speed = 2f;
        private float hp;
        private boolean dead = false;

        DummyEnemy(float x, float y, float hp) {
            this.x = x; this.y = y; this.hp = hp;
        }

        @Override public float getX() { return x; }
        @Override public float getY() { return y; }
        @Override public float getSpeed() { return speed; }
        @Override public float getRenderSize() { return 48f; }

        @Override public float getCurrentHealth() { return hp; }
        @Override public float getMaxHealth() { return hp; }
        @Override public void setCurrentHealth(float health) { hp = health; if (hp <= 0) dead = true; }
        @Override public void setPosition(float x, float y) { this.x=x; this.y=y; }
        @Override public void damage(float amount) { hp -= amount; if (hp <= 0) dead = true; }
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
        TowerManager.getInstance().getTowers().clear();
    }

    // ---------------- existing Cannon tests ----------------

    @Test
    void towerAcquiresClosestTargetAndShoots() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy close = new DummyEnemy(50, 0, 10);
        DummyEnemy far   = new DummyEnemy(200, 0, 10);

        em.addEnemy(close);
        em.addEnemy(far);

        CannonTower tower = new CannonTower(0, 0);
        tower.update(1f);

        assertEquals(1, pm.getProjectiles().size,
            "Tower should have spawned one projectile");

        Projectile proj = pm.getProjectiles().first();
        assertTrue(proj instanceof AbstractProjectile);

        AbstractProjectile ap = (AbstractProjectile) proj;
        assertSame(close, ap.getTarget(),
            "Tower should target the closest enemy in range");
    }

    @Test
    void towerRespectsCooldownAndDoesNotFireTooFast() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemy = new DummyEnemy(50, 0, 10);
        em.addEnemy(enemy);

        CannonTower tower = new CannonTower(0, 0);

        tower.update(1f);   // fires once
        tower.update(0.1f); // too soon

        assertEquals(1, pm.getProjectiles().size,
            "Tower should not fire again before cooldown expires");
    }

    @Test
    void towerRotatesTowardTarget() {
        EnemyManager em = EnemyManager.getInstance();
        DummyEnemy enemyUp = new DummyEnemy(0, 100, 10);
        em.addEnemy(enemyUp);

        CannonTower tower = new CannonTower(0, 0);
        tower.update(0.1f);

        float rot = tower.getRotation();
        assertEquals(90f, rot, 1.0f,
            "Tower rotation should face upward target (approx 90 degrees)");
    }

    @Test
    void towerDoesNotShootOutOfRange() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemyFar = new DummyEnemy(1000, 1000, 10);
        em.addEnemy(enemyFar);

        CannonTower tower = new CannonTower(0, 0);
        tower.update(2f);

        assertEquals(0, pm.getProjectiles().size,
            "Tower should not shoot enemies outside its range");
    }

    // ---------------- NEW: FlameTower tests ----------------

    @Test
    void flameTowerSpawnsFlameProjectile() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemy = new DummyEnemy(50, 0, 10); // within range 120
        em.addEnemy(enemy);

        FlameTower tower = new FlameTower(0, 0);
        tower.update(0.1f);

        assertEquals(1, pm.getProjectiles().size,
            "FlameTower should spawn a projectile when enemy in range");

        assertTrue(pm.getProjectiles().first() instanceof FlameProjectile,
            "FlameTower must spawn FlameProjectile based on type='flame'");
    }

    @Test
    void flameTowerHighFireRateLeadsToFrequentShotsAcrossUpdates() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemy = new DummyEnemy(50, 0, 10);
        em.addEnemy(enemy);

        FlameTower tower = new FlameTower(0, 0);

        tower.update(0.01f); // should fire once, cooldown ~0.02
        assertEquals(1, pm.getProjectiles().size);

        tower.update(0.01f); // cooldown still >0 -> no fire
        assertEquals(1, pm.getProjectiles().size,
            "FlameTower should not fire again before cooldown");

        tower.update(0.03f); // cooldown passes -> fire again
        assertEquals(2, pm.getProjectiles().size,
            "FlameTower should fire again quickly due to high fireRate");
    }

    // ---------------- NEW: StickyTower tests ----------------

    @Test
    void stickyTowerSpawnsStickyProjectile() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemy = new DummyEnemy(80, 0, 10); // within range 180
        em.addEnemy(enemy);

        StickyTower tower = new StickyTower(0, 0);
        tower.update(0.2f);

        assertEquals(1, pm.getProjectiles().size,
            "StickyTower should spawn a projectile when enemy in range");

        assertTrue(pm.getProjectiles().first() instanceof StickyProjectile,
            "StickyTower must spawn StickyProjectile based on type='sticky'");
    }

    @Test
    void stickyTowerShotSlowsEnemyAfterHit() {
        EnemyManager em = EnemyManager.getInstance();
        ProjectileManager pm = ProjectileManager.getInstance();

        DummyEnemy enemy = new DummyEnemy(5, 0, 10); // very close so projectile hits fast
        float beforeSpeed = enemy.getSpeed();
        em.addEnemy(enemy);

        StickyTower tower = new StickyTower(0, 0);
        tower.update(0.1f); // spawns a sticky projectile

        assertEquals(1, pm.getProjectiles().size);

        // advance projectiles so it hits + onDeath runs (slow applied)
        pm.update(0.1f);

        assertTrue(enemy.getSpeed() < beforeSpeed,
            "StickyProjectile onDeath should slow the target enemy");
    }
}
