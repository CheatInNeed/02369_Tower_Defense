package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

import io.github.cheatinneed.tower_defense.model.projectiles.FlameProjectile;
import io.github.cheatinneed.tower_defense.model.projectiles.Projectile;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;

public class ProjectileRenderer {
    private static Texture pixel;
    private static Texture beam;
    public static void load() {
        if (pixel == null) {
            Pixmap pix = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
            pix.setColor(Color.YELLOW);
            pix.fill();
            pixel = new Texture(pix);
            pix.dispose();
        }
    }

    public static void draw(SpriteBatch batch) {
        var arr = ProjectileManager.getInstance().getProjectiles();
        for (int i = 0; i < arr.size; i++) {
            Projectile p = arr.get(i);
            if (p instanceof FlameProjectile) {
                batch.draw(pixel, p.getX() - 1, p.getY() - 1, 2, 2);
            }

            // Tiny square bullet; swap for sprite/region whenever you have art
            batch.draw(pixel, p.getX() - 1, p.getY() - 1, 2, 2);
        }
    }

    public static void dispose() {
        if (pixel != null) { pixel.dispose(); pixel = null; }
    }
}
