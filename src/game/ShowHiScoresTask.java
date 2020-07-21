package game;

import animations.AnimationRunner;
import animations.Animation;

/**
 * run high score animation task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Constructor.
     *
     * @param runner              animation runner
     * @param highScoresAnimation animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * run the animation.
     *
     * @return void
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }

    /**
     * @return the Animation.
     */
    public Animation getAnimation() {
        return this.highScoresAnimation;
    }
}