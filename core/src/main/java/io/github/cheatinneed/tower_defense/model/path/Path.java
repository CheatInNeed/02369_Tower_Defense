package io.github.cheatinneed.tower_defense.model.path;

import java.util.ArrayList;
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

    public static PathPoint convertToBottomLeft(float x, float y, int mapHeight) {
        return new PathPoint(x, mapHeight - y);
    }

    public static List<PathPoint> convertPath(List<PathPoint> topLeftPoints, int mapHeight) {
        List<PathPoint> converted = new ArrayList<>();
        for (PathPoint p : topLeftPoints) {
            converted.add(new PathPoint(p.x(), mapHeight - p.y()));
        }
        return converted;
    }
}
