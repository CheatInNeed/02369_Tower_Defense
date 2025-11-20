package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;

public interface Tower {
    float getX();
    float getY();
    void update(float deltaTime);
    void draw(SpriteBatch batch);

    /** Optional: returns true if the tower can shoot this enemy (used for logic/debug). */
    default boolean canTarget(Enemy e) { return true; }

    int getCost();
}
