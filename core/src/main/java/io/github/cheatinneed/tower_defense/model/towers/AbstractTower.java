package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;

public abstract class AbstractTower implements Tower {
    protected float x, y;
    protected float range = 140f;
    protected float damage = 10f;
    protected float fireRate = 1.0f;
    protected float projectileSpeed = 420f;
    protected float cooldown = 0f;
    protected Enemy target;

    // 🔁 rotation angle in degrees
    protected float rotation = 0f;

    public AbstractTower(float x, float y) { this.x = x; this.y = y; }

    @Override public float getX() { return x; }
    @Override public float getY() { return y; }
    public float getRotation() { return rotation; }

    @Override
    public void update(float dt) {
        if (cooldown > 0f) cooldown -= dt;

        // reacquire if target lost
        if (!isValidTarget(target)) target = acquireClosestTarget();

        // 🔁 rotate toward target (even if not shooting yet)
        if (target != null && !target.isDead()) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            rotation = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        }

        // fire
        if (target != null && cooldown <= 0f) {
            if (inRange(target)) {
                shoot(target);
                cooldown = 1f / fireRate;
            } else {
                target = null;
            }
        }

        onUpdate(dt);
    }

    protected void onUpdate(float dt) {}

    @Override
    public abstract void draw(SpriteBatch batch);

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
                best = e; bestD2 = d2;
            }
        }
        return best;
    }

    protected void shoot(Enemy e) {
        ProjectileManager.getInstance().spawn(x, y, e, projectileSpeed, damage);
    }
}
