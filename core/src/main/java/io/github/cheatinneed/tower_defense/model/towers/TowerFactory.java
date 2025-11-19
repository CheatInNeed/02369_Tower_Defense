package io.github.cheatinneed.tower_defense.model.towers;

public class TowerFactory {

    public static Tower createTower(String type, float x, float y) {
        return switch (type.toLowerCase()) {
            case "cannon" -> new CannonTower(x, y);
            case "flame" -> new FlameTower(x, y);
            default -> throw new IllegalArgumentException("Unknown tower type: " + type);
        };
    }
}
