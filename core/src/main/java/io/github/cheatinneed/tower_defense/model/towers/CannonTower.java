package io.github.cheatinneed.tower_defense.model.towers;

public class CannonTower extends AbstractTower implements ProjectileTower {

    public CannonTower(float x, float y) {
        super(x, y, 15, 200);
    }

    @Override
    public void attack() {
        System.out.println("CannonTower fires!");
        //createProjectile();
    }
}
