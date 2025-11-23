package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlameTower extends AbstractTower{
    public FlameTower(float x, float y) {
        super(x, y);
        this.range = 120f;
        this.damage = 0.5f;
        this.fireRate = 50f;
        this.projectileSpeed = 500f;
        this.type = "flame";
        this.cost = 50;
    }
    @Override
    protected void onUpdate(float dt) {
        // rotate/animate if you’d like
    }

    @Override
    public void draw(SpriteBatch batch) {
        // If TowerRenderer handles visuals, you can keep this empty.
        // Otherwise, draw the tower sprite at (x,y) here.
    }
}
