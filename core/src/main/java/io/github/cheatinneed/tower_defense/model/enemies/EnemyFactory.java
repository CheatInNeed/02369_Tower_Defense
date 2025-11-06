package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public class EnemyFactory {

    public static Enemy createEnemy(String type, Path path) {
        return switch (type.toLowerCase()) {
            case "basic" -> new BasicEnemy(path);
            case "custom" -> new CustomEnemy(path, 2.5f, 120);
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }
}
