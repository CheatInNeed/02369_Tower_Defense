package io.github.cheatinneed.tower_defense.model.Enemies;

public abstract class AbstractEnemy implements Enemy {
    float x, y;
    int maxHealth;
    int currentHealth;
    int level;
    int speed;

    public AbstractEnemy(float x, float y, int maxHealth, int speed) {

        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.speed = speed;
        this.level = 1;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() { return x; }

    @Override
    public float getY() { return y; }

    @Override
    public void setCurrentHealth(int health) {this.currentHealth = health; }

    @Override
    public int getCurrentHealth() { return currentHealth; }

    @Override
    public int getMaxHealth() { return currentHealth; }

}
