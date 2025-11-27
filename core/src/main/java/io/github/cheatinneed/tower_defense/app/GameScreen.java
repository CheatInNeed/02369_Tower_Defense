package io.github.cheatinneed.tower_defense.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.cheatinneed.tower_defense.model.GameState;

public class GameScreen implements Screen, InputProcessor {

    private GameState gameState = GameState.RUNNING;

    private Stage uiStage;
    private Skin skin;
    private Table pauseTable;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Texture pauseBackground;

    public GameScreen() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);

        uiStage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();

        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        pauseTable.add(resumeButton).pad(10).row();
        pauseTable.add(quitButton).pad(10);

        pauseTable.setVisible(false);
        uiStage.addActor(pauseTable);

        // Load pause background image
        pauseBackground = new Texture(Gdx.files.internal("Pause_MENU.png"));

        // Button listeners
        resumeButton.addListener(event -> {
            gameState = GameState.RUNNING;
            pauseTable.setVisible(false);
            return true;
        });

        quitButton.addListener(event -> {
            Gdx.app.exit();
            return true;
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update game logic kun hvis RUNNING
        if (gameState == GameState.RUNNING) {
            waveManager.update(delta);
            enemyManager.update(delta);
            projectileManager.update(delta);
            towerManager.update(delta);
        }

        // Render spil elementer
        mapRenderer.render();
        enemyRenderer.render();
        towerRenderer.render();

        // Pause menu overlay og billede
        if (gameState == GameState.PAUSED) {
            // Dark semi-transparent overlay
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.6f);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();

            // Pause menu image centreret
            batch.begin();
            batch.draw(pauseBackground,
                Gdx.graphics.getWidth()/2f - pauseBackground.getWidth()/2f,
                Gdx.graphics.getHeight()/2f - pauseBackground.getHeight()/2f);
            batch.end();

            pauseTable.setVisible(true);
        }

        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            gameState = (gameState == GameState.RUNNING)
                ? GameState.PAUSED
                : GameState.RUNNING;
            return true;
        }
        return false;
    }

    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int x, int y, int p, int b) { return false; }
    @Override public boolean touchUp(int x, int y, int p, int b) { return false; }
    @Override public boolean touchDragged(int x, int y, int p) { return false; }
    @Override public boolean mouseMoved(int x, int y) { return false; }
    @Override public boolean scrolled(float x, float y) { return false; }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        uiStage.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        skin.dispose();
        pauseBackground.dispose();
    }
}
