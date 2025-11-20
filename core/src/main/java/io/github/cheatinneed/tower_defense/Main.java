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
import io.github.cheatinneed.tower_defense.model.path.TmxPathLoader;
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
        viewport = new FitViewport(1920, 1080, camera);
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

        // PATH (fra TMX)
        path = TmxPathLoader.loadPath(
            "tiled_map/map trying tmx loader.tmx",
            "path",
            "main"
        );


        waveManager = new WaveManager(path);
        gameController = new GameController(waveManager);
        TowerController.getInstance().setPlayer(gameController.getPlayer());

        gameView = new GameView(batch, viewport);

        // Tower input
        TowerController.getInstance().init(camera, 64);
        gameView.setOnNextWaveClicked(() -> gameController.startNextWave());

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
    }
    @Override
    public void resize(int width, int height) {
        // Opdater kamera/viewport til den nye skærmstørrelse
        viewport.update(width, height, true);

        // Fortæl views at verden er blevet resized (så Stage også opdateres)
        if (gameView != null) {
            gameView.resize(width, height);
        }
        if (mainMenuView != null) {
            mainMenuView.resize(width, height);
        }
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
