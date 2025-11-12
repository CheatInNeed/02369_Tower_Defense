package io.github.cheatinneed.tower_defense.model.projectiles;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;

public class Projectile {
    private float x, y;
    private final float speed;      // pixels/sec
    private final float damage;
    private Enemy target;
    private boolean alive = true;
    private float hitRadius = 12f;  // collision radius (pixels)

    public Projectile(float x, float y, Enemy target, float speed, float damage) {
        this.x = x;
        this.y = y;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
    }

    public boolean isAlive() { return alive; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void update(float dt) {
        if (!alive) return;
        if (target == null || target.isDead()) { alive = false; return; }

        float dx = target.getX() - x;
        float dy = target.getY() - y;
        float d2 = dx*dx + dy*dy;

        // Hit?
        float hr2 = hitRadius * hitRadius;
        if (d2 <= hr2) {
            // Apply damage (rename if your Enemy uses a different method)
            // TODO: IMPLEMENT // target.takeDamage(damage);
            alive = false;
            return;
        }

        // Move toward target
        float len = (float)Math.sqrt(d2);
        if (len > 0.0001f) {
            float vx = dx / len;
            float vy = dy / len;
            x += vx * speed * dt;
            y += vy * speed * dt;
        } else {
            alive = false; // overlapping; mark as consumed
        }
    }
}
