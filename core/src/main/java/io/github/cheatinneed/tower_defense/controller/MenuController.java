package io.github.cheatinneed.tower_defense.controller;

public class MenuController {

    private boolean playRequested = false;
    private boolean exitRequested = false;

    public void onPlayPressed() {
        playRequested = true;
    }

    public void onExitPressed() {
        exitRequested = true;
    }

    public boolean isPlayRequested() {
        return playRequested;
    }

    public boolean isExitRequested() {
        return exitRequested;
    }

    public void reset() {
        playRequested = false;
        exitRequested = false;
    }
}
