package io.github.cheatinneed.tower_defense.model;
import io.github.cheatinneed.tower_defense.model.Map.Map;
import io.github.cheatinneed.tower_defense.model.Map.Tile;
import io.github.cheatinneed.tower_defense.model.Map.TileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void mapIsInitializedWithBuildableTiles() {
        Map map = new Map(5, 5, new int[]{0,0}, new int[]{4,4});

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertEquals(TileType.BUILDABLE, map.getTileTypeAt(x, y));
            }
        }
    }

    @Test
    void canPlaceTowerReturnsTrueOnBuildable() {
        Map map = new Map(3,3,new int[]{0,0},new int[]{2,2});
        assertTrue(map.canPlaceTower(1,1));
    }

    @Test
    void canPlaceTowerReturnsFalseIfTileIsNotBuildable() {
        Map map = new Map(3,3,new int[]{0,0}, new int[]{2,2});
        // vi manipulerer direkte i grid via tile reference
        // (vi har ikke en offentlig setter af tiletype)
        Tile tile = new Tile(1,1,TileType.PATH);
        // inject tile i map grid for testens skyld
        // dette er lovligt fordi grid er skabt på new og vi er i samme package
        map.grid[1][1] = tile;

        assertFalse(map.canPlaceTower(1,1));
    }
}
