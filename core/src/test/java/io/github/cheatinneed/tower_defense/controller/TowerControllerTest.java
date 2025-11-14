package io.github.cheatinneed.tower_defense.controller;

import io.github.cheatinneed.tower_defense.model.towers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD suite that defines the minimal behavior for a very simple TowerController.
 * No mocking framework required; we use tiny fakes for Tower and Enemy.
 */
public class TowerControllerTest {


    private TowerController controller;
    private TowerFactory factory;

    @BeforeEach
    void setup() {
        controller = TowerController.getInstance();
        factory = new TowerFactory();

    }

    @Test
    void PlaceTowerTest(){
        controller.placeTower("cannon", 2,24);
        assertEquals(1, controller.getTowers().size());
    }
    @Test
    void RemoveTowerTest(){
        controller.placeTower("cannon", 2, 24);
        List<Tower> list = controller.getTowers();
        Tower t = list.get(0);
        assertTrue(controller.removeTower(t));
        assertEquals(0,controller.getTowers().size());

    }
    @Test
    void CannonTowerHasShot(){
        controller.placeTower("cannon", 2, 24);
        List<Tower> list = controller.getTowers();
        Tower t = list.get(0);
        //assertEquals("CannonTower fires!",t.attack());
    }
}
