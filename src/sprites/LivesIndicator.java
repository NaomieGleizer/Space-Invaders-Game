package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import animations.Counter;
import game.GameLevel;
import listeners.HitListener;

import java.awt.Color;

/**
 * Lives indicator.
 */
public class LivesIndicator implements Sprite, HitListener {

    private Rectangle shape;
    private Counter numOfLives;

    /**
     * Constructor.
     *
     * @param rectangle  shape of this indicator
     * @param value      number of lives remained in the game
     */
    public LivesIndicator(Rectangle rectangle, Counter value) {
        this.shape = rectangle;
        this.numOfLives = value;
    }

    /**
     * @return the shape
     */
    public Rectangle getShape() {
        return this.shape;
    }

    /**
     * this function draws the sprites on the screen.
     *
     * @param d the surface of the game
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.shape.getColor());
        Point upperLeftRect = this.shape.getUpperLeft();
        d.fillRectangle((int) upperLeftRect.getX(), (int) upperLeftRect.getY(), (int) this.shape.getWidth()
                , (int) this.shape.getHeight());

        Point positionText = new Point(this.shape.getTopLine().middle().getX() - 25
                , this.shape.getLeftLine().middle().getY() + 5);

        // draw the score of the game
        d.setColor(Color.BLACK);
        d.drawText((int) positionText.getX(), (int) positionText.getY()
                , "Lives: " + String.valueOf(this.numOfLives.getValue()), 16);
    }

    /**
     * this function notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {

    }

    /**
     * add the score block to the game as a sprite.
     *
     * @param g the game
     */
    public void addToTheGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * notify the listener when there is a hit.
     *
     * @param beingHit the block which is being hit
     * @param hitter   the ball which hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {

    }
}
