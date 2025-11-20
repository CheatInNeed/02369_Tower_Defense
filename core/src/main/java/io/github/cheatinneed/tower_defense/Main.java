package io.github.cheatinneed.tower_defense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.cheatinneed.tower_defense.controller.TowerController;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.TmxPathLoader;
import io.github.cheatinneed.tower_defense.model.projectiles.ProjectileManager;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;
import io.github.cheatinneed.tower_defense.view.EnemyRenderer;
import io.github.cheatinneed.tower_defense.view.GameView;
import io.github.cheatinneed.tower_defense.view.MainMenuView;
import io.github.cheatinneed.tower_defense.view.ProjectileRenderer;
import io.github.cheatinneed.tower_defense.view.TowerRenderer;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;

    private FitViewport viewport;
    private OrthographicCamera camera;
    private static final int viewport_width = 1536;
    private static final int viewport_height = 1024;

    // Menu
    private Texture mainMenuTexture;
    private MainMenuView mainMenuView;

    // Game
    private Texture mapTexture;
    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private Path path;

    private GameView gameView;

    private boolean showMenu = true;

    @Override
    public void create() {

        // SPRITEBATCH
        batch = new SpriteBatch();

        // CAMERA + VIEWPORT
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewport_width, viewport_height, camera);
        viewport.apply();

        // RENDERERS
        EnemyRenderer.load();
        TowerRenderer.load();
        ProjectileRenderer.load();

        // ASSETS
        mapTexture = new Texture("tiled_map/map trying tmx loader.png");
        mainMenuTexture = new Texture("MainMenu.png");

        // MENU VIEW
        mainMenuView = new MainMenuView(batch, mainMenuTexture);
        mainMenuView.initUi(viewport);
        Gdx.input.setInputProcessor(mainMenuView.getStage());

        // GAME VIEW
        gameView = new GameView(batch, viewport);
        gameView.setMapTexture(mapTexture);  // Tegnes i original størrelse

        // PATH (fra TMX)
        path = TmxPathLoader.loadPath(
            "tiled_map/map trying tmx loader.tmx",
            "path",
            "main"
        );

        // WAVE + ENEMY MANAGERS
        waveManager = new WaveManager(path);
        enemyManager = EnemyManager.getInstance();

        // TOWER INPUT
        TowerController.getInstance().init(camera, 128);

        // BUTTON HANDLERS
        mainMenuView.getPlayButton().addListener(evt -> {
            if (mainMenuView.getPlayButton().isPressed()) {

                showMenu = false;

                InputMultiplexer multiplexer = new InputMultiplexer(
                    gameView.getStage(),
                    TowerController.getInstance()
                );

                Gdx.input.setInputProcessor(multiplexer);
            }
            return false;
        });

        mainMenuView.getExitButton().addListener(evt -> {
            if (mainMenuView.getExitButton().isPressed()) {
                Gdx.app.exit();
            }
            return false;
        });

        gameView.getPauseButton().addListener(evt -> {
            if (gameView.getPauseButton().isPressed()) {

                showMenu = true;
                Gdx.input.setInputProcessor(mainMenuView.getStage());
            }
            return false;
        });
    }


    @Override
    public void render() {

        if (showMenu) {
            mainMenuView.render();
            return;
        }

        float dt = Gdx.graphics.getDeltaTime();

        // GAME UPDATES
        waveManager.update(dt);
        enemyManager.update();
        TowerManager.getInstance().update(dt);
        ProjectileManager.getInstance().update(dt);

        // CLEAR
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // DRAW GAMEVIEW (baggrund + UI)
        gameView.render(dt);

        // DRAW RENDERERS (fjender, towers, projektiler)
        batch.begin();
        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();

        if (mainMenuView != null) {
            mainMenuView.dispose();
        }

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
