package io.github.cheatinneed.tower_defense.model.enemies;

import io.github.cheatinneed.tower_defense.model.path.Path;
import io.github.cheatinneed.tower_defense.model.path.PathPoint;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EnemyMovementTest {

    @Test
    void enemyShouldMoveAlongPath() {
        // Create a simple path
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(10, 0)
        ));
        Enemy enemy = new BasicEnemy(path);

        // Move the enemy several times
        for (int i = 0; i < 5; i++) {
            enemy.move();
        }

        // The enemy should have moved along the path
        assertTrue(enemy.getX() > 0, "Enemy should have moved along X axis");
        assertEquals(0, enemy.getY(), "Enemy should not move vertically");
    }

    @Test
    void enemyShouldReachEndOfPath() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(5, 0)
        ));
        Enemy enemy = new BasicEnemy(path);

        for (int i = 0; i < 20; i++) {
            enemy.move();
        }

        assertTrue(enemy.reachedEnd(), "Enemy should have reached the end of path");
        assertEquals(5, enemy.getX(), 0.001);
    }

    @Test
    void enemyShouldFollowTurningPath() {
        Path path = new Path(List.of(
            new PathPoint(0, 0),
            new PathPoint(5, 0),
            new PathPoint(5, 5),
            new PathPoint(10, 5)
        ));

        Enemy enemy = new BasicEnemy(path);

        System.out.println("Start: (" + enemy.getX() + ", " + enemy.getY() + ")");
        for (int i = 0; i < 20; i++) {
            enemy.move();
            System.out.printf("Step %d: (%.2f, %.2f)%n", i, enemy.getX(), enemy.getY());
        }
    }
}
