package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;

public class EnemyRenderer {

    private static Texture pixel;

    private static Texture getPixel() {
        if (pixel == null) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(Color.WHITE);
            pix.fill();
            pixel = new Texture(pix);
            pix.dispose();
        }
        return pixel;
    }

    public static void draw(SpriteBatch batch) {
        for (Enemy e : EnemyManager.getInstance().getEnemies()) {
            batch.setColor(1f, 0f, 0f, 1f);
            batch.draw(getPixel(), e.getX(), e.getY(), 16, 16);
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }
}
