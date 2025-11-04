package io.github.cheatinneed.tower_defense.model.Enemies;

public interface Enemy {
    void setPosition(float x, float y);
    float getX();
    float getY();


    int getCurrentHealth();
    int getMaxHealth();
    void setCurrentHealth(int health);
    void damage(int damage);

}
