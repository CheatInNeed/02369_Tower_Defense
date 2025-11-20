package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public class CustomEnemy extends AbstractEnemy {
    float size = 48;

    public CustomEnemy(Path path, float speed, int health) {
        super(path, speed, health);
        this.moneyYield = 20;
    }

    @Override
    public float getRenderSize() {
        return size;
    }

    @Override
    public EnemyType getType() {
        return EnemyType.CUSTOM;
    }
}
