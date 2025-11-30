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

    private Texture pauseButtonTexture;
    private Texture nextWaveTexture;

    // ⭐ Pause menu textures
    private Texture pauseMenuTexture;
    private Texture resumeTexture;
    private Texture quitTexture;

    private Image pauseMenuBackground;
    private ImageButton resumeButton;
    private ImageButton quitButton;

    private ImageButton nextWaveButton;
    private ImageButton pauseButton;

    private Runnable onNextWaveClicked;
    private Runnable onQuitClicked;   // ← NY callback

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

        pauseButtonTexture = new Texture("Buttons/PauseButton.png");
        nextWaveTexture   = new Texture("Buttons/NextWaveButton.png");

        // ⭐ PAUSE-MENU
        pauseMenuTexture = new Texture("Pause_MENU.png");

        resumeTexture = new Texture("Buttons/ResumeButton.png");
        quitTexture   = new Texture("Buttons/QuitButton.png");
    }

    private void initUi() {

        stage = new Stage(viewport, batch);

        hud = new HudRenderer(player, viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.addActor(hud);

        // ====== GAME BUTTONS ======

        float size = 80f;
        float margin = 10f;

        pauseButton = new ImageButton(new TextureRegionDrawable(
            new TextureRegion(pauseButtonTexture)
        ));
        pauseButton.setSize(size, size);
        pauseButton.setPosition(margin, margin);

        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPauseMenu();
            }
        });

        nextWaveButton = new ImageButton(new TextureRegionDrawable(
            new TextureRegion(nextWaveTexture)
        ));
        nextWaveButton.setSize(size, size);
        nextWaveButton.setPosition(margin, margin + size);

        nextWaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onNextWaveClicked != null)
                    onNextWaveClicked.run();
            }
        });

        stage.addActor(pauseButton);
        stage.addActor(nextWaveButton);

        // ====== PAUSE MENU UI ======

        pauseMenuBackground = new Image(pauseMenuTexture);
        pauseMenuBackground.setSize(
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
        pauseMenuBackground.setVisible(false);

        resumeButton = new ImageButton(new TextureRegionDrawable(
            new TextureRegion(resumeTexture)
        ));

        quitButton = new ImageButton(new TextureRegionDrawable(
            new TextureRegion(quitTexture)
        ));

        float buttonWidth = 240;
        float buttonHeight = 80;

        float centerX = viewport.getWorldWidth() / 2f - buttonWidth / 2f;
        float centerY = viewport.getWorldHeight() / 2f;

        resumeButton.setSize(buttonWidth, buttonHeight);
        quitButton.setSize(buttonWidth, buttonHeight);

        resumeButton.setPosition(centerX, centerY + 20);
        quitButton.setPosition(centerX, centerY - 80);

        // Resume → fortsæt spil
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hidePauseMenu();
            }
        });

        // Quit → tilbage til main menu
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onQuitClicked != null)
                    onQuitClicked.run();
            }
        });

        stage.addActor(pauseMenuBackground);
        stage.addActor(resumeButton);
        stage.addActor(quitButton);

        hidePauseMenu();


        // ===== Tower popup =====

        towerPopup = new TowerPopupRenderer(
            TowerController.getInstance(),
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );

        stage.addActor(towerPopup);

        TowerController.getInstance()
            .setPlacementListener((gx, gy) -> towerPopup.show());
    }

    // ======== Pause handling ========

    private void showPauseMenu() {
        paused = true;

        pauseMenuBackground.setVisible(true);
        resumeButton.setVisible(true);
        quitButton.setVisible(true);
    }

    private void hidePauseMenu() {
        paused = false;

        pauseMenuBackground.setVisible(false);
        resumeButton.setVisible(false);
        quitButton.setVisible(false);
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    public void setOnNextWaveClicked(Runnable r) {
        this.onNextWaveClicked = r;
    }

    public void setOnQuitClicked(Runnable r) {
        this.onQuitClicked = r;
    }


    // ====== Render loop ======

    public void render(float dt) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        if (mapTexture != null)
            batch.draw(mapTexture, 0, 0);

        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);

        batch.end();

        // UI
        stage.act(dt);
        hud.update();
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {

        stage.dispose();

        mapTexture.dispose();
        pauseMenuTexture.dispose();
        pauseButtonTexture.dispose();
        nextWaveTexture.dispose();

        resumeTexture.dispose();
        quitTexture.dispose();

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
