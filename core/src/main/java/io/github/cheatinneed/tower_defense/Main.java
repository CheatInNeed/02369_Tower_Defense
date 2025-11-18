package io.github.cheatinneed.tower_defense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import io.github.cheatinneed.tower_defense.controller.GameController;
import io.github.cheatinneed.tower_defense.controller.MenuController;
import io.github.cheatinneed.tower_defense.controller.TowerController;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;

import io.github.cheatinneed.tower_defense.view.EnemyRenderer;
import io.github.cheatinneed.tower_defense.view.MainMenuView;
import io.github.cheatinneed.tower_defense.view.ProjectileRenderer;
import io.github.cheatinneed.tower_defense.view.TowerRenderer;
import io.github.cheatinneed.tower_defense.view.GameView;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private MainMenuView mainMenuView;
    private GameView gameView;

    private WaveManager waveManager;
    private GameController gameController;
    private MenuController menuController;

    private Path path;

    private boolean showMenu = true;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(1536, 1024, camera);
        viewport.apply();

        batch = new SpriteBatch();

        // Load renderers
        EnemyRenderer.load();
        TowerRenderer.load();
        ProjectileRenderer.load();

        // Menu
        menuController = new MenuController();
        mainMenuView = new MainMenuView(viewport, menuController);
        Gdx.input.setInputProcessor(mainMenuView.getStage());

        // Build path
        List<PathPoint> designedPoints = new ArrayList<>();
        designedPoints.add(new PathPoint(172, 166));
        designedPoints.add(new PathPoint(814, 166));
        /*designedPoints.add(new PathPoint(814, 166));
        designedPoints.add(new PathPoint(865, 275));
        designedPoints.add(new PathPoint(814, 388));
        designedPoints.add(new PathPoint(166, 399));
        designedPoints.add(new PathPoint(85, 506));
        designedPoints.add(new PathPoint(160, 621));
        designedPoints.add(new PathPoint(810, 621));
        designedPoints.add(new PathPoint(860, 695));
        designedPoints.add(new PathPoint(854, 854));*/

        path = new Path(Path.convertPath(designedPoints, 1024));

        waveManager = new WaveManager(path);
        gameController = new GameController(waveManager);

        gameView = new GameView(batch, viewport);

        // Tower input
        TowerController.getInstance().init(camera, 128);

    }

    @Override
    public void render() {

        if (showMenu) {
            mainMenuView.render();

            // Menu controller actions
            if (menuController.isPlayRequested()) {
                showMenu = false;
                gameView.setPaused(false);              // Når vi går i game, er vi IKKE på pause
                gameController.resume();

                InputMultiplexer multiplexer = new InputMultiplexer(
                    gameView.getStage(),
                    TowerController.getInstance()
                );
                Gdx.input.setInputProcessor(multiplexer);

                menuController.reset();
            }

            if (menuController.isExitRequested()) {
                Gdx.app.exit();
            }

            return;
        }

        float dt = Gdx.graphics.getDeltaTime();
        if (!gameView.isPaused()) {
            gameController.update(dt);
        }

        gameView.render(dt);
        /*
        batch.begin();
        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);
        batch.end();
         */
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (batch != null) batch.dispose();
        if (mainMenuView != null) mainMenuView.dispose();
        /*EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
         */
    }
}
