package listeners;

import animations.Counter;
import game.GameLevel;
import sprites.AlienMover;
import sprites.Ball;
import sprites.Block;

/**
 * Remove alien.
 */
public class AlienRemover implements HitListener {
    private GameLevel game;
    private Counter remainingAliens;
    private Counter score;
    private AlienMover alienMover;

    /**
     * Constructor.
     *
     * @param game          the game
     * @param removedAliens a counter which counts the number of getBlocks that remain.
     * @param score         of the game
     * @param alienMover    alien mover
     */
    public AlienRemover(GameLevel game, Counter removedAliens, Counter score, AlienMover alienMover) {
        this.game = game;
        this.remainingAliens = removedAliens;
        this.score = score;
        this.alienMover = alienMover;
    }

    /**
     * Remove Blocks that are hit and reach 0 hit-points and remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit the block which is being hit
     * @param hitter   the ball which hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!hitter.isEnemy()) {
            this.remainingAliens.decrease(1);
            this.score.increase(100);
            beingHit.removeHitListener(this);
            alienMover.removeAlien(beingHit);
            beingHit.removeFromGame(this.game);
            hitter.removeFromGame(this.game);
        }
    }
}
