package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Stop animation by pressing a key.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String endKey;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     *
     * @param sensor    keyboard
     * @param key       key
     * @param animation animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.endKey = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * is in charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (isAlreadyPressed) {
            isAlreadyPressed = false;
        } else if (this.keyboard.isPressed(this.endKey)) {
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
