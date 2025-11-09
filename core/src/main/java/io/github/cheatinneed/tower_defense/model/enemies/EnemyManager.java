package io.github.cheatinneed.tower_defense.model.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;


public class EnemyManager {
    private static Texture pixel;
    private static EnemyManager instance;
    public static EnemyManager getInstance() {
        if (instance == null) instance = new EnemyManager();
        return instance;
    }

    private final Array<Enemy> enemies = new Array<>();

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void update() {
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            e.update();

            if (e.isDead()) {
                enemies.removeIndex(i);
            }
        }
    }

    /*public void draw(SpriteBatch batch) {
        for (Enemy e : enemies) {
            // TEMP: draw placeholder until you have textures
            // Replace with sprite rendering when ready
            batch.draw(
                Assets.enemyTexture, // you will add this later
                e.getX(),
                e.getY(),
                16, 16 // enemy size (adjust as needed)
            );
        }
    }*/

    public void draw(SpriteBatch batch) {
        // ensure pixel texture is created
        if (pixel == null) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(Color.WHITE);
            pix.fill();
            pixel = new Texture(pix);
            pix.dispose();
        }

        for (Enemy e : enemies) {
            batch.setColor(1f, 0f, 0f, 1f); // red

            batch.draw(
                pixel,
                e.getX(),
                e.getY(),
                16,
                16
            );

            batch.setColor(1f, 1f, 1f, 1f); // reset
        }
    }


    public boolean isEmpty() {
        return enemies.size == 0;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
