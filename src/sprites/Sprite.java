package sprites;

import biuoop.DrawSurface;
import game.GameLevel;

/**
 * sprites.Sprite interface.
 */
public interface Sprite {
    /**
     * this function draws the sprites on the screen.
     *
     * @param d the surface of the game
     */
    void drawOn(DrawSurface d);

    /**
     * this function notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call
     */
    void timePassed(double dt);

    /**
     * add the sprite to the game.
     *
     * @param g the game
     */
    void addToTheGame(GameLevel g);
}