package game;

import animations.AnimationRunner;
import animations.KeyPressStoppableAnimation;
import animations.HighScoresAnimation;
import animations.Animation;
import animations.EndScreen;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import scores.HighScoresTable;
import scores.ScoreInfo;

import java.io.File;

/**
 * Start the game.
 */
public class StartGameTask implements Task<Void> {
    private AnimationRunner runner;
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;
    private int frameWidth;
    private int frameHeight;
    private HighScoresTable highScoresTable;
    private File scoresFile;

    /**
     * Constructor.
     *
     * @param animationRunner animation runner
     * @param level           levels of the game
     * @param k               keyboard
     * @param width           width of the frame
     * @param height          height of the frame
     * @param highScores      high scores animation
     * @param file            score file
     */
    public StartGameTask(AnimationRunner animationRunner, LevelInformation level, KeyboardSensor k
            , int width, int height, HighScoresTable highScores, File file) {
        this.runner = animationRunner;
        this.levelInfo = level;
        this.keyboard = k;
        this.frameWidth = width;
        this.frameHeight = height;
        this.highScoresTable = highScores;
        this.scoresFile = file;
    }

    /**
     * run the animation.
     *
     * @return void
     */
    public Void run() {
        boolean startGame = true;
        int counterLevel = 2;
        levelInfo.setLevelName("Space Invaders - Round 1");
        GameLevel level = new GameLevel(levelInfo, this.runner, this.keyboard, this.frameWidth, this.frameHeight);
        level.initialize(startGame);
        while (level.getNumOfLife() > 0) {
            startGame = false;
            // run every level until it's over(lose)
            level.playOneTurn();
            if (level.getNumOfRemainedBlocks() == 0) {
                levelInfo.setLevelName("Space Invaders - Round " + String.valueOf(counterLevel));
                counterLevel++;
                level = new GameLevel(levelInfo, this.runner, this.keyboard, this.frameWidth, this.frameHeight);
                level.initialize(startGame);
            }
        }
        int remainedLives = level.getNumOfLife();
        int score = level.getScore();

        // end screen- win or lose
        Animation endScreen = new EndScreen(score, remainedLives);
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, endScreen));
        printHighScores(score);
        return null;
    }

    /**
     * Print high score table.
     *
     * @param score current score
     */
    public void printHighScores(int score) {
        int scoreRank = this.highScoresTable.getRank(score);
        if (scoreRank <= this.highScoresTable.size()) { //new high score
            DialogManager dialog = this.runner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.highScoresTable.add(new ScoreInfo(name, score));
            try {
                this.highScoresTable.save(this.scoresFile);
            } catch (Exception e) {
                return;
            }
        }
        Animation highScoresAnimation = new HighScoresAnimation(this.highScoresTable, KeyboardSensor.SPACE_KEY
                , this.keyboard);
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, highScoresAnimation));
    }

    /**
     * @return the Animation.
     */
    public Animation getAnimation() {
        return null;
    }
}
