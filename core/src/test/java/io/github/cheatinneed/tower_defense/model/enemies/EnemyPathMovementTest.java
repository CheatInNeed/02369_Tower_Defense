package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPathMovementTest {

    private Path makePath(float x1, float y1, float x2, float y2) {
        return new Path(List.of(
            new PathPoint(x1, y1),
            new PathPoint(x2, y2)
        ));
    }

    @Test
    void enemyStartsAtFirstPathPoint() {
        Path path = makePath(0, 0, 10, 0);
        Enemy enemy = new BasicEnemy(path);

        assertEquals(0f, enemy.getX(), 0.0001f);
        assertEquals(0f, enemy.getY(), 0.0001f);
    }

    @Test
    void enemyMovesTowardNextPointOnUpdate() {
        Path path = makePath(0, 0, 10, 0);
        Enemy enemy = new CustomEnemy(path, 2f, 5);

        enemy.update();

        assertTrue(enemy.getX() > 0, "Enemy should move along +X direction");
        assertEquals(0f, enemy.getY(), 0.0001f);
    }

    @Test
    void enemyReachesNextPointAndStopsMovingPastIt() {
        Path path = makePath(0, 0, 3, 0);
        Enemy enemy = new CustomEnemy(path, 2f, 5);

        enemy.update();
        enemy.update();

        assertEquals(3f, enemy.getX(), 0.0001f);
        assertEquals(0f, enemy.getY(), 0.0001f);
    }

    @Test
    void enemyDoesNotDieAfterReachingEndOfPath() {
        Path path = makePath(0, 0, 1, 0);
        Enemy enemy = new BasicEnemy(path);

        enemy.update();

        assertFalse(enemy.isDead(), "Enemy should die after reaching end of path");
    }

    @Test
    void enemyMovesAlongDiagonal() {
        Path path = makePath(0, 0, 10, 10);
        Enemy enemy = new CustomEnemy(path, 2f, 10);

        enemy.update();

        assertTrue(enemy.getX() > 0, "Should move positive X");
        assertTrue(enemy.getY() > 0, "Should move positive Y");
        assertEquals(enemy.getX(), enemy.getY(), 0.001f, "Diagonal path should move equally in X and Y");
    }
}
