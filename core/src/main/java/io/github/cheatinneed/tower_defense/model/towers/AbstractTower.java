package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;

public abstract class AbstractTower implements Tower {
    protected float x, y;              // world center
    protected float range = 140f;      // pixels
    protected float damage = 10f;      // per projectile
    protected float fireRate = 1.0f;   // shots/sec
    protected float projectileSpeed = 420f; // px/sec

    protected float cooldown = 0f;
    protected Enemy target;

    public AbstractTower(float x, float y) { this.x = x; this.y = y; }

    public float getX() { return x; }
    public float getY() { return y; }

    public void update(float dt) {
        if (cooldown > 0f) cooldown -= dt;

        if (!isValidTarget(target)) target = acquireClosestTarget();

        if (target != null && cooldown <= 0f) {
            if (inRange(target)) {
                shoot(target); // spawns projectile
                cooldown = 1f / fireRate;
            } else {
                target = null;
            }
        }
        onUpdate(dt);
    }

    protected void onUpdate(float dt) {}

    public abstract void draw(SpriteBatch batch);

    // ----- targeting helpers -----
    protected boolean isValidTarget(Enemy e) {
        return e != null && !e.isDead() && inRange(e);
    }

    protected boolean inRange(Enemy e) {
        float dx = e.getX() - x, dy = e.getY() - y;
        return dx*dx + dy*dy <= range*range;
    }

    protected Enemy acquireClosestTarget() {
        var enemies = EnemyManager.getInstance().getEnemies();
        Enemy best = null;
        float bestD2 = Float.MAX_VALUE;
        for (int i = 0; i < enemies.size; i++) {
            Enemy e = enemies.get(i);
            if (e.isDead()) continue;
            float dx = e.getX() - x, dy = e.getY() - y;
            float d2 = dx*dx + dy*dy;
            if (d2 <= range*range && d2 < bestD2) {
                bestD2 = d2; best = e;
            }
        }
        return best;
    }

    /** Projectile-based firing */
    protected void shoot(Enemy e) {
        ProjectileManager.getInstance().spawn(x, y, e, projectileSpeed, damage);
    }
}
