package main;
import animations.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import levels.LevelSpecificationReader;
import scores.HighScoresTable;
import game.GameFlow;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.File;
import java.util.List;

/**
 * Assignment 7 game.
 */
public class Ass7Game {
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    /**
     * Main of the game.
     *
     * @param args arguments
     * @throws Exception if fail reading a file
     */
    public static void main(String[] args) throws Exception {
        List<LevelInformation> levels;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("definitions/levels.txt");
        Reader reader = new InputStreamReader(is);
        levels = new LevelSpecificationReader().fromReader(reader);

        HighScoresTable highScoresTable;
        File highScoresFile = new File("src/highscores.txt");
        if (!highScoresFile.exists()) {
            highScoresTable = new HighScoresTable(4);
        } else {
            highScoresTable = HighScoresTable.loadFromFile(highScoresFile);
        }
        highScoresTable.save(highScoresFile);

        // run the game
        GUI gui = new GUI("SPACE INVADERS", FRAME_WIDTH, FRAME_HEIGHT);
        AnimationRunner animationRunner = new AnimationRunner(gui);
        KeyboardSensor keyboard = animationRunner.getGui().getKeyboardSensor();
        GameFlow game = new GameFlow(animationRunner, keyboard, FRAME_WIDTH, FRAME_HEIGHT, highScoresTable
                , highScoresFile);

        game.openMenu(levels.get(0));
    }
}
