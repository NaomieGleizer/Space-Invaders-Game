//package levels;
//
//import animations.AnimationRunner;
//import animations.Menu;
//import animations.MenuAnimation;
//import biuoop.KeyboardSensor;
//import game.StartGameTask;
//import game.Task;
//import scores.HighScoresTable;
//
//import java.io.File;
//import java.io.BufferedReader;
//import java.io.Reader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Level set.
// */
//public class LevelSet {
//    private KeyboardSensor keyboard;
//    private AnimationRunner runner;
//    private int frameWidth;
//    private int frameHeight;
//    private HighScoresTable highScoresTable;
//    private File scoreFile;
//
//    /**
//     * Constructor.
//     *
//     * @param k          keyboard
//     * @param ar         animation runner
//     * @param width      width of frame
//     * @param height     height of frame
//     * @param highScores high score animation
//     * @param scores     score file
//     */
//    public LevelSet(KeyboardSensor k, AnimationRunner ar, int width, int height, HighScoresTable highScores
//            , File scores) {
//        this.keyboard = k;
//        this.runner = ar;
//        this.frameHeight = height;
//        this.frameWidth = width;
//        this.highScoresTable = highScores;
//        this.scoreFile = scores;
//    }
//
//    /**
//     * set the levels.
//     *
//     * @param file set file
//     * @return a menu with the different levels
//     */
//    public Menu<Task<Void>> setLevels(Reader file) {
//        Menu<Task<Void>> subMenu = new MenuAnimation<Void>("Level Sets", keyboard, runner);
//        BufferedReader br = new BufferedReader(file);
//        List<LevelInformation> levels;
//        String line, key = "", message = "";
//        int i = 1;
//        try {
//            while ((line = br.readLine()) != null) {
//                if (i % 2 == 1) { // odd line
//                    String[] levelName = line.split(":");
//                    key = levelName[0];
//                    message = levelName[1];
//                } else { // even line
//                    BufferedReader reader = new BufferedReader(new FileReader(line));
//                    levels = new LevelSpecificationReader().fromReader(reader);
//                    subMenu.addSelection(key, message, new StartGameTask(runner, levels, keyboard, frameWidth
//                            , frameHeight, highScoresTable, scoreFile));
//                }
//                i++;
//            }
//        } catch (IOException e) {
//            System.exit(0);
//        }
//        return subMenu;
//    }
//}
