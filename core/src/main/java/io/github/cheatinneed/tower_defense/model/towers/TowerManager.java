package io.github.cheatinneed.tower_defense.model.towers;

import com.badlogic.gdx.utils.Array;

public class TowerManager {
    private static TowerManager instance;
    public static TowerManager getInstance() {
        if (instance == null) instance = new TowerManager();
        return instance;
    }

    private final Array<Tower> towers = new Array<>();

    private TowerManager() {}

    public void add(Tower tower) {
        if (tower != null) towers.add(tower);
    }

    public Array<Tower> getTowers() {
        return towers;
    }

    /** Call this once per frame from your main render loop. */
    public void update(float dt) {
        // update all towers (handles targeting, cooldowns, and shooting)
        for (int i = 0; i < towers.size; i++) {
            towers.get(i).update(dt);
        }
    }

    /** Optional: prevent placing two towers on the same snapped tile. */
    public boolean isOccupied(float worldX, float worldY, float snapSize) {
        float sx = (float)Math.floor(worldX / snapSize);
        float sy = (float)Math.floor(worldY / snapSize);
        for (int i = 0; i < towers.size; i++) {
            Tower t = towers.get(i);
            float tx = (float)Math.floor(t.getX() / snapSize);
            float ty = (float)Math.floor(t.getY() / snapSize);
            if (tx == sx && ty == sy) return true;
        }
        return false;
    }
}
