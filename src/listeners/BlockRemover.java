package listeners;

import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * a listeners.BlockRemover is in charge of removing getBlocks from the game, as well as keeping count
 * of the number of getBlocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;

    /**
     * Constructor.
     *
     * @param game          the game
     */
    public BlockRemover(GameLevel game) {
        this.game = game;
    }

    /**
     * Remove Blocks that are hit and reach 0 hit-points and remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit the block which is being hit
     * @param hitter   the ball which hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            hitter.removeFromGame(this.game);
        }
    }
}