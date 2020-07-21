package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.RunSubMenuTask;
import game.Task;
import geometry.Rectangle;
import geometry.Point;
import levels.Background;
import levels.BlocksDefinitionReader;
import levels.ImageBackground;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu animation of the game.
 *
 * @param <T> type
 */
public class MenuAnimation<T> implements Animation, Menu<Task<Void>> {
    private String menuTitle;
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private List<String> keys = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private boolean stop = false;
    private List<Task<Void>> statuses = new ArrayList<>();
    private Task<Void> status;

    /**
     * Constructor.
     *
     * @param title title of menu
     * @param k     keyboard
     * @param ar    animation runner
     */
    public MenuAnimation(String title, KeyboardSensor k, AnimationRunner ar) {
        this.menuTitle = title;
        this.keyboard = k;
        this.runner = ar;
    }

    /**
     * Generics for menu.
     *
     * @param key       key to wait for
     * @param message   message to print
     * @param returnVal return value
     */
    public void addSelection(String key, String message, Task<Void> returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.statuses.add(returnVal);
    }

    /**
     * change the state of should stop.
     *
     * @param state true or false
     */
    public void setStop(boolean state) {
        this.stop = state;
        this.status = null;
    }

    /**
     * @return status.
     */
    public Task<Void> getStatus() {
        return this.status;
    }

    /**
     * add a sub menu.
     *
     * @param key     key
     * @param message level type
     * @param subMenu sub menu
     */
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {
        this.addSelection(key, message, new RunSubMenuTask(this.runner, subMenu));
    }

    /**
     * is in charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // background color
        Background background = new ImageBackground(BlocksDefinitionReader.findImage("screen_images/menu.png"));
        background.drawBackground(d, new Rectangle(new Point(0, 0), d.getWidth(), d.getHeight()));

        // title of the menu
        d.setColor(Color.WHITE);
        d.drawText(70, 100, this.menuTitle, 75);

        int positionX = 250;
        int positionY = 200;

        // draw selections
        for (String key : this.keys) {
            int index = this.keys.indexOf(key);
            d.drawText(positionX, positionY, this.messages.get(index), 50);
            d.drawText(positionX + 285, positionY, "(" + key + ")", 30);
            positionY += 90;
            if (this.keyboard.isPressed(key)) {
                this.stop = true;
                if (key.equals("q")) { // exit game
                    System.exit(0);
                }
                this.status = this.statuses.get(index);
                break;
            }
        }

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
