package io.github.cheatinneed.tower_defense.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void playerStartsWith1Lives() {
        Player player = new Player();
        assertEquals(1, player.getLives());
    }

    @Test
    void losingOneLifeDecrementsLives() {
        Player player = new Player();
        player.loseLife();
        assertEquals(0, player.getLives());
    }

    @Test
    void playerIsNotDeadAtPositiveHealth() {
        Player player = new Player();
        assertFalse(player.isDead());
        player.loseLife();
        assertTrue(player.isDead());
    }

    @Test
    void playerDiesWhenLivesReachZero() {
        Player player = new Player();
        for (int i = 0; i < 10; i++) {
            player.loseLife();
        }
        assertTrue(player.isDead());
    }

    @Test
    void playerDiesWhenLivesBelowZero() {
        Player player = new Player();
        for (int i = 0; i < 15; i++) {
            player.loseLife();
        }
        assertTrue(player.isDead());
    }
}
