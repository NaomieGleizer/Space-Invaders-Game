package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import game.GameLevel;
import animations.Counter;

import java.awt.Color;

/**
 * Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private Rectangle shape;

    /**
     * Constructor.
     *
     * @param rectangle shape of the block
     * @param score     the score
     */
    public ScoreIndicator(Rectangle rectangle, Counter score) {
        this.shape = rectangle;
        this.score = score;
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

        Point positionText = new Point(this.shape.getTopLine().middle().getX() - 30
                , this.shape.getLeftLine().middle().getY() + 5);

        // draw the score of the game
        d.setColor(Color.BLACK);
        d.drawText((int) positionText.getX(), (int) positionText.getY()
                , "Score: " + String.valueOf(this.score.getValue()), 18);
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
}

