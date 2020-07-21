package sprites;

import geometry.Point;

/**
 * This class contains the info about the collision- point at which the collision occurs and
 * collidable object involved in the collision.
 */
public class CollisionInfo {
    private Point collision;
    private Collidable object;

    /**
     * @param collision point at which the collision occurs
     * @param object    collidable object involved in the collision
     */
    public CollisionInfo(Point collision, Collidable object) {
        this.object = object;
        this.collision = collision;
    }

    /**
     * @return point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collision;
    }


    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.object;
    }
}