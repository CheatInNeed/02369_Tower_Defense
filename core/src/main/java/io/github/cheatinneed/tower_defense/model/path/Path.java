package io.github.cheatinneed.tower_defense.model.path;

import java.util.List;

public class Path {
    private final List<PathPoint> points;

    public Path(List<PathPoint> points) {
        this.points = points;
    }

    public int size() {
        return points.size();
    }

    public PathPoint getPoint(int index) {
        return points.get(index);
    }
}
