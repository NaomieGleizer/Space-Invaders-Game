package sprites;

import geometry.Point;

/**
 * sprites.Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Naomie Gleizer
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * construct the velocity, given dx and dy.
     *
     * @param dx change in position on the `x` axis
     * @param dy change in position on the `y` axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * construct the velocity taking an angle an a speed.
     *
     * @param angle angle
     * @param speed speed of the ball
     * @return velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dy = Math.cos(java.lang.Math.toRadians(angle)) * -speed;
        double dx = Math.sin(java.lang.Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p position point
     * @return new position
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }
}