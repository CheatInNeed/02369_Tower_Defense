package io.github.cheatinneed.tower_defense.model.projectiles;

public interface Projectile {

    public boolean isAlive();
    public float getX();
    public float getY();
    public void update(float dt);
    public void onDeath();
}


