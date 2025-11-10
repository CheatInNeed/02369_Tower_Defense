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
        EnemyRenderer.load();
        batch = new SpriteBatch();
        mapTexture = new Texture("TDmap2.png");

        // Build path
        int mapHeight = 1024; // image height
        List<PathPoint> designedPoints = new ArrayList<>();

        designedPoints.add(new PathPoint(300, 500));   // Start mound
        designedPoints.add(new PathPoint(600, 700));
        designedPoints.add(new PathPoint(550, 480));

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
        EnemyRenderer.dispose();
    }
}
