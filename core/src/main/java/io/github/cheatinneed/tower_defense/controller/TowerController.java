package io.github.cheatinneed.tower_defense.controller;



import io.github.cheatinneed.tower_defense.model.towers.Tower;
import io.github.cheatinneed.tower_defense.model.towers.TowerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TowerController {

    private final List<Tower> towers = new ArrayList<>();

    private Tower selected;

    public TowerController() {}


    public Tower placeTower(String type,float x,float y){
        //check plaer gold?
        Tower t = TowerFactory.createTower(type,x,y);
        towers.add(t);
        return t;
    }

    public boolean removeTower(Tower t){
        return towers.remove(t);
    }
    public List<Tower> getTowers() {
        return Collections.unmodifiableList(towers);
    }


}
