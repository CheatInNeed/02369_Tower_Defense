package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.cheatinneed.tower_defense.controller.TowerController;
import io.github.cheatinneed.tower_defense.model.Player;

public class GameView {

    private final SpriteBatch batch;
    private final Viewport viewport;
    private final Player player;

    private Texture mapTexture;
    private HudRenderer hud;

    private Stage stage;

    private Texture pauseTexture;
    private Texture nextWaveTexture;

    private ImageButton pauseButton;
    private ImageButton nextWaveButton;

    // Pause menu
    private Texture pauseMenuTexture;
    private Image pauseMenuImage;

    private ImageButton resumeZone;
    private ImageButton quitZone;

    private Runnable onNextWaveClicked;
    private Runnable onQuitClicked;

    private boolean paused = false;

    private TowerPopupRenderer towerPopup;

    public GameView(SpriteBatch batch, Viewport viewport, Player player) {
        this.batch = batch;
        this.viewport = viewport;
        this.player = player;

        loadAssets();
        initUi();
    }

    private void loadAssets() {

        mapTexture = new Texture("tiled_map/map trying tmx loader.png");

        EnemyRenderer.load();
        TowerRenderer.load();
        ProjectileRenderer.load();

        pauseTexture = new Texture("Buttons/PauseButton.png");
        nextWaveTexture = new Texture("Buttons/NextWaveButton.png");

        pauseMenuTexture = new Texture("Pause_MENU.png");
    }

    private ImageButton createButton(Texture texture) {
        return new ImageButton(
            new TextureRegionDrawable(
                new TextureRegion(texture)
            )
        );
    }

    private void initUi() {

        stage = new Stage(viewport, batch);

        hud = new HudRenderer(
            player,
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
        stage.addActor(hud);

        //---------------- GAME BUTTONS ----------------

        pauseButton = createButton(pauseTexture);
        pauseButton.setSize(80, 80);
        pauseButton.setPosition(10, 10);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseMenu();
            }
        });
        stage.addActor(pauseButton);

        nextWaveButton = createButton(nextWaveTexture);
        nextWaveButton.setSize(80, 80);
        nextWaveButton.setPosition(10, 100);
        nextWaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(onNextWaveClicked != null) onNextWaveClicked.run();
            }
        });
        stage.addActor(nextWaveButton);

        //---------------- PAUSE MENU ----------------

        pauseMenuImage = new Image(pauseMenuTexture);
        pauseMenuImage.setSize(
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
        pauseMenuImage.setPosition(0,0);
        pauseMenuImage.setVisible(false);
        stage.addActor(pauseMenuImage);

        //---------------- CLICK ZONES ----------------

        float vw = viewport.getWorldWidth();
        float vh = viewport.getWorldHeight();

        // Disse tal passer korrekt til DIT pause-billede
        float zoneWidth  = vw * 0.75f;
        float zoneHeight = vh * 0.25f;

        float centerX = (vw - zoneWidth) / 2f;

        // Resume – øverste knap
        resumeZone = createButton(pauseTexture);
        resumeZone.setBounds(
            centerX,
            vh * 0.39f,
            zoneWidth,
            zoneHeight
        );
        resumeZone.getImage().setVisible(false);
        resumeZone.setVisible(false);
        resumeZone.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hidePauseMenu();
            }
        });
        stage.addActor(resumeZone);

        // Quit – nederste knap
        quitZone = createButton(pauseTexture);
        quitZone.setBounds(
            centerX,
            vh * 0.22f,
            zoneWidth,
            zoneHeight
        );
        quitZone.getImage().setVisible(false);
        quitZone.setVisible(false);
        quitZone.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if(onQuitClicked != null) onQuitClicked.run();
            }
        });
        stage.addActor(quitZone);

        //---------------- TOWER POPUP ----------------
        towerPopup = new TowerPopupRenderer(
            TowerController.getInstance(),
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
        stage.addActor(towerPopup);

        TowerController.getInstance()
            .setPlacementListener((gx,gy) -> towerPopup.show());
    }

    //---------------- PAUSE CONTROL ----------------

    private void showPauseMenu() {
        paused = true;
        pauseMenuImage.setVisible(true);
        resumeZone.setVisible(true);
        quitZone.setVisible(true);
    }

    private void hidePauseMenu() {
        paused = false;
        pauseMenuImage.setVisible(false);
        resumeZone.setVisible(false);
        quitZone.setVisible(false);
    }

    public void setPaused(boolean value) {
        if(value) showPauseMenu();
        else hidePauseMenu();
    }

    public boolean isPaused() {
        return paused;
    }

    //---------------- CALLBACKS ----------------

    public void setOnNextWaveClicked(Runnable r) {
        onNextWaveClicked = r;
    }

    public void setOnQuitClicked(Runnable r) {
        onQuitClicked = r;
    }

    public Stage getStage() {
        return stage;
    }

    //---------------- RENDER ----------------

    public void render(float dt) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(mapTexture, 0, 0);
        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);
        batch.end();

        stage.act(dt);
        hud.update();
        stage.draw();
    }

    public void resize(int width,int height) {
        viewport.update(width,height,true);

        pauseMenuImage.setSize(
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
    }

    public void dispose() {
        stage.dispose();
        pauseMenuTexture.dispose();
        pauseTexture.dispose();
        nextWaveTexture.dispose();
        mapTexture.dispose();

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
