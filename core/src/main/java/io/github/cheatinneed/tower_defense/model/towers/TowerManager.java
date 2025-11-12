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

    public void add(Tower t) { towers.add(t); }

    public boolean isOccupied(float x, float y, float snap) {
        // basic overlap: same snapped cell
        float sx = (float)Math.floor(x / snap);
        float sy = (float)Math.floor(y / snap);
        for (Tower t : towers) {
            float tx = (float)Math.floor(t.getX() / snap);
            float ty = (float)Math.floor(t.getY() / snap);
            if (tx == sx && ty == sy) return true;
        }
        return false;
    }

    public Array<Tower> getTowers() { return towers; }

    public void update(float dt) {
        // placeholder for tower logic (targeting, cooldowns, etc.)
        // for (Tower t : towers) t.update(dt);  // if you add update to Tower later
    }
}
