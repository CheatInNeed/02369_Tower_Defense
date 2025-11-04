package io.github.cheatinneed.tower_defense.model.Enemies;

public class EnemyFactory {
    public static Enemy createEnemy(String type, float x, float y, int maxHealth) {
        return switch (type.toLowerCase()) {
            case "basic" -> new BasicEnemy(x, y);
            case "custom" -> new CustomEnemy(x, y, maxHealth);
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }
}
