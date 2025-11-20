package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public class BasicEnemy extends AbstractEnemy {
    float size = 48;

    public BasicEnemy(Path path) {
        super(path, 1.5f, 50); // speed, health (example values!)
        this.moneyYield = 10;
    }

    @Override
    public float getRenderSize() {
        return size;
    }

    @Override
    public EnemyType getType() {
        return EnemyType.BASIC;
    }
}
