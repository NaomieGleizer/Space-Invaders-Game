package animations;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.Ball;

import java.awt.Color;
import java.util.Random;

/**
 * An helper class for the ball animation.
 */
public class AnimationParameters {

    /**
     * @return a random color
     */
    public Color randomColor() {
        Random rand = new Random();
        float r = rand.nextFloat(); // red color
        float g = rand.nextFloat(); // green color
        float b = rand.nextFloat(); // blue color
        return new Color(r, g, b);
    }

    /**
     * @param ballSize the size of thr ball (radius)
     * @return a speed for the ball depending on its size
     */
    public int ballSpeed(int ballSize) {
        int speed;
        if (ballSize >= 50) {  // balls above size 50 can all have the same slow speed
            speed = 2;
        } else {
            speed = 100 / ballSize;
        }
        return speed;
    }

    /**
     * show every ball of the array in the surface.
     *
     * @param ball    an array of ball
     * @param d       surface
     * @param gui     gui
     * @param sleeper sleeper
     */
    public void showGui(Ball[] ball, DrawSurface d, GUI gui, Sleeper sleeper) {
        for (int i = 0; i <= ball.length - 1; i++) {
            ball[i].moveOneStep();
            ball[i].drawOn(d);
        }
        gui.show(d);
        sleeper.sleepFor(30);  // wait for 30 milliseconds.
    }
}
