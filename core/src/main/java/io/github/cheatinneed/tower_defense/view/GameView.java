package io.github.cheatinneed.tower_defense.view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.cheatinneed.tower_defense.view.EnemyRenderer;

public class GameView {
    private final SpriteBatch batch;
    private final Viewport viewport;
    private Texture mapTexture;

    private Stage stage;
    private Texture pauseTexture;
    private ImageButton pauseButton;

    public GameView(SpriteBatch batch, Viewport viewport) {
        this.batch = batch;
        this.viewport = viewport;
        initUi();
    }
    private void initUi() {
        stage = new Stage(viewport, batch);
        pauseTexture = new Texture("Buttons/PauseButton.png");
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));

        // størrelse og placering (10 px fra top og højre kant)
        float buttonSize = 80f;
        pauseButton.setSize(buttonSize, buttonSize);
        pauseButton.setPosition(1f,1f);

        stage.addActor(pauseButton);
    }

    public void setMapTexture(Texture mapTexture) {
        this.mapTexture = mapTexture;
    }

    public void render(float dt) {
        // Clear (kan ligge i GameScreen, men fint at gøre her for “view-ansvar”)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Brug samme kamera/projektion som din Viewport
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        // Tegn kortet strakt til viewportens “world size”
        if (mapTexture != null) {
            batch.draw(
                mapTexture,
                0f, 0f,
                (float) viewport.getWorldWidth(),
                (float) viewport.getWorldHeight()
            );
        }

        // Tegn enemies (og evt. andet “world”-lager)
        EnemyRenderer.draw(batch);

        batch.end();
        stage.act(dt);
        stage.draw();
    }
    public Stage getStage() { return stage; }

    public void dispose() {
        if (stage != null) stage.dispose();
        if (pauseTexture != null) pauseTexture.dispose();
    }
    public ImageButton getPauseButton() { return pauseButton; }
}

