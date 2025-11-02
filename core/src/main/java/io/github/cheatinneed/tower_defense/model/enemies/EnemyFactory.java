package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;

public class EnemyFactory {

    public static Enemy createEnemy(String type, Path path) {
        return switch (type.toLowerCase()) {
            case "basic" -> new BasicEnemy(path);
            //case "fast" -> new FastEnemy(path);
            //case "tank" -> new TankEnemy(path);
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }
}
