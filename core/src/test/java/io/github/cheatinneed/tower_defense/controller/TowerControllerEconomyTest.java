package io.github.cheatinneed.tower_defense.controller;

import io.github.cheatinneed.tower_defense.model.Player;
import io.github.cheatinneed.tower_defense.model.towers.Tower;
import io.github.cheatinneed.tower_defense.model.towers.TowerFactory;
import io.github.cheatinneed.tower_defense.model.towers.TowerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TowerControllerEconomyTest {

    private TowerController controller;
    private Player player;

    @BeforeEach
    void setup() {
        controller = TowerController.getInstance();
        player = new Player();
        controller.setPlayer(player);

        // Clean tower manager between tests
        TowerManager.getInstance().getTowers().clear();
    }

    @Test
    void testTryPlaceTowerSucceedsWhenAffordable() {
        int startingMoney = player.getMoney();
        boolean result = controller.tryPlaceTower("cannon", 100, 100, player);

        assertTrue(result);
        assertEquals(startingMoney - 30, player.getMoney());
        assertEquals(1, TowerManager.getInstance().getTowers().size);
    }

    @Test
    void testTryPlaceTowerFailsWhenNotAffordable() {
        player.spendMoney(99); // leave only 1 gold

        int startingMoney = player.getMoney();
        boolean result = controller.tryPlaceTower("flame", 100, 100, player);

        assertFalse(result);
        assertEquals(startingMoney, player.getMoney());
        assertEquals(0, TowerManager.getInstance().getTowers().size);
    }
}
