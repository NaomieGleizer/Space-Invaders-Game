package animations;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * The animations.AnimationRunner takes an animation object and runs it.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper = new Sleeper();

    /**
     * Constructor.
     *
     * @param gui gui of the game.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
    }

    /**
     * Set the number of frames per second.
     *
     * @param frames frames per second
     */
    public void setFramesPerSecond(int frames) {
        this.framesPerSecond = frames;

    }

    /**
     * @return the gui of the game
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Run loop.
     *
     * @param animation logic and stopping condition for the loop
     */
    public void run(Animation animation) {
        long millisecondsPerFrame = (long) 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d, (double) millisecondsPerFrame / 1000.0D);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}