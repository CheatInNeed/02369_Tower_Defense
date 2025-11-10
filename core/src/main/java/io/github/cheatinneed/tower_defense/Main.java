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
        mapTexture = new Texture("TDmap3.png");

        // Build path
        int mapHeight = 1024; // image height
        List<PathPoint> designedPoints = new ArrayList<>();

/*
        designedPoints.add(new PathPoint(100, 100));
        designedPoints.add(new PathPoint(200, 100));
        designedPoints.add(new PathPoint(200, 200));
        designedPoints.add(new PathPoint(100, 200));
        designedPoints.add(new PathPoint(100, 100));
*/
        designedPoints.add(new PathPoint(172, 166));
        designedPoints.add(new PathPoint(814, 166)); ;
        designedPoints.add(new PathPoint(865, 275));
        designedPoints.add(new PathPoint(814, 388)); ;
        designedPoints.add(new PathPoint(166, 399));
        designedPoints.add(new PathPoint(85, 506));
        designedPoints.add(new PathPoint(160, 621));
        designedPoints.add(new PathPoint(810, 621));
        designedPoints.add(new PathPoint(860, 695));
        designedPoints.add(new PathPoint(854, 854));


/*
        designedPoints.add(new PathPoint(867, 280));
        designedPoints.add(new PathPoint(822, 391));
        designedPoints.add(new PathPoint(182, 393));
        designedPoints.add(new PathPoint(98, 436));
        designedPoints.add(new PathPoint(88, 510));
        designedPoints.add(new PathPoint(106, 588));
        designedPoints.add(new PathPoint(183, 624));
        designedPoints.add(new PathPoint(776, 628));
        designedPoints.add(new PathPoint(842, 667));
        designedPoints.add(new PathPoint(853, 821));
*/

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
