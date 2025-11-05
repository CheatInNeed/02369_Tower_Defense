package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public class BasicEnemy extends AbstractEnemy {
    public BasicEnemy(Path path) {
        super(path, 1.5f, 50); // speed, health (example values!)
    }
}
