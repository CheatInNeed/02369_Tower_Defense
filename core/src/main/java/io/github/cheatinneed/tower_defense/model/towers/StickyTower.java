package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;

public class StickyTower extends AbstractTower {
    public StickyTower(float x, float y) {
        super(x, y);
        this.range = 180f;
        this.damage = 2f;
        this.fireRate = 1.2f;
        this.projectileSpeed = 500f;
        this.type = "sticky";
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
