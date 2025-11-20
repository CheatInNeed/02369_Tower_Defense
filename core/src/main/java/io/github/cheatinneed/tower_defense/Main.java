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

        // WAVE + ENEMY MANAGERS
        waveManager = new WaveManager(path);
        gameController = new GameController(waveManager);

        // TOWER INPUT
        TowerController.getInstance().init(camera, 64);

        // BUTTON HANDLERS
        mainMenuView.getPlayButton().addListener(evt -> {
            if (mainMenuView.getPlayButton().isPressed()) {

        gameView = new GameView(batch, viewport);

        // Tower input
        TowerController.getInstance().init(camera, 128);
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

        gameView.getPauseButton().addListener(evt -> {
            if (gameView.getPauseButton().isPressed()) {

                showMenu = true;
                Gdx.input.setInputProcessor(mainMenuView.getStage());
            if (menuController.isExitRequested()) {
                Gdx.app.exit();
            }

            return;
        }

        float dt = Gdx.graphics.getDeltaTime();
        if (!gameView.isPaused()) {
            gameController.update(dt);
        }

        // GAME UPDATES
        waveManager.update(dt);
        enemyManager.update();
        TowerManager.getInstance().update(dt);
        ProjectileManager.getInstance().update(dt);

        // CLEAR
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // DRAW GAMEVIEW (baggrund + UI)
        gameView.render(dt);
    }
    @Override
    public void resize(int width, int height) {
        // Opdater kamera/viewport til den nye skærmstørrelse
        viewport.update(width, height, true);

        // DRAW RENDERERS (fjender, towers, projektiler)
        batch.begin();
        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);
        batch.end();
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
