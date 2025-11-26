package io.github.cheatinneed.tower_defense.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.cheatinneed.tower_defense.model.Player;

public class HudRenderer extends Group {

    private final Player player;

    private Label livesLabel;
    private Label moneyLabel;

    private float worldWidth;
    private float worldHeight;

    public HudRenderer(Player player, float worldWidth, float worldHeight) {
        this.player = player;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        // --- FONT ---
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.5f);

        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        // --- LABELS ---
        livesLabel = new Label("Lives: ?", style);
        moneyLabel = new Label("Gold: ?", style);

        // Initial update + position
        update();
    }

    public void update() {
        float margin = 20f;
        float yTop = worldHeight - 40f;

        // Update text
        livesLabel.setText("Lives: " + player.getLives());
        moneyLabel.setText("Gold: " + player.getMoney());

        // Recalculate widths after updating text
        livesLabel.pack();
        moneyLabel.pack();

        // TOP RIGHT POSITION:
        livesLabel.setPosition(worldWidth - livesLabel.getWidth() - margin, yTop);
        moneyLabel.setPosition(worldWidth - moneyLabel.getWidth() - margin, yTop - 50);

        // Ensure they are added once
        if (livesLabel.getParent() == null) addActor(livesLabel);
        if (moneyLabel.getParent() == null) addActor(moneyLabel);
    }
}
