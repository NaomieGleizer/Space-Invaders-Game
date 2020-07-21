package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.PauseScreen;
import animations.Counter;
import animations.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import listeners.AlienRemover;
import sprites.Ball;
import sprites.Paddle;
import sprites.Block;
import sprites.Sprite;
import sprites.Collidable;
import sprites.SpriteCollection;
import sprites.Velocity;
import sprites.ScoreIndicator;
import sprites.LivesIndicator;
import sprites.AlienMover;
import sprites.Alien;
import listeners.BallRemover;
import listeners.BlockRemover;
import levels.LevelInformation;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

/**
 * The game.
 */
public class GameLevel implements Animation {
    public static final double SCORE_BLOCK_HEIGHT = 24;
    public static final int PADDLE_HEIGHT = 20;
    public static final int SHIELD_POSITION = 500;
    public static final int NUM_OF_ALIEN_ROWS = 5;
    public static final int NUM_OF_ALIEN_COLUMN = 10;
    public static final int ALIEN_X_START_POSITION = 150;
    public static final int ALIEN_Y_START_POSITION = 50;
    public static final int ALIEN_WIDTH = 40;
    public static final int ALIEN_HEIGHT = 30;
    public static final int ALIEN_GAP = 10;


    private int frameHeight;
    private int frameWidth;
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private Counter remainedAliens = new Counter(0);
    private boolean paddleHit = false;
    private static Counter score = null;
    private static Counter numOfLives = null;
    private AnimationRunner runner;
    private boolean running = this.remainedAliens.getValue() > 0 && !this.paddleHit;
    private KeyboardSensor keyboard;
    private LevelInformation level;
    private Paddle paddle;
    private double canShoot = 0.35;
    private double enemyBullet = 0.5;
    private List<Ball> balls = new ArrayList<>();
    private AlienMover alienMover;
    private static double alienStartSpeed = 1.5;

    /**
     * Constructor.
     *
     * @param levelInformation level of the game
     * @param ar               animation runner
     * @param keyboard         keyboard
     * @param width            width of the screen
     * @param height           height of the screen
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner ar, KeyboardSensor keyboard, int width
            , int height) {
        this.level = levelInformation;
        this.frameWidth = width;
        this.frameHeight = height;
        this.keyboard = keyboard;
        this.runner = ar;
    }

    /**
     * @param c a collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * @param s a sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks, balls and sprites.Paddle, and add them to the game.
     *
     * @param withReset true if its a new game
     */
    public void initialize(boolean withReset) {
        this.alienMover = new AlienMover(0, frameWidth, alienStartSpeed, SHIELD_POSITION, ALIEN_X_START_POSITION
                , ALIEN_GAP);
        if (withReset) {
            numOfLives = new Counter(3);
            score = new Counter(0);
        }
        BlockRemover blockRemover = new BlockRemover(this);
        BallRemover ballRemover = new BallRemover(this);
        AlienRemover alienRemover = new AlienRemover(this, remainedAliens, score, alienMover);
        // Add the background of the level as a sprite to the game
        Sprite backGround = this.level.getBackground();
        backGround.addToTheGame(this);
        // create the lives block;
        this.createLivesBlock();
        // create score block
        this.createScoreBlock();
        // create blocks at top and bottom to remove the balls
        Block top = createTopRemoveBall();
        top.addHitListener(ballRemover);
        Block bottom = createBottomRemoveBall();
        bottom.addHitListener(ballRemover);

        //aliens
        int x = ALIEN_X_START_POSITION, y = ALIEN_Y_START_POSITION, column = 1, row = 1;
        for (int i = 1; i <= NUM_OF_ALIEN_COLUMN * NUM_OF_ALIEN_ROWS; i++) {
            Rectangle r = new Rectangle(new Point(x, y), ALIEN_WIDTH, ALIEN_HEIGHT);
            Alien alien = new Alien(r, 1, column, row);
            alien.addHitListener(alienRemover);
            alien.addToTheGame(this);
            alien.addToAlienMover(this.alienMover);
            row++;
            y += ALIEN_GAP + ALIEN_HEIGHT;
            if (i % NUM_OF_ALIEN_ROWS == 0) {
                x += ALIEN_GAP + ALIEN_WIDTH;
                y = ALIEN_Y_START_POSITION;
                column++;
                row = 1;
            }
        }

        // creates the shields
        List<Block> blocks = this.level.getBlocks();
        this.remainedAliens.increase(this.level.getNumberOfBlocksToRemove());
        for (Block block : blocks) {
            Block currentBlock = new Block(block);
            currentBlock.addToTheGame(this);
            currentBlock.addHitListener(blockRemover);
        }
    }

    /**
     * Create the balls  and the paddle for the game.
     */
    public void createPaddle() {
        // create paddle
        Point upperLeftPaddle = new Point((this.frameWidth - this.level.getPaddleWidth()) / 2
                , this.frameHeight - PADDLE_HEIGHT);
        Rectangle paddleRect = new Rectangle(upperLeftPaddle, this.level.getPaddleWidth(), PADDLE_HEIGHT);
        paddleRect.setColor(Color.decode("#0000CD"));
        this.paddle = new Paddle(this.keyboard, 0, this.frameWidth, this.level.getPaddleSpeed()
                , numOfLives, this);
        this.paddle.setShape(paddleRect);
        this.paddle.addToTheGame(this);
    }

    /**
     * shoot a bullet from the space ship.
     */
    public void shoot() {
        this.canShoot = 0.35;
        // create the balls
        Velocity velocity = this.level.getBallVelocities().get(0);
        Rectangle r = this.paddle.getCollisionRectangle();
        int centerX = (int) r.getUpperLeft().getX() + (int) r.getWidth() / 2;
        Point center = new Point(centerX, this.frameHeight - r.getHeight() - 2);
        Ball ball = new Ball(center, 4, Color.orange);
        ball.setVelocity(velocity);
        // add the ball to the game
        ball.addToTheGame(this);
        ball.setGameEnvironment(this.environment);
        this.balls.add(ball);
    }

    /**
     * shoot a bullet from an alien.
     */
    public void alienBullet() {
        this.enemyBullet = 0.5;
        Random random = new Random();
        int chosenColumn = random.nextInt(9);
        Alien lastAlienInRow = findLastAlienInRow(chosenColumn);

        while (lastAlienInRow == null) { // if there isn't an alien in this column, find another column
            chosenColumn = random.nextInt(9);
            lastAlienInRow = findLastAlienInRow(chosenColumn);
        }

        Rectangle r = lastAlienInRow.getCollisionRectangle();
        int centerX = (int) r.getUpperLeft().getX() + (int) r.getWidth() / 2 + 5;
        int centerY = (int) r.getUpperLeft().getY() + (int) r.getHeight() + 5;
        Point center = new Point(centerX, centerY);
        Ball ball = new Ball(center, 5, Color.red);
        ball.setVelocity(Velocity.fromAngleAndSpeed(180, 550));
        ball.setStatusIsEnemyBullet(true);
        // add the ball to the game
        ball.addToTheGame(this);
        ball.setGameEnvironment(this.environment);
        this.balls.add(ball);
    }

    /**
     * @param column a number of column
     * @return the last alien in this column
     */
    public Alien findLastAlienInRow(int column) {
        Alien lastAlienInRow = null;
        boolean flagFound = false;
        List<Alien> aliens = alienMover.getAliens();
        for (Alien a : aliens) {
            if (a.getNumOfColumn() == column) {
                lastAlienInRow = a;
                flagFound = true;
            } else if (flagFound) {
                break;
            }
        }
        return lastAlienInRow;
    }

    /**
     * Play one life.
     */
    public void playOneTurn() {
        this.paddleHit = false;
        this.createPaddle();
        this.runner.setFramesPerSecond(2); // frames for the countdown
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.runner.setFramesPerSecond(60); // frames for the game
        this.running = true;
        // use our runner to run the current animation -- which is one turn of the game.
        this.runner.run(this);

        this.removeSprite(this.paddle);
        this.removeCollidable(this.paddle);
        for (Ball b : this.balls) {
            b.removeFromGame(this);
        }
    }

    /**
     * create the block for the score at top of the screen.
     */
    public void createScoreBlock() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Rectangle(new Point(150, 0), this.frameWidth - 400
                , SCORE_BLOCK_HEIGHT), score);
        scoreIndicator.getShape().setColor(Color.CYAN);
        scoreIndicator.addToTheGame(this);
    }

    /**
     * create the block for the lives-indicator at top of the screen.
     */
    public void createLivesBlock() {
        LivesIndicator livesIndicator = new LivesIndicator(new Rectangle(new Point(0, 0), 150
                , SCORE_BLOCK_HEIGHT), numOfLives);
        livesIndicator.getShape().setColor(Color.PINK);
        livesIndicator.addToTheGame(this);
    }


    /**
     * @return the block which is located at top of the screen and is responsible for the lost of the balls.
     */
    public Block createTopRemoveBall() {
        Block topBlock = new Block(new Rectangle(new Point(-5, -5), this.frameWidth, 5), 0);
        topBlock.getCollisionRectangle().setColor(Color.BLACK);
        topBlock.addToTheGame(this);
        return topBlock;
    }

    /**
     * @return the block which is located below the screen and is responsible for the lost of the balls.
     */
    public Block createBottomRemoveBall() {
        Block bottomBlock = new Block(new Rectangle(new Point(0, this.frameHeight), this.frameWidth, 5), 0);
        bottomBlock.getCollisionRectangle().setColor(Color.BLACK);
        bottomBlock.addToTheGame(this);
        return bottomBlock;
    }

    /**
     * is in charge of the logic.
     *
     * @param d  surface
     * @param dt It specifies the amount of seconds passed since the last call
     */
    public void doOneFrame(DrawSurface d, double dt) {
        canShoot -= dt;
        enemyBullet -= dt;

        if (this.keyboard.isPressed("space") && canShoot < 0) {
            shoot();
        }
        if (enemyBullet < 0) {
            alienBullet();
        }

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        // move aliens
        boolean alienReachShield = this.alienMover.move();
        if (this.paddleHit || alienReachShield) {
            this.alienMover.goBackToTheTop();
            this.running = false;
            if (alienReachShield) {
                numOfLives.decrease(1);
            }
        } else if (getNumOfRemainedBlocks() == 0) {
            alienStartSpeed += alienStartSpeed * 0.15;
            this.running = false;
        }

        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
    }

    /**
     * Remove the sprites.Collidable from the game.
     *
     * @param c sprites.Collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);

    }

    /**
     * Remove the sprite from the game.
     *
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
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
        return !this.running;
    }

    /**
     * @return the number of life
     */
    public int getNumOfLife() {
        return numOfLives.getValue();
    }

    /**
     * @return the number of remaining getBlocks
     */
    public int getNumOfRemainedBlocks() {
        return this.remainedAliens.getValue();
    }


    /**
     * @return the current score
     */
    public int getScore() {
        return score.getValue();
    }

    /**
     * @param hit true if there is a hit with the paddle
     */
    public void setPaddleHit(boolean hit) {
        this.paddleHit = hit;
    }
}