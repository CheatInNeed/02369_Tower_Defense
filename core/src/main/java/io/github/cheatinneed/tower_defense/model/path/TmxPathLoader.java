package io.github.cheatinneed.tower_defense.model.path;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.List;

public final class TmxPathLoader {

    private TmxPathLoader() {}

    public static Path loadPath(String tmxFile,
                                String layerName,
                                String objectName) {

        TiledMap tiledMap = new TmxMapLoader().load(tmxFile);
        try {
            MapLayer layer = tiledMap.getLayers().get(layerName);
            if (layer == null) {
                throw new IllegalArgumentException(
                    "TmxPathLoader: Layer '" + layerName + "' findes ikke i " + tmxFile);
            }

            MapObjects objects = layer.getObjects();
            PolylineMapObject polylineObject = null;

            if (objectName != null && !objectName.isEmpty()) {
                MapObject named = objects.get(objectName);
                if (named instanceof PolylineMapObject) {
                    polylineObject = (PolylineMapObject) named;
                }
            }
            if (polylineObject == null) {
                for (MapObject obj : objects) {
                    if (obj instanceof PolylineMapObject) {
                        polylineObject = (PolylineMapObject) obj;
                        break;
                    }
                }
            }
            if (polylineObject == null) {
                throw new IllegalArgumentException(
                    "TmxPathLoader: Ingen PolylineMapObject fundet på layer '" +
                        layerName + "' i " + tmxFile);
            }

            float[] vertices = polylineObject.getPolyline().getTransformedVertices();

            List<PathPoint> points = new ArrayList<>();
            for (int i = 0; i < vertices.length; i += 2) {
                float x = vertices[i];
                float y = vertices[i + 1];
                // INGEN flip, INGEN mapHeight – brug Tiled-koordinater direkte
                points.add(new PathPoint(x, y));
            }

            return new Path(points);
        } finally {
            tiledMap.dispose();
        }
    }
}
