package io.github.cheatinneed.tower_defense.model.Map;

public class Tile {
    final private int x;
    final private int y;
    private TileType type;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public TileType getTileType() {
        return this.type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
}
