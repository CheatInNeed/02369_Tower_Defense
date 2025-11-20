package io.github.cheatinneed.tower_defense.model;

public class Player {

    private int lives = 1;
    private int money = 100;

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount){
        if (amount > 0){
            this.money += amount;
        }
    }

    public boolean spendMoney(int cost){
        if(!canAfford(cost)){
            return false;
        }
        money -= cost;
        return true;
    }

    private boolean canAfford(int cost) {
        return cost <= money;
    }
}
