package io.github.cheatinneed.tower_defense.model.towers;

public interface Tower {
    void setPosition(float x, float y);
    float getX();
    float getY();

    void upgrade();
    int getLevel();
    int getHealth();
    float getRange();
    int getCost();

    String attack();
}
