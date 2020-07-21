package levels;

import geometry.Point;
import geometry.Rectangle;
import sprites.Block;
import sprites.Velocity;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Level settings.
 */
public class CreateLevel implements LevelInformation {
    private int frameWidth;
    private int frameHeight;
    private List<Velocity> velocities = new ArrayList<>();
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Background background;
    private List<Block> blocks;
    private int numOfBlocksToRemove;

    /**
     * Constructor.
     *
     * @param width  width of frame
     * @param height height of frame
     */
    public CreateLevel(int width, int height) {
        this.frameWidth = width;
        this.frameHeight = height;
    }

    /**
     * Set velocities of balls in the level.
     *
     * @param vel velocities of balls
     */
    public void setBallsVelocities(List<Velocity> vel) {
        this.velocities = vel;
    }

    /**
     * Set speed of the paddle in the level.
     *
     * @param speed speed of paddle
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;

    }

    /**
     * Set width of the paddle in the level.
     *
     * @param width width of the paddle
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;

    }

    /**
     * Set name of level.
     *
     * @param name name of level
     */
    public void setLevelName(String name) {
        this.levelName = name;

    }

    /**
     * set background of the level.
     *
     * @param b background of the level
     */
    public void setBackground(Background b) {
        this.background = b;

    }

    /**
     * Set the getBlocks for the level.
     *
     * @param blocksList getBlocks in the level
     */
    public void setBlocks(List<Block> blocksList) {
        this.blocks = blocksList;
    }

    /**
     * Set the number of getBlocks to remove.
     *
     * @param num number of getBlocks to remove
     */
    public void setNumOfBlocksToRemove(int num) {
        this.numOfBlocksToRemove = num;
    }

    /**
     * @return the number of balls in the level
     */
    public int getNumberOfBalls() {
        return this.velocities.size();
    }

    /**
     * @return the initial velocity of each ball
     */
    public List<Velocity> getBallVelocities() {
        return this.velocities;
    }

    /**
     * @return the speed of the paddle for the level
     */
    public int getPaddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return the width of the paddle for the level
     */
    public int getPaddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return the level name will be displayed at the top of the screen.
     */
    public String getLevelName() {
        return this.levelName;
    }

    /**
     * @return a sprite with the background of the level
     */
    public Sprite getBackground() {
        Rectangle r = new Rectangle(new Point(0, 0), this.frameWidth, this.frameHeight);

        return new LevelBackgroundSprite(r, getLevelName(), background);
    }

    /**
     * @return the Blocks that make up this level, each block contains its size, color and location.
     */
    public List<Block> getBlocks() {
        return this.blocks;
    }

    /**
     * @return the number of Blocks that should be removed before the level is considered to be "cleared".
     */
    public int getNumberOfBlocksToRemove() {
        return this.numOfBlocksToRemove;
    }
}
