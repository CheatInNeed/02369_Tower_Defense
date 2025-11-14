package io.github.cheatinneed.tower_defense.model;

public class Player {

    private int lives = 1;

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
    }

    public boolean isDead() {
        return lives <= 0;
    }
}
