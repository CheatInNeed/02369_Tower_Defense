package io.github.cheatinneed.tower_defense.model.towers;

public abstract class AbstractTower implements Tower {

    protected float x, y;
    protected int health;
    protected float range;
    protected int level;

    public AbstractTower(float x, float y, int health, float range) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.range = range;
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
    public int getHealth() { return health; }

    @Override
    public float getRange() { return range; }

    @Override
    public int getLevel() { return level; }

    @Override
    public void upgrade() {
    }
}
