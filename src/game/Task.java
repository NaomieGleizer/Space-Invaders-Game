package game;

import animations.Animation;

/**
 * Task interface.
 *
 * @param <T> type
 */
public interface Task<T> {
    /**
     * run.
     *
     * @return value
     */
    T run();

    /**
     * @return the Animation.
     */
    Animation getAnimation();
}
