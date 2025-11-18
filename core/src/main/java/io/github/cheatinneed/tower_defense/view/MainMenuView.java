package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.cheatinneed.tower_defense.controller.MenuController;

public class MainMenuView {

    private final Viewport viewport;
    private final MenuController menuController;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture exitTexture;

    private ImageButton playButton;
    private ImageButton exitButton;

    public MainMenuView(Viewport viewport, MenuController menuController) {
        this.viewport = viewport;
        this.menuController = menuController;

        initUi();
    }

    private void initUi() {
        if (stage != null) return; // allerede init

        // Stage laver selv sin SpriteBatch
        stage = new Stage(viewport);

        // --- Textures ejes af MainMenuView ---
        backgroundTexture = new Texture("MainMenu.png");
        playTexture = new Texture("Buttons/playButton.png");
        exitTexture = new Texture("Buttons/exitButton.png");

        // Baggrund som Image-actor
        Image backgroundImage = new Image(new TextureRegionDrawable(backgroundTexture));
        backgroundImage.setFillParent(true); // fyld hele viewports world area
        stage.addActor(backgroundImage);

        // Knapper
        playButton = new ImageButton(new TextureRegionDrawable(playTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));

        playButton.setSize(250, 100);
        exitButton.setSize(250, 100);

        float centerX = viewport.getWorldWidth() / 2f;
        playButton.setPosition(centerX - playButton.getPrefWidth() / 2f, 300f);
        exitButton.setPosition(centerX - exitButton.getPrefWidth() / 2f, 200f);

        stage.addActor(playButton);
        stage.addActor(exitButton);

        // Listeners – logikken går via MenuController
        playButton.addListener(event -> {
            if (playButton.isPressed()) {
                menuController.onPlayPressed();
            }
            return false;
        });

        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                menuController.onExitPressed();
            }
            return false;
        });
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        stage.act(dt);
        stage.draw();
    }

    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    public void dispose() {
        if (stage != null) stage.dispose();
        if (playTexture != null) playTexture.dispose();
        if (exitTexture != null) exitTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }

    // Getters – til input routing i Main
    public Stage getStage() { return stage; }
    public ImageButton getPlayButton() { return playButton; }
    public ImageButton getExitButton() { return exitButton; }
}
