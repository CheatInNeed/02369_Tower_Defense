// FILE: TowerPopupRenderer.java
package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import io.github.cheatinneed.tower_defense.controller.TowerController;

/**
 * Simple popup drawn with only textures and BitmapFont.
 * No Skin, no uiskin.json.
 */
public class TowerPopupRenderer extends Actor {

    private static Texture pixel;
    private static BitmapFont font;

    private final TowerController towerController;

    private final float worldWidth;
    private final float worldHeight;

    private final float panelWidth = 400f;
    private final float panelHeight = 220f;

    private float panelX, panelY;
    private float btnWidth = 360f;
    private float btnHeight = 50f;

    private float btn1X, btn1Y;
    private float btn2X, btn2Y;
    private float btn3X, btn3Y;

    public TowerPopupRenderer(TowerController controller, float worldWidth, float worldHeight) {
        this.towerController = controller;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        if (pixel == null) {
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(Color.WHITE);
            pix.fill();
            pixel = new Texture(pix);
            pix.dispose();
        }
        if (font == null) {
            font = new BitmapFont();
        }

        setBounds(0, 0, worldWidth, worldHeight);
        setVisible(false);

        layoutButtons();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                if (!isVisible()) return false;

                float worldX = getX() + x;
                float worldY = getY() + y;

                if (hitButton(worldX, worldY, btn1X, btn1Y, btnWidth, btnHeight)) {
                    towerController.setSelectedType("cannon");
                    towerController.confirmAndPlaceSelected();
                    setVisible(false);
                    return true;
                }
                if (hitButton(worldX, worldY, btn2X, btn2Y, btnWidth, btnHeight)) {
                    towerController.setSelectedType("flame");
                    towerController.confirmAndPlaceSelected();
                    setVisible(false);
                    return true;
                }
                if (hitButton(worldX, worldY, btn3X, btn3Y, btnWidth, btnHeight)) {
                    // Placeholder third tower type
                    towerController.setSelectedType("sticky");
                    towerController.confirmAndPlaceSelected();
                    setVisible(false);
                    return true;
                }

                if (!insidePanel(worldX, worldY)) {
                    setVisible(false);
                    return true;
                }

                return false;
            }
        });
    }

    private void layoutButtons() {
        panelX = (worldWidth - panelWidth) / 2f;
        panelY = (worldHeight - panelHeight) / 2f;

        float padding = 10f;

        btn1X = panelX + padding;
        btn2X = panelX + padding;
        btn3X = panelX + padding;

        float startY = panelY + panelHeight - padding - btnHeight;
        btn1Y = startY;
        btn2Y = startY - (btnHeight + padding);
        btn3Y = startY - 2 * (btnHeight + padding);
    }

    private boolean hitButton(float x, float y,
                              float bx, float by, float bw, float bh) {
        return x >= bx && x <= bx + bw && y >= by && y <= by + bh;
    }

    private boolean insidePanel(float x, float y) {
        return x >= panelX && x <= panelX + panelWidth &&
            y >= panelY && y <= panelY + panelHeight;
    }

    public void show() {
        setVisible(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;

        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(pixel, 0, 0, worldWidth, worldHeight);

        batch.setColor(0.15f, 0.15f, 0.15f, 0.95f);
        batch.draw(pixel, panelX, panelY, panelWidth, panelHeight);

        batch.setColor(0.25f, 0.25f, 0.25f, 1f);
        batch.draw(pixel, btn1X, btn1Y, btnWidth, btnHeight);
        batch.draw(pixel, btn2X, btn2Y, btnWidth, btnHeight);
        batch.draw(pixel, btn3X, btn3Y, btnWidth, btnHeight);

        batch.setColor(1, 1, 1, 1);

        font.draw(batch, "Cannon tower", btn1X + 10f, btn1Y + btnHeight - 15f);
        font.draw(batch, "Flame tower",  btn2X + 10f, btn2Y + btnHeight - 15f);
        font.draw(batch, "Sticky Tower", btn3X + 10f, btn3Y + btnHeight - 15f);
    }

    public static void disposeStatic() {
        if (pixel != null) {
            pixel.dispose();
            pixel = null;
        }
        if (font != null) {
            font.dispose();
            font = null;
        }
    }
}
