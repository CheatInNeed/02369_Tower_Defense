package io.github.cheatinneed.tower_defense.model.Enemies;

public abstract class AbstractEnemy implements Enemy {
    float x, y;
    int maxHealth;
    int currentHealth;
    int level;
    int speed;
    boolean isDead;

    public AbstractEnemy(float x, float y, int maxHealth, int speed) {

        this.x = x;
        this.y = y;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.speed = speed;
        this.level = 1;
        this.isDead = false;
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

    @Override
    public void damage(int damage) { this.currentHealth -= damage; }

    @Override
    public boolean isDead() { return this.isDead; }

    @Override
    public void update(){
        if (this.getCurrentHealth() >= 0){
            this.isDead = true;
        }
    }
}
