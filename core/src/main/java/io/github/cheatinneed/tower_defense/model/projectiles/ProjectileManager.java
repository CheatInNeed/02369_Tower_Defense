package io.github.cheatinneed.tower_defense.model.projectiles;

import com.badlogic.gdx.utils.Array;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;

public class ProjectileManager {
    private static ProjectileManager instance;
    public static ProjectileManager getInstance() {
        if (instance == null) instance = new ProjectileManager();
        return instance;
    }

    private final Array<Projectile> projectiles = new Array<>();

    private ProjectileManager() {}

    public void spawn(float x, float y, Enemy target, float speed, float damage) {
        projectiles.add(new Projectile(x, y, target, speed, damage));
    }

    public Array<Projectile> getProjectiles() { return projectiles; }

    public void update(float dt) {
        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.update(dt);
            if (!p.isAlive()) projectiles.removeIndex(i);
        }
    }
}
