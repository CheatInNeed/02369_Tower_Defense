package io.github.cheatinneed.tower_defense.model.towers;

public class CannonTower extends AbstractTower implements ProjectileTower {

    public CannonTower(float x, float y) {
        super(x, y, 15, 200, 300);
    }

    @Override
    public String attack() {
        return "CannonTower fires!";
        //createProjectile();
    }
}
