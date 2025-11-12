package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

import io.github.cheatinneed.tower_defense.model.towers.Tower;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;

public class TowerRenderer {
    private static Texture cannonTex;
    private static Texture pixel;

    public static void load() {
        cannonTex = new Texture("cannon.png");
    }

    private static Texture getPixel() {
        if (pixel == null) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(Color.DARK_GRAY);
            pix.fill();
            pixel = new Texture(pix);
            pix.dispose();
        }
        return pixel;
    }

    public static void draw(SpriteBatch batch) {
        for (Tower t : TowerManager.getInstance().getTowers()) {
            Texture tex = cannonTex != null ? cannonTex : getPixel();
            float size = 40f; // visual size (independent of grid size)
            float half = size / 2f;
            batch.draw(tex, t.getX() - half, t.getY() - half, size, size);
        }
    }

    public static void dispose() {
        if (cannonTex != null) cannonTex.dispose();
        if (pixel != null) pixel.dispose();
    }
}
