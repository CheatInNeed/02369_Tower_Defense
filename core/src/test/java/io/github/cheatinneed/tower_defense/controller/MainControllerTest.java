package io.github.cheatinneed.tower_defense.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    void testControllerInitialization() {
        System.out.println("MainControllerTest is running!");
        assertTrue(true);
    }

    @Test
    void testIntentionalFailure() {
        System.out.println("This test will fail intentionally!");
        int expected = 5;
        int actual = 2 + 2; // med vilje forkert
        assertEquals(expected, actual, "Expected 5 but got something else!");
    }
}
