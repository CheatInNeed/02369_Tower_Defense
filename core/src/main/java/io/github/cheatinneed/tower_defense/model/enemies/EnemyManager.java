package io.github.cheatinneed.tower_defense.model.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import io.github.cheatinneed.tower_defense.model.Player;


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

    public void update(Player player) {
        for (int i = enemies.size - 1; i >= 0; i--) {
            Enemy e = enemies.get(i);
            e.update();

            if (e.hasReachedGoal()) {
                System.out.println("Enemy reached goal!");
                player.loseLife();
                enemies.removeIndex(i);
                continue;
            }

            if (e.isDead()) {
                System.out.println("Enemy is dead!");
                player.addMoney(e.getMoneyYield());
                enemies.removeIndex(i);
                continue;
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



    public boolean isEmpty() {
        return enemies.size == 0;
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
}
