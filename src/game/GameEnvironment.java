package game;

import geometry.Line;
import geometry.Point;
import sprites.Collidable;
import sprites.CollisionInfo;

import java.util.ArrayList;

/**
 * The environment of the game.
 */
public class GameEnvironment {
    private java.util.List<Collidable> collidable = new ArrayList<>();

    /**
     * add the given collidable to the environment.
     *
     * @param c a collidable
     */
    public void addCollidable(Collidable c) {
        this.collidable.add(c);
    }


    /**
     * remove the given collidable from the environment.
     *
     * @param c a collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidable.remove(c);
    }

    /**
     * @param trajectory the trajectory of an object moving from line.start() to line.end()
     * @return null if this object will not collide with any of the collidables in this collection.
     * Otherwise, return the information about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double minDistance = trajectory.length();
        Point collisionPoint = null;
        Collidable collisionObject = null;
        for (int i = 0; i < this.collidable.size(); i++) {
            Point point = trajectory.closestIntersectionToStartOfLine(
                    this.collidable.get(i).getCollisionRectangle());
            // find the closest collision point
            if (point != null) {
                double distance = trajectory.start().distance(point);
                if (minDistance > distance) {
                    minDistance = distance;
                    collisionPoint = point;
                    collisionObject = this.collidable.get(i);
                }
            }
        }
        if (collisionPoint != null) {
            return new CollisionInfo(collisionPoint, collisionObject);

        }
        // the object will not collide
        return null;
    }

}
