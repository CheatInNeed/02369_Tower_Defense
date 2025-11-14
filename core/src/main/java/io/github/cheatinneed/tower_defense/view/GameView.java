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
        float buttonSize = 80f;
        pauseButton.setSize(buttonSize, buttonSize);
        pauseButton.setPosition(1f, 1f);
        stage.addActor(pauseButton);
    }

    public void setMapTexture(Texture mapTexture) {
        this.mapTexture = mapTexture;
    }

    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        if (mapTexture != null) {
            batch.draw(
                mapTexture,
                0f, 0f,
                (float) viewport.getWorldWidth(),
                (float) viewport.getWorldHeight()
            );
        }
        EnemyRenderer.draw(batch);
        batch.end();

        stage.act(dt);
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public void dispose() {
        if (stage != null) stage.dispose();
        if (pauseTexture != null) pauseTexture.dispose();
    }
}
