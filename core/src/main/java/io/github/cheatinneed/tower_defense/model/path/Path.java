package io.github.cheatinneed.tower_defense.model.path;

import java.util.List;

public class Path {
    private final List<PathPoint> points;

    public Path(List<PathPoint> points) {
        this.points = List.copyOf(points);
    }

    public List<PathPoint> getPoints() {
        return points;
    }

    public PathPoint getStart() {
        return points.get(0);
    }

    public PathPoint getEnd() {
        return points.get(points.size() - 1);
    }

    public int size() {
        return points.size();
    }

    public PathPoint getPoint(int index) {
        return points.get(index);
    }
}
