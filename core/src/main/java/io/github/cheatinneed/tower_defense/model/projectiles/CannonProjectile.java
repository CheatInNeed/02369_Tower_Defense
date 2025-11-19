package io.github.cheatinneed.tower_defense.model.projectiles;

import io.github.cheatinneed.tower_defense.model.enemies.Enemy;

public class CannonProjectile extends AbstractProjectile {
    public CannonProjectile(float x, float y, Enemy target, float speed, float damage){
        super(x,y,target,speed,damage);
    }


}
