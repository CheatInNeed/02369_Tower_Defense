package io.github.cheatinneed.tower_defense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.cheatinneed.tower_defense.model.enemies.EnemyManager;
import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import io.github.cheatinneed.tower_defense.model.waves.WaveManager;
import io.github.cheatinneed.tower_defense.view.EnemyRenderer;
import io.github.cheatinneed.tower_defense.view.GameView;
import io.github.cheatinneed.tower_defense.view.MainMenuView;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private static final int viewport_width = 1980;
    private static final int viewport_height = 1080;

    //menu
    private Texture mainMenuTexture;
    private MainMenuView mainMenuView;

    //game
    private Texture mapTexture;
    private WaveManager waveManager;
    private EnemyManager enemyManager;
    private Path path;

    private GameView gameView;

    private boolean showMenu = true; // flag for om vi er i menu eller i spil


    @Override
    public void create() {
        batch = new SpriteBatch();
        // sikre at vinduet er den rigtige størrelse
        viewport = new FitViewport(viewport_width, viewport_height);
        viewport.apply();

        //assets
        mapTexture = new Texture("TDmap2.png");
        mainMenuTexture = new Texture("MainMenu.png");

        //menu view
        mainMenuView = new MainMenuView(batch, mainMenuTexture);
        mainMenuView.initUi(viewport);                  // Stage + knapper
        Gdx.input.setInputProcessor(mainMenuView.getStage());

        //game view
        gameView = new GameView(batch,viewport);
        gameView.setMapTexture(mapTexture);

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

        //knap logik
        mainMenuView.getPlayButton().addListener(evt -> {
            if (mainMenuView.getPlayButton().isPressed()) {
                showMenu = false;
                Gdx.input.setInputProcessor(gameView.getStage()); // skift til spillets input når du er klar
            }
            return false;
        });

        mainMenuView.getExitButton().addListener(evt -> {
            if (mainMenuView.getExitButton().isPressed()) {
                Gdx.app.exit();
            }
            return false;
        });
        gameView.getPauseButton().addListener(evt ->{
            if(gameView.getPauseButton().isPressed()){
                showMenu = true;
                Gdx.input.setInputProcessor(mainMenuView.getStage());
            }
            return false;
        });
    }

    @Override
    public void render() {
        if(showMenu){mainMenuView.render();}
        else{
            float dt = Gdx.graphics.getDeltaTime();
            waveManager.update(dt);
            enemyManager.update();
            gameView.render(dt);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
        if(mainMenuView!=null){mainMenuView.dispose();}
    }
}
