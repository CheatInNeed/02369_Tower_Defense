package io.github.cheatinneed.tower_defense.model.enemies;

public interface Enemy {

    float getX();
    float getY();
    float getSpeed();
    float getRenderSize();

    int getCurrentHealth();
    int getMaxHealth();

    void setCurrentHealth(int health);
    void setPosition(float x, float y);
    void damage(int amount);

    boolean isDead();
    void update();
    EnemyType getType();
}
