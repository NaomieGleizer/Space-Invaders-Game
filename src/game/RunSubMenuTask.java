package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.Menu;

/**
 * Run sub-menu.
 */
public class RunSubMenuTask implements Task<Void> {
    private AnimationRunner runner;
    private Menu<Task<Void>> menuAnimation;

    /**
     * Constructor.
     *
     * @param ar        animation runner
     * @param animation menu animation
     */
    public RunSubMenuTask(AnimationRunner ar, Menu<Task<Void>> animation) {
        this.runner = ar;
        this.menuAnimation = animation;
    }

    /**
     * run the animation.
     *
     * @return void
     */
    public Void run() {
        this.runner.run(this.menuAnimation);

        Task<Void> task = menuAnimation.getStatus();
        task.run();
        menuAnimation.setStop(false);
        return null;
    }

    /**
     * @return the Animation.
     */
    public Animation getAnimation() {
        return menuAnimation;
    }
}
