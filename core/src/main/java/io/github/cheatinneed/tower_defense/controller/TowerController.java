package io.github.cheatinneed.tower_defense.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

import io.github.cheatinneed.tower_defense.model.towers.Tower;
import io.github.cheatinneed.tower_defense.model.towers.TowerFactory;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TowerController extends InputAdapter {

    private static TowerController instance;
    public static TowerController getInstance() {
        if (instance == null) instance = new TowerController();
        return instance;
    }

    private final List<Tower> towers = new ArrayList<>(); // retained for your tests
    private final Vector3 tmp = new Vector3();

    private Camera camera;
    private String selectedType = "cannon";
    private int gridSize = 48; // snap size; adjust to your art/map

    private TowerController() {}

    /** Call once from Main.create() after you have a Camera */
    public void init(Camera camera, int gridSize) {
        this.camera = camera;
        if (gridSize > 0) this.gridSize = gridSize;
    }

    /** Programmatic placement (kept for tests) */
    public Tower placeTower(String type, float x, float y) {
        Tower t = TowerFactory.createTower(type, x, y);
        towers.add(t); // keeps test expectations intact
        TowerManager.getInstance().add(t);
        return t;
    }

    public boolean removeTower(Tower t) {
        return towers.remove(t);
    }

    public List<Tower> getTowers() {
        return Collections.unmodifiableList(towers);
    }

    /** Mouse input → world coords → snap to grid → place if free */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (camera == null) return false;

        tmp.set(screenX, screenY, 0);
        camera.unproject(tmp);
        float wx = tmp.x;
        float wy = tmp.y;

        // snap to grid center
        float gx = (float)Math.floor(wx / gridSize) * gridSize + gridSize / 2f;
        float gy = (float)Math.floor(wy / gridSize) * gridSize + gridSize / 2f;

        if (TowerManager.getInstance().isOccupied(gx, gy, gridSize)) {
            return false; // already a tower here
        }

        // Optional: hook in your Map model for buildability:
        // if (!map.canPlaceTower(tileX, tileY)) return false;   // see Map model

        placeTower(selectedType, gx, gy);
        return true;
    }

    public void setSelectedType(String type) {
        this.selectedType = type;
    }
}
