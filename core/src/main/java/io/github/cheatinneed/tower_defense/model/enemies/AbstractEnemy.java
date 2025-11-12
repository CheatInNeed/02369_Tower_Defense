package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;

public abstract class AbstractEnemy implements Enemy {

    protected float x, y;
    protected float speed;
    protected float maxHealth;
    protected float currentHealth;
    protected boolean dead = false;

    protected final Path path;
    protected int pathIndex = 0;

    @Override
    public abstract EnemyType getType();

    public AbstractEnemy(Path path, float speed, float maxHealth) {
        this.path = path;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;

        PathPoint start = path.getPoint(0);
        this.x = start.x();
        this.y = start.y();
    }

    @Override
    public float getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setCurrentHealth(float health) {
        this.currentHealth = health;
        if (currentHealth <= 0) dead = true;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void damage(float amount) {
        this.currentHealth -= amount;
        if (currentHealth <= 0) dead = true;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getSpeed() { return speed; }

    @Override
    public void update() {
        if (dead) return;

        if (pathIndex >= path.size() - 1) {
            dead = true;
            return;
        }

        PathPoint current = path.getPoint(pathIndex);
        PathPoint next = path.getPoint(pathIndex + 1);

        float dx = next.x() - x;
        float dy = next.y() - y;
        float dist = (float)Math.sqrt(dx*dx + dy*dy);

        if (dist < 0.001f) {
            pathIndex++;
            update();
            return;
        }

        float stepX = (dx / dist) * speed;
        float stepY = (dy / dist) * speed;

        x += stepX;
        y += stepY;

        if (Math.signum(dx) != Math.signum(next.x() - x) ||
            Math.signum(dy) != Math.signum(next.y() - y)) {

            x = next.x();
            y = next.y();
            pathIndex++;

            if (pathIndex >= path.size() - 1) {
                dead = true;
            }
        }
    }

}
