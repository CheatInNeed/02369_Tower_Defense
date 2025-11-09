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

import java.util.ArrayList;
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
        mapTexture = new Texture("TDmap.png");

        // Build path
        List<PathPoint> pathPoints = new ArrayList<>();
        pathPoints.add(new PathPoint(100, 100));
        pathPoints.add(new PathPoint(200, 100));
        pathPoints.add(new PathPoint(300, 200));
        pathPoints.add(new PathPoint(400, 300));

        Path path = new Path(pathPoints);

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
        enemyManager.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
    }
}
