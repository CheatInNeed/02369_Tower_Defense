package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.*;

public abstract class AbstractEnemy implements Enemy {

    protected float x, y;
    protected float speed;
    protected int health;
    protected int currentIndex = 0;
    protected final Path path;

    public AbstractEnemy(Path path, float speed, int health) {
        this.path = path;
        this.speed = speed;
        this.health = health;

        PathPoint start = path.getStart();
        this.x = start.x();
        this.y = start.y();
    }

    @Override
    public void move() {
        if (currentIndex < path.size() - 1) {
            PathPoint next = path.getPoint(currentIndex + 1);
            float dx = next.x() - x;
            float dy = next.y() - y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);

            if (distance <= speed) {
                x = next.x();
                y = next.y();
                currentIndex++;
            } else {
                x += (dx / distance) * speed;
                y += (dy / distance) * speed;
            }
        }
    }

    @Override
    public boolean reachedEnd() {
        return currentIndex >= path.size() - 1;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public float getSpeed() { return speed; }

    @Override
    public int getHealth() { return health; }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
    }

    @Override
    public Path getPath() { return path; }
}
