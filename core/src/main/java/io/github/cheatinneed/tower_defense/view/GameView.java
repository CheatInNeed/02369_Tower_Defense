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

    // NYT: fuldskærms pause-menu
    private Texture pauseMenuTexture;

    private ImageButton nextWaveButton;
    private ImageButton pauseButton;
    private Runnable onNextWaveClicked;

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
        // Bane-texture
        mapTexture = new Texture("tiled_map/map trying tmx loader.png");

        // Renderer assets
        EnemyRenderer.load();
        TowerRenderer.load();
        ProjectileRenderer.load();

        // NYT: fuldskærms pause-menu (ligger i core/assets)
        pauseMenuTexture = new Texture("Pause_MENU.png");
    }

    private void initUi() {
        stage = new Stage(viewport, batch);

        hud = new HudRenderer(
            player,
            viewport.getWorldWidth(),
            viewport.getWorldHeight()
        );
        stage.addActor(hud);

        // Knap-teksturer
        pauseTexture = new Texture("Buttons/PauseButton.png");
        nextWaveTexture = new Texture("Buttons/NextWaveButton.png");

        nextWaveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(nextWaveTexture)));
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));

        float buttonSize = 80f;
        float margin = 10f;

        pauseButton.setSize(buttonSize, buttonSize);
        pauseButton.setPosition(margin, margin);

        nextWaveButton.setSize(buttonSize, buttonSize);
        nextWaveButton.setPosition(margin, margin + buttonSize);

        // Pause-knap: toggler paused-flag
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });

        // Next wave-knap
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

        // Tower-popup
        towerPopup = new TowerPopupRenderer(
            TowerController.getInstance(),
            (float) viewport.getWorldWidth(),
            (float) viewport.getWorldHeight()
        );
        stage.addActor(towerPopup);

        TowerController.getInstance()
            .setPlacementListener((gx, gy) -> towerPopup.show());
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
    }

    public void setMapTexture(Texture mapTexture) {
        this.mapTexture = mapTexture;
    }

    public void render(float dt) {
        // Ryd skærmen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Tegn verden
        batch.begin();

        if (mapTexture != null) {
            batch.draw(mapTexture, 0f, 0f);
        }

        TowerRenderer.draw(batch);
        EnemyRenderer.draw(batch);
        ProjectileRenderer.draw(batch);

        // ⭐ NYT: hvis paused → tegn Pause_MENU.png hen over hele verden
        if (paused && pauseMenuTexture != null) {
            batch.draw(
                pauseMenuTexture,
                0f,
                0f,
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
            );
        }

        batch.end();

        // UI (HUD + knapper + tower-popup)
        stage.act(dt);
        hud.update();
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        if (stage != null) stage.dispose();

        if (pauseTexture != null) pauseTexture.dispose();
        if (nextWaveTexture != null) nextWaveTexture.dispose();
        if (mapTexture != null) mapTexture.dispose();

        // NYT: ryd op efter pauseMenuTexture
        if (pauseMenuTexture != null) pauseMenuTexture.dispose();

        EnemyRenderer.dispose();
        TowerRenderer.dispose();
        ProjectileRenderer.dispose();
    }
}
