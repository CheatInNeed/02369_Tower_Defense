package io.github.cheatinneed.tower_defense.model.projectiles;


import io.github.cheatinneed.tower_defense.model.enemies.Enemy;

public class ProjectileFactory {
    public static Projectile createProjectile(float x, float y, Enemy target, float speed, float damage, String type) {
        return switch (type.toLowerCase()) {
            case "cannon" -> new CannonProjectile(x,y,target,speed,damage);
            case "flame" -> new FlameProjectile(x,y,target,speed,damage);
            case "sticky" -> new StickyProjectile(x,y,target,speed,damage);
            default -> throw new IllegalStateException("Unexpected value: " + type.toLowerCase());
        };
    }
}
