package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.MenuAnimation;
import animations.KeyPressStoppableAnimation;
import animations.HighScoresAnimation;
import animations.Menu;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import scores.HighScoresTable;

import java.io.File;

/**
 * The game runner.
 */
public class GameFlow {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private int frameWidth;
    private int frameHeight;
    private HighScoresTable highScoresTable;
    private File scoresFile;

    /**
     * Constructor.
     *
     * @param ar          animation runner
     * @param ks          keyboard
     * @param frameWidth  the width of the screen
     * @param frameHeight the height of the screen
     * @param highScores  high scores
     * @param fileName    file of high scores
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int frameWidth, int frameHeight
            , HighScoresTable highScores, File fileName) {
        this.runner = ar;
        ar.setFramesPerSecond(60);
        this.keyboard = ks;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.highScoresTable = highScores;
        this.scoresFile = fileName;
    }

    /**
     * Open menu.
     *
     * @param level the level of the game
     */
    public void openMenu(LevelInformation level) {
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("SPACE INVADERS", keyboard, runner);

        // add selections
        menu.addSelection("s", "Start Game", new StartGameTask(runner, level, keyboard, frameWidth
                , frameHeight, highScoresTable, scoresFile)); // start game
        Animation highScoresAnimation = new HighScoresAnimation(this.highScoresTable, KeyboardSensor.SPACE_KEY
                , this.keyboard);         // high scores
        menu.addSelection("h", "High Scores", new ShowHiScoresTask(runner
                , new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, highScoresAnimation)));
        menu.addSelection("q", "Quit Game", null);         // quit

        // run menu
        while (true) {
            this.runner.run(menu);

            Task<Void> task = menu.getStatus();
            task.run();
            menu.setStop(false);
            if (task.getAnimation() != null) {
                task.getAnimation().setStop(false);
            }
        }
    }
}
