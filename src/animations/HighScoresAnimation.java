package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import scores.HighScoresTable;
import scores.ScoreInfo;

import java.awt.Color;

/**
 * High score animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highScores;
    private String key;
    private boolean stop;
    private KeyboardSensor keyboard;

    /**
     * Constructor.
     *
     * @param scores         high score table
     * @param endKey         key for closing
     * @param keyboardSensor keyboard
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor keyboardSensor) {
        this.highScores = scores;
        this.key = endKey;
        this.stop = false;
        this.keyboard = keyboardSensor;
    }

    /**
     * is in charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        int row = 210, i = 1;
        d.setColor(Color.RED);
        d.drawText(75, 160, "Rank", 25);
        d.drawText(240, 160, "Name", 25);
        d.drawText(510, 160, "Score", 25);

        d.setColor(Color.YELLOW);
        d.drawText(197, 80, "HIGH SCORES", 60);
        for (ScoreInfo scoreInfo : this.highScores.getHighScores()) {
            d.drawText(90, row, String.valueOf(i), 30);
            d.drawText(240, row, scoreInfo.getName(), 30);
            d.drawText(510, row, String.valueOf(scoreInfo.getScore()), 30);
            row += 70;
            i++;
        }

        if (this.keyboard.isPressed(this.key)) {
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
