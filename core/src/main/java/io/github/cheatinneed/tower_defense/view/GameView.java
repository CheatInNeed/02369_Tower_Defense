package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.cheatinneed.tower_defense.controller.TowerController;

public class GameView {
    private final SpriteBatch batch;
    private final Viewport viewport;

    private Texture mapTexture;

    private Stage stage;
    private Texture pauseTexture;
    private Texture nextWaveTexture;
    private ImageButton nextWaveButton;
    private ImageButton pauseButton;
    private Runnable onNextWaveClicked;


    private boolean paused = false;   // 👈 pause-tilstand bor her
    private TowerPopupRenderer towerPopup;

    public GameView(SpriteBatch batch, Viewport viewport) {
        this.batch = batch;
        this.viewport = viewport;

        loadAssets();
        initUi();
    }

    private void loadAssets() {
        // GameView ejer nu sin egen bane-texture
        mapTexture = new Texture("TDmap3.png");

        // Renderer assets bliver også initialiseret her
        EnemyRenderer.load();
        TowerRenderer.load();
        ProjectileRenderer.load();
    }

    private void initUi() {
        stage = new Stage(viewport, batch);

        pauseTexture = new Texture("Buttons/PauseButton.png");
        nextWaveTexture = new Texture("Buttons/NextWaveButton.png");
        nextWaveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextWaveTexture)));
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));

        float buttonSize = 80f;
        float margin = 10f;

        pauseButton.setSize(buttonSize, buttonSize);
        pauseButton.setPosition(margin, margin);
        nextWaveButton.setSize(buttonSize, buttonSize);
        nextWaveButton.setPosition(margin, margin+buttonSize);

        // Klik på pause-knap = toggle local paused-flag
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        nextWaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (onNextWaveClicked != null) {
                    onNextWaveClicked.run();
                }
            }
        });

        stage.addActor(pauseButton);
        stage.addActor(nextWaveButton);
    }
    public void setOnNextWaveClicked(Runnable onNextWaveClicked) {
        this.onNextWaveClicked = onNextWaveClicked;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Stage getStage() {
        return stage;

        // NEW: Popup
        towerPopup = new TowerPopupRenderer(
            TowerController.getInstance(),
            (float) viewport.getWorldWidth(),
            (float) viewport.getWorldHeight()
        );
        stage.addActor(towerPopup);

        // Connect popup to controller
        TowerController.getInstance()
            .setPlacementListener((gx, gy) -> towerPopup.show());
    }


    public void setMapTexture(Texture mapTexture) {
        this.mapTexture = mapTexture;
    }

    public void render(float dt) {
        // Clear skærmen sker her
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Tegn map + towers + enemies + projectiles
        batch.begin();

        if (mapTexture != null) {
            batch.draw(
                mapTexture,
                0f, 0f,
                (float) viewport.getWorldWidth(),
                (float) viewport.getWorldHeight()
            );
        }

        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);

        batch.end();

        // UI
        stage.act(dt);
        stage.draw();
    }
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    public void dispose() {
        if (stage != null) stage.dispose();
        if (pauseTexture != null) pauseTexture.dispose();
        if (mapTexture != null) mapTexture.dispose();
        if (nextWaveTexture != null) nextWaveTexture.dispose();

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
