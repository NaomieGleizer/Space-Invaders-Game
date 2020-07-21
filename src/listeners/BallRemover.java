package listeners;

import game.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * Remove a ball from the game.
 */
public class BallRemover implements HitListener {
    private GameLevel game;

    /**
     * Constructor.
     *
     * @param game the game
     */
    public BallRemover(GameLevel game) {
        this.game = game;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the object that's being hit
     * @param hitter   the sprites.Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
    }
}
