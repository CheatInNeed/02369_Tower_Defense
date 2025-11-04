package io.github.cheatinneed.tower_defense;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture mapTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        mapTexture = new Texture("TDmap.png");
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(mapTexture, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
    }
}
