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

public class GameView {
    private final SpriteBatch batch;
    private final Viewport viewport;

    private Texture mapTexture;

    private Stage stage;
    private Texture pauseTexture;
    private ImageButton pauseButton;

    private boolean paused = false;   // 👈 pause-tilstand bor her

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
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));

        float buttonSize = 80f;
        pauseButton.setSize(buttonSize, buttonSize);
        pauseButton.setPosition(1f, 1f);

        // Klik på pause-knap = toggle local paused-flag
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        stage.addActor(pauseButton);
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Stage getStage() {
        return stage;
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

    public void dispose() {
        if (stage != null) stage.dispose();
        if (pauseTexture != null) pauseTexture.dispose();
        if (mapTexture != null) mapTexture.dispose();

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
