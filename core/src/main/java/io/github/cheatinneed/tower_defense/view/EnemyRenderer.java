package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import io.github.cheatinneed.tower_defense.model.enemies.Enemy;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import java.util.HashMap;
import java.util.Map;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyType;

public class EnemyRenderer {

    private static Map<EnemyType, Texture> textures = new HashMap<>();
    private static Texture pixel;

    public static void load() {
        textures.put(EnemyType.BASIC, new Texture("basicEnemy.png"));
        textures.put(EnemyType.CUSTOM, new Texture("customEnemy.png"));
    }

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

            Texture tex = textures.get(e.getType());
            if (tex == null) {
                tex = getPixel(); // fallback hvis texture mangler
            }

            float size = e.getRenderSize();
            float half = size / 2f;
            batch.draw(tex, e.getX() - half, e.getY() - half, size, size);
        }
    }

    public static void dispose() {
        for (Texture t : textures.values()) {
            t.dispose();
        }
    }
}
