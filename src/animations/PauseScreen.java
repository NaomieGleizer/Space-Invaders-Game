package animations;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The Pause screen.
 */
public class PauseScreen implements Animation {

    /**
     * In charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.decode("#7C1471"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.WHITE);
        d.drawText(315, d.getHeight() / 3 + 50, "Paused", 50);
        d.drawText(250, d.getHeight() / 2 + 15, "Press Space To Continue", 26);
    }

    /**
     * change the state of should stop.
     *
     * @param state true or false
     */
    public void setStop(boolean state) {
    }

    /**
     * is in charge of stopping condition.
     *
     * @return true or false
     */
    public boolean shouldStop() {
        return false;
    }
}
