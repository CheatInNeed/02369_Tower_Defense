package io.github.cheatinneed.tower_defense.view;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuView {
    private final Texture background;
    private final SpriteBatch batch;

    //buttons
    private Stage stage;
    private Texture playTexture, exitTexture;
    private ImageButton playButton, exitButton;


    public MainMenuView(SpriteBatch batch, Texture background) {
        this.batch = batch;
        this.background = background;
    }

    public void initUi(Viewport viewport) {
        if (stage != null) return; // allerede init

        stage = new Stage(viewport, batch);

        // Indlæs knap-teksturer (enkelt — du kan senere skifte til Skin)
        playTexture = new Texture("Buttons/playButton.png");
        exitTexture = new Texture("Buttons/exitButton.png");

        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        playButton.setSize(250, 100);
        exitButton.setSize(250, 100);

        // Placér midt på skærmen
        float centerX = viewport.getWorldWidth() / 2f;
        playButton.setPosition(centerX - playButton.getPrefWidth() / 2f, 300f);
        exitButton.setPosition(centerX - exitButton.getPrefWidth() / 2f, 200f);

        stage.addActor(playButton);
        stage.addActor(exitButton);
    }
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        if (stage != null) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }
    public void resize(int width, int height) {
        if (stage != null) stage.getViewport().update(width, height, true);
    }
    public void dispose() {
        if (stage != null) stage.dispose();
        if (playTexture != null) playTexture.dispose();
        if (exitTexture != null) exitTexture.dispose();
        if (background != null) background.dispose();
    }
    // Getters så du kan koble logik på i Main eller en controller
    public Stage getStage() { return stage; }
    public ImageButton getPlayButton() { return playButton; }
    public ImageButton getExitButton() { return exitButton; }


}

