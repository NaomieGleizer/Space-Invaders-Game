package levels;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Draw background for the level.
 */
public class LevelBackgroundSprite implements Sprite {
    private String nameOfLevel;
    private Rectangle rectangle;
    private Background background;

    /**
     * Constructor.
     *
     * @param r    rectangle for the background
     * @param name name of the level
     * @param b    background
     */
    public LevelBackgroundSprite(Rectangle r, String name, Background b) {
        this.rectangle = r;
        this.nameOfLevel = name;
        this.background = b;
    }


    /**
     * this function draws the sprites on the screen.
     *
     * @param d the surface of the game
     */
    public void drawOn(DrawSurface d) {
        background.drawBackground(d, this.rectangle);

        // draw the name of the level
        d.setColor(Color.decode("#95FF95"));
        d.fillRectangle(450, 0, 350, 24);
        d.setColor(Color.BLACK);
        d.drawText(580, 18, this.nameOfLevel, 16);
    }

    /**
     * this function notify the sprite that time has passed.
     *
     * @param dt dt
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
