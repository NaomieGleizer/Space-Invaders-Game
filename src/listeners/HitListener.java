package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * Hit Listener interface.
 */
public interface HitListener {

    /**
     * Remove Blocks that are hit and reach 0 hit-points and remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit the block which is being hit
     * @param hitter   the ball which hits the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}