package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.Map.Tile;
import io.github.cheatinneed.tower_defense.model.Map.TileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void constructorSetsTypeCorrectly() {
        Tile tile = new Tile(2, 3, TileType.BUILDABLE);
        assertEquals(TileType.BUILDABLE, tile.getTileType());
    }

    @Test
    void setTypeChangesType() {
        Tile tile = new Tile(0,0,TileType.BUILDABLE);
        tile.setType(TileType.PATH);  // eller BLOCKED alt efter dit enum
        assertEquals(TileType.PATH, tile.getTileType());
    }
}
