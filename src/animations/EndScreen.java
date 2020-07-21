package animations;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import levels.Background;
import levels.BlocksDefinitionReader;
import levels.ImageBackground;

import java.awt.Color;

/**
 * The End screen.
 */
public class EndScreen implements Animation {
    private int score;
    private int numOfLivesRemained;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param score              score of the game
     * @param numOfLivesRemained number of remained balls in the game
     */
    public EndScreen(int score, int numOfLivesRemained) {
        this.score = score;
        this.numOfLivesRemained = numOfLivesRemained;
        this.stop = false;
    }

    /**
     * In charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (numOfLivesRemained == 0) { // lost
            Background b = new ImageBackground(BlocksDefinitionReader.findImage("screen_images/gameOver.jpg"));
            b.drawBackground(d, new Rectangle(new Point(0, 0), 800, 600));

            d.setColor(Color.decode("#99004C"));
            d.drawText(250, d.getHeight() - 50, "YOUR SCORE IS " + String.valueOf(this.score), 26);

        } else { // win
            Background b = new ImageBackground(BlocksDefinitionReader.findImage("screen_images/winGame.jpg"));
            b.drawBackground(d, new Rectangle(new Point(0, 0), 800, 600));
            d.setColor(Color.BLACK);
            d.drawText(270, 0, "Your Score Is " + String.valueOf(this.score), 35);
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


