package io.github.cheatinneed.tower_defense.model.enemies;

public interface Enemy {

    float getX();
    float getY();
    float getSpeed();
    float getRenderSize();

    float getCurrentHealth();
    float getMaxHealth();

    void setCurrentHealth(float health);
    void setPosition(float x, float y);
    void damage(float amount);
    void setSpeed(float factor);
    boolean isDead();
    void update();
    EnemyType getType();

    boolean hasReachedGoal();

    int getMoneyYield();
}
