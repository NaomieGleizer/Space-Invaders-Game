package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import levels.Background;
import listeners.HitListener;
import listeners.HitNotifier;
import game.GameLevel;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A block class.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle shape;
    private int numOfHitPoints;
    private List<HitListener> hitListeners = new ArrayList<>();
    private Color stroke = null;
    private Background defaultBackground = null;
    private Map<Integer, Background> backgrounds = new HashMap<>();

    /**
     * Constructor.
     *
     * @param x x position
     * @param y y position
     */
    public Block(int x, int y) {
        this.shape.setUpperLeftPoint(new Point(x, y));
    }

    /**
     * @param r         rectangle
     * @param hitPoints number of hit- points
     */
    public Block(Rectangle r, int hitPoints) {
        this.shape = r;
        this.numOfHitPoints = hitPoints;
    }

    /**
     * Constructor.
     *
     * @param original block
     */
    public Block(Block original) {
        this.shape = original.shape;
        this.numOfHitPoints = original.numOfHitPoints;
        this.hitListeners = new ArrayList<>();
        this.stroke = original.stroke;
        this.defaultBackground = original.defaultBackground;
        this.backgrounds = original.backgrounds;
    }

    /**
     * Set the default background.
     *
     * @param background default background
     */
    public void setDefaultBackground(Background background) {
        this.defaultBackground = background;
    }

    /**
     * Set the backgrounds (per hit point).
     *
     * @param bgs backgrounds
     */
    public void setBackgrounds(Map<Integer, Background> bgs) {
        this.backgrounds = bgs;
    }

    /**
     * Set the stroke.
     *
     * @param blockStroke stroke
     */
    public void setStroke(Color blockStroke) {
        this.stroke = blockStroke;
    }

    /**
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * @return the number of hit-points.
     */
    public int getHitPoints() {
        return this.numOfHitPoints;
    }

    /**
     * @param hitter          the ball
     * @param collisionPoint  the collision point of the ball with the object
     * @param currentVelocity the velocity of the object
     * @return is the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // check collision with the sides of the block
        if (this.shape.getLeftLine().isPointOnLine(collisionPoint)
                || this.shape.getRightLine().isPointOnLine(collisionPoint)) {
            dx *= -1;

        }
        // check collision with top or bottom of the block
        if (this.shape.getBottomLine().isPointOnLine(collisionPoint)
                || this.shape.getTopLine().isPointOnLine(collisionPoint)) {
            dy *= -1;

        }
        this.numOfHitPoints--;
        this.notifyHit(hitter);

        return new Velocity(dx, dy);
    }

    /**
     * draw the block and its number of hit points.
     *
     * @param d surface
     */
    public void drawOn(DrawSurface d) {
        Point upperLeftRect = this.shape.getUpperLeft();
        if (this.backgrounds.containsKey(this.getHitPoints())) {
            this.backgrounds.get(this.getHitPoints()).drawBackground(d, this.shape);
        } else if (this.defaultBackground != null) {
            this.defaultBackground.drawBackground(d, this.shape);
        }
        if (stroke != null) {
            d.setColor(stroke);
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
        return; // do nothing for now
    }

    /**
     * add the block to the game as a sprite and a collidable.
     *
     * @param g the game
     */
    public void addToTheGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove a block from the game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl hit listener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl hit listener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify all the listeners about a hit event.
     *
     * @param hitter the ball
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
