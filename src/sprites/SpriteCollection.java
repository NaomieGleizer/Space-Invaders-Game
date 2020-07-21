package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;

/**
 * The collection of all the sprites in the game.
 */
public class SpriteCollection {
    private java.util.List<Sprite> spriteCollection = new ArrayList<>();

    /**
     * add this sprite to the game.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.add(s);
    }

    /**
     * remove this sprite from the game.
     *
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
            this.spriteCollection.remove(s);
    }


    /**
     * call timePassed() on all sprites.
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite sprite = this.spriteCollection.get(i);
            sprite.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d the surface of the game.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.spriteCollection.size(); i++) {
            Sprite sprite = this.spriteCollection.get(i);
            sprite.drawOn(d);
        }
    }
}
