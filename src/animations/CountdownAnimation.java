package animations;

import biuoop.DrawSurface;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * Countdown from 3 to 1, which will show up at the beginning of each turn.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private int countLeft;

    /**
     * Constructor.
     *
     * @param numOfSeconds seconds
     * @param countFrom    count from which number
     * @param gameScreen   the screen of the game
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.countLeft = countFrom;
    }

    /**
     * In charge of the logic.
     *
     * @param d surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.WHITE);
        if (countLeft == 0) {
            d.drawText(d.getWidth() / 2 - 53, d.getHeight() / 2, "GO!", 60);

        } else {
            d.drawText(d.getWidth() / 2 - 20, d.getHeight() / 2, String.valueOf(this.countLeft), 60);
        }
        --this.countLeft;

        if (this.countLeft < 0) {
            this.stop = true;
        }
    }
    /**
     * change the state of should stop.
     *
     * @param state true or false
     */
    public void setStop(boolean state) {
        this.stop = state;
    }

    /**
     * is in charge of stopping condition.
     *
     * @return true or false
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
