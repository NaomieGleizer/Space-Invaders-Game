package levels;

import geometry.Point;
import geometry.Rectangle;
import sprites.Block;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * set the block.
 */
public class SetBlock implements BlockCreator {
    private int width;
    private int height;
    private int hitPoints;
    private Background background;
    private Color stroke;
    private Map<Integer, Background> backgrounds = new HashMap<>();

    /**
     * Constructor, set parameters.
     */
    public SetBlock() {
        this.width = 0;
        this.height = 0;
        this.background = null;
        this.hitPoints = 0;
        this.stroke = null;
    }

    /**
     * Create a block based on a default block.
     *
     * @param defaultBlock default block
     */
    public SetBlock(SetBlock defaultBlock) {
        this.width = defaultBlock.width;
        this.height = defaultBlock.height;
        this.background = defaultBlock.background;
        this.hitPoints = defaultBlock.hitPoints;
        this.stroke = defaultBlock.stroke;
        this.backgrounds = defaultBlock.backgrounds;
    }

    /**
     * Set the width.
     *
     * @param blockWidth width
     */
    public void setWidth(int blockWidth) {
        this.width = blockWidth;
    }

    /**
     * Set the height.
     *
     * @param blockHeight height
     */
    public void setHeight(int blockHeight) {
        this.height = blockHeight;
    }

    /**
     * Set the hit-points.
     *
     * @param points hit-points
     */
    public void setHitPoints(int points) {
        this.hitPoints = points;
    }

    /**
     * Set background of the block.
     *
     * @param b background
     */
    public void setBackground(Background b) {
        this.background = b;
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
     * Set the background this hit point.
     *
     * @param points hit point
     * @param b      background
     */
    public void addBackground(int points, Background b) {
        this.backgrounds.put(points, b);
    }

    /**
     * Create a block at the specified location.
     *
     * @param xpos x position
     * @param ypos y position
     * @return a block at the specified location
     */
    public Block create(int xpos, int ypos) {
        Rectangle rectangle = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        Block block = new Block(rectangle, this.hitPoints);
        if (backgrounds != null) {
            block.setBackgrounds(backgrounds);
        }
        if (background != null) {
            block.setDefaultBackground(background);
        }
        if (stroke != null) {
            block.setStroke(stroke);
        }
        return block;
    }
}
