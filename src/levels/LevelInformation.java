package levels;

import sprites.Block;
import sprites.Sprite;
import sprites.Velocity;

import java.util.List;

/**
 * Level information interface.
 */
public interface LevelInformation {

    /**
     * Set velocities of balls in the level.
     *
     * @param vel velocities of balls
     */
    void setBallsVelocities(List<Velocity> vel);

    /**
     * Set speed of the paddle in the level.
     *
     * @param speed speed of paddle
     */
    void setPaddleSpeed(int speed);

    /**
     * Set width of the paddle in the level.
     *
     * @param width width of the paddle
     */
    void setPaddleWidth(int width);

    /**
     * Set name of level.
     *
     * @param name name of level
     */
    void setLevelName(String name);

    /**
     * Set background of the level.
     *
     * @param background background of the level
     */
    void setBackground(Background background);

    /**
     * Set the getBlocks for the level.
     *
     * @param blocksList getBlocks in the level
     */
    void setBlocks(List<Block> blocksList);

    /**
     * Set the number of getBlocks to remove.
     *
     * @param num number of getBlocks to remove
     */
    void setNumOfBlocksToRemove(int num);

    /**
     * @return the number of balls in the level
     */
    int getNumberOfBalls();

    /**
     * @return the initial velocity of each ball
     */
    List<Velocity> getBallVelocities();

    /**
     * @return the speed of the paddle for the level
     */
    int getPaddleSpeed();

    /**
     * @return the width of the paddle for the level
     */
    int getPaddleWidth();

    /**
     * @return the level name will be displayed at the top of the screen.
     */
    String getLevelName();

    /**
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    // The Blocks that make up this level, each block contains
    // its size, color and location.

    /**
     * @return the Blocks that make up this level, each block contains its size, color and location.
     */
    List<Block> getBlocks();

    /**
     * @return the number of levels that should be removed before the level is considered to be "cleared".
     */
    int getNumberOfBlocksToRemove();
}

