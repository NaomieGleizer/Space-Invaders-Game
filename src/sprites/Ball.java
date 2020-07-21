package sprites;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import game.GameLevel;
import game.GameEnvironment;

import java.awt.Color;

/**
 * A sprites.Ball class.
 *
 * @author Naomie Gleizer
 */
public class Ball implements Sprite {

    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment gameEnvironment;
    private double dt;
    private boolean isEnemy = false;

    /**
     * Construct a ball given a center point, radius and a color.
     *
     * @param center the center of the circle (geometry.Point)
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    /**
     * @return x coordinate of the center
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return y coordinate of the center
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return the radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    // draw the ball on the given surface

    /**
     * @param surface surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * set the velocity.
     *
     * @param v velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * set the velocity when 'dx' and 'dy' are given.
     *
     * @param dx change in position on the x axis
     * @param dy change in position on the y axis
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    /**
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * @param environment game environment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * update the direction of the ball.
     */
    public void moveOneStep() {
        Velocity velocityDT = new Velocity(this.vel.getDx() * dt, this.vel.getDy() * dt);
        Point startTraject = this.center;
        Point endTraject = velocityDT.applyToPoint(this.center);
        Line trajectory = new Line(startTraject, endTraject);
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);
        if (info != null) {
            this.setVelocity(info.collisionObject().hit(this, info.collisionPoint(), this.vel));
            Rectangle collisionRectangle = info.collisionObject().getCollisionRectangle();
            int margeX = 0;
            int margeY = 0;
            if (collisionRectangle.getUpperLeft().getX() == info.collisionPoint().getX()) {
                //bump left side
                margeX = -1;
            } else if (collisionRectangle.getUpperLeft().getX() + collisionRectangle.getWidth()
                    == info.collisionPoint().getX()) {
                //bump right side
                margeX = 1;
            }
            if (collisionRectangle.getUpperLeft().getY() == info.collisionPoint().getY()) {
                //bump top side
                margeY = -1;
            } else if (collisionRectangle.getUpperLeft().getY() + collisionRectangle.getHeight()
                    == info.collisionPoint().getY()) {
                //bump bottom side
                margeY = 1;
            }
            this.center = new Point(info.collisionPoint().getX() + margeX, info.collisionPoint().getY() + margeY);
        } else {
            this.center = velocityDT.applyToPoint(this.center);
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param change (dt)It specifies the amount of seconds passed since the last call
     */
    public void timePassed(double change) {
        this.dt = change;
        this.moveOneStep();
    }

    /**
     * add the ball to the game as a sprite.
     *
     * @param g the game
     */
    public void addToTheGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * remove the ball from the game as a sprite.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    /**
     * @param isEnemyBullet true if the ball is an alien bullet
     */
    public void setStatusIsEnemyBullet(boolean isEnemyBullet) {
        this.isEnemy = isEnemyBullet;
    }

    /**
     * @return true if the ball is an alien bullet
     */
    public boolean isEnemy() {
        return this.isEnemy;
    }
}
