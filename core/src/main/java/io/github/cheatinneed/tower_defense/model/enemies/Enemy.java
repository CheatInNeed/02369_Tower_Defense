package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public interface Enemy {
    void move();
    boolean reachedEnd();

    float getX();
    float getY();
    float getSpeed();

    int getHealth();
    void takeDamage(int amount);

    Path getPath();
}
