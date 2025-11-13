package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

import io.github.cheatinneed.tower_defense.model.towers.Tower;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;
import io.github.cheatinneed.tower_defense.model.towers.CannonTower;
// import other tower subclasses if you have them

public class TowerRenderer {

    private static Texture cannonTex;
    private static Texture pixel;

    public static void load() {
        try {
            cannonTex = new Texture("cannon.png");
        } catch (Exception e) {
            System.err.println("⚠️ Missing cannon.png, using placeholder texture.");
        }
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

    private static Texture textureFor(Tower t) {
        if (t instanceof CannonTower) return cannonTex;
        return null;
    }

    private static float rotationOf(Tower t) {
        try {
            return (float) t.getClass().getMethod("getRotation").invoke(t);
        } catch (Exception ignored) {
            return 0f;
        }
    }

    private static float sizeOf(Tower t) {
        try {
            return (float) t.getClass().getMethod("getRenderSize").invoke(t);
        } catch (Exception ignored) {
            return 40f;
        }
    }

    public static void draw(SpriteBatch batch) {
        for (Tower t : TowerManager.getInstance().getTowers()) {
            Texture tex = textureFor(t);
            if (tex == null) tex = getPixel();

            float size = sizeOf(t);
            float half = size / 2f;
            float rot  = rotationOf(t);

            // 🔁 Adjust rotation 90° counterclockwise (left)
            rot -= 90f;

            batch.draw(
                    tex,
                    t.getX() - half, t.getY() - half, // position
                    half, half,                       // rotation origin
                    size, size,                       // width/height
                    1f, 1f,                           // scale
                    rot,                               // rotation degrees
                    0, 0, tex.getWidth(), tex.getHeight(),
                    false, false
            );
        }
    }

    public static void dispose() {
        if (cannonTex != null) cannonTex.dispose();
        if (pixel != null) pixel.dispose();
    }
}
