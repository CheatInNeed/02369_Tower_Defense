package io.github.cheatinneed.tower_defense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;
import io.github.cheatinneed.tower_defense.view.EnemyRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture mapTexture;

    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private Path path;

    @Override
    public void create() {
        batch = new SpriteBatch();
        mapTexture = new Texture("TDmap2.png");

        // Build path
        int mapHeight = 1024; // image height
        List<PathPoint> designedPoints = new ArrayList<>();

        designedPoints.add(new PathPoint(300, 200));   // Start mound
        designedPoints.add(new PathPoint(900, 200));
        designedPoints.add(new PathPoint(550, 480));

        designedPoints.add(new PathPoint(750, 480));   // Curve down
        designedPoints.add(new PathPoint(750, 670));

        designedPoints.add(new PathPoint(550, 670));   // Sweep left
        designedPoints.add(new PathPoint(350, 670));

        designedPoints.add(new PathPoint(200, 670));   // Curve down again
        designedPoints.add(new PathPoint(200, 870));

        designedPoints.add(new PathPoint(450, 870));   // Go right
        designedPoints.add(new PathPoint(750, 870));
        designedPoints.add(new PathPoint(1050, 870));

        designedPoints.add(new PathPoint(1280, 780));  // Curve up-right
        designedPoints.add(new PathPoint(1400, 600));

        List<PathPoint> gamePoints = Path.convertPath(designedPoints, mapHeight);

        Path path = new Path(gamePoints);

        waveManager = new WaveManager(path);
        enemyManager = EnemyManager.getInstance();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        waveManager.update(dt);
        enemyManager.update();

        draw();
    }

    private void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(mapTexture, 0, 0);
        EnemyRenderer.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
    }
}
