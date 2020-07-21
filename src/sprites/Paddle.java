package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import game.GameLevel;
import animations.Counter;
import levels.Background;
import levels.BlocksDefinitionReader;
import levels.ImageBackground;

import java.awt.Color;

/**
 * A paddle class.
 */
public class Paddle implements Collidable, Sprite {
    private Rectangle shape;
    private biuoop.KeyboardSensor keyboard;
    private int frameStart;
    private int frameEnd;
    private int speed;
    private boolean speedSet = false;
    private Counter numOfLives;
    private GameLevel game;
    private Background background = null;

    /**
     * paddle constructor.
     *
     * @param keyboard   keyboard
     * @param start      the leftest point of the frame where the paddle can access
     * @param end        the rightest point of the frame where the paddle can access
     * @param speed      the speed of the paddle
     * @param numOfLives num of lives in game
     * @param gameLevel  the game
     */
    public Paddle(KeyboardSensor keyboard, int start, int end, int speed, Counter numOfLives, GameLevel gameLevel) {
        this.keyboard = keyboard;
        this.frameStart = start;
        this.frameEnd = end;
        this.speed = speed;
        this.numOfLives = numOfLives;
        this.game = gameLevel;
        this.setBackground(new ImageBackground(BlocksDefinitionReader.findImage("block_images/baseShip.png")));

    }

    /**
     * set the rectangle of the paddle when given a rectangle.
     *
     * @param rectangle a rectangle
     */
    public void setShape(Rectangle rectangle) {
        this.shape = rectangle;
    }

    /**
     * move the paddle to the left.
     */
    public void moveLeft() {
        Point upperLeftPoint = this.getCollisionRectangle().getUpperLeft();
        if (upperLeftPoint.getX() - this.speed >= this.frameStart) {
            Point newUpperLeftPoint = new Point(upperLeftPoint.getX() - this.speed, upperLeftPoint.getY());
            this.getCollisionRectangle().setUpperLeftPoint(newUpperLeftPoint);
        }
    }

    /**
     * move the paddle to the right.
     */
    public void moveRight() {
        Point upperLeftPoint = this.getCollisionRectangle().getUpperLeft();
        if (upperLeftPoint.getX() + this.getCollisionRectangle().getWidth() + this.speed <= this.frameEnd) {
            Point newUpperLeftPoint = new Point(upperLeftPoint.getX() + this.speed, upperLeftPoint.getY());
            this.getCollisionRectangle().setUpperLeftPoint(newUpperLeftPoint);
        }
    }

    /**
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * @param hitter          the ball
     * @param collisionPoint  the location of the object
     * @param currentVelocity the velocity of the object
     * @return is the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.numOfLives.decrease(1);
        hitter.removeFromGame(game);
        this.game.setPaddleHit(true);
        return currentVelocity;
    }

    /**
     * Set the background.
     *
     * @param b background
     */
    public void setBackground(Background b) {
        this.background = b;
    }

    /**
     * draw the paddle to the screen.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        if (background != null) {
            background.drawBackground(d, this.shape);
        } else {
            d.setColor(this.shape.getColor());
            Point upperLeftRect = this.shape.getUpperLeft();
            d.fillRectangle((int) upperLeftRect.getX(), (int) upperLeftRect.getY(), (int) this.shape.getWidth()
                    , (int) this.shape.getHeight());

            d.setColor(Color.BLACK);
            d.drawRectangle((int) upperLeftRect.getX(), (int) upperLeftRect.getY(), (int) this.shape.getWidth()
                    , (int) this.shape.getHeight());
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
        if (!speedSet) {
            this.speed *= dt;
            speedSet = true;
        }
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * add paddle to the game as a collidable and a sprite.
     *
     * @param g the game
     */
    public void addToTheGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
