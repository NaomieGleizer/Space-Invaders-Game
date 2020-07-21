package animations;

import biuoop.DrawSurface;

/**
 * Animation interface.
 */
public interface Animation {
    /**
     * is in charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * is in charge of stopping condition.
     *
     * @return true or false
     */
    boolean shouldStop();

    /**
     * change the state of should stop.
     *
     * @param state true or false
     */
    void setStop(boolean state);
}