package levels;

import sprites.Block;

/**
 * Block creator interface.
 */
public interface BlockCreator {

    /**
     * Create a block at the specified location.
     *
     * @param xpos x position
     * @param ypos y position
     * @return a block at the specified location
     */
    Block create(int xpos, int ypos);
}