package sprites;

import geometry.Point;
import geometry.Rectangle;

/**
 * The Collidable interface is used by objects that can be collided with.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * @param hitter the ball
     * @param collisionPoint  the location of the object
     * @param currentVelocity the velocity of the object
     * @return is the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}