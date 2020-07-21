package sprites;

import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Move the aliens.
 */
public class AlienMover {
    private double originalSpeed;
    private double speed;
    private List<Alien> aliens = new ArrayList<>();
    private boolean finishMovingRight = false;
    private int frameStart;
    private int frameEnd;
    private int shieldLocation;
    private int numOfRowDown = 0;
    private int xStartPosition;
    private int gapAliens;

    /**
     * Constructor.
     * @param start start of fame
     * @param end end of frame
     * @param speed starting speed of the aliens
     * @param shieldLocation the location of the shields
     * @param xStartPosition x coordinate of the first alien
     * @param gap gap between the column and the rows
     */
    public AlienMover(int start, int end, double speed, int shieldLocation, int xStartPosition, int gap) {
        this.frameStart = start;
        this.frameEnd = end;
        this.originalSpeed = speed;
        this.speed = speed;
        this.shieldLocation = shieldLocation;
        this.xStartPosition = xStartPosition;
        this.gapAliens = gap;
    }


    /**
     * add this alien to the game.
     *
     * @param a sprite
     */
    public void addAlien(Alien a) {
        this.aliens.add(a);
    }

    /**
     * remove this alien from the game.
     *
     * @param a sprite
     */
    public void removeAlien(Block a) {
        this.aliens.remove(a);
    }

    /**
     * @return the list of aliens
     */
    public List<Alien> getAliens() {
        return this.aliens;
    }


    /**
     * @return true if the aliens reach the shields
     */
    public boolean move() {
        boolean flagLine = false;
        if (!finishMovingRight) { // move right
            for (int i = this.aliens.size() - 1, j = 1; j <= 5 && i > 0; i--, j++) { // right side
                // check if the first column is going to hit the border
                Alien alien = this.aliens.get(i);
                Rectangle r = alien.getCollisionRectangle();
                if (r.getUpperLeft().getX() + r.getWidth() + speed >= frameEnd) {
                    if (moveLine()) {
                        return true; // the alien reach the shield
                    }
                    flagLine = true;
                    finishMovingRight = true;
                    break;
                }
            }
            if (!flagLine && !aliens.isEmpty()) {
                moveRight();
            }
        } else { // move left
            for (int i = 0; i < 5; i++) { // left side
                if (aliens.size() <= i) {
                    break;
                }
                Alien alien = this.aliens.get(i);
                if (alien.getCollisionRectangle().getUpperLeft().getX() - speed <= frameStart) {
                    if (moveLine()) {
                        return true; // the alien reach the shield
                    }
                    flagLine = true;
                    finishMovingRight = false;
                    break;
                }
            }
            if (!flagLine && !aliens.isEmpty()) {
                moveLeft();
            }
        }
        return false;
    }

    /**
     * @return true if the aliens reach the shields
     */
    public boolean moveLine() {
        int row;
        for (Alien alien : this.aliens) {
            row = alien.getNumOfRow();
            if (alien.getCollisionRectangle().getUpperLeft().getY()
                    + 2 * alien.getCollisionRectangle().getHeight() >= shieldLocation) {
                return true;
            } else {
                if (row == 5) {
                    break;
                }
            }
        }
        for (Alien alien : this.aliens) {
            alien.moveLine();
        }
        speed += (speed * 0.1);
        numOfRowDown++;
        return false;
    }

    /**
     * Move the aliens to the right.
     */
    public void moveRight() {
        for (Alien alien : this.aliens) {
            alien.moveRightOrLeft(speed);
        }
    }

    /**
     * Move the aliens to the left.
     */
    public void moveLeft() {
        for (Alien alien : this.aliens) {
            alien.moveRightOrLeft(-speed);
        }
    }

    /**
     * Move the aliens back to the top.
     */
    public void goBackToTheTop() {
        for (Alien alien : this.aliens) {
            Point location = alien.getCollisionRectangle().getUpperLeft();
            Point newUpperLeftPoint = new Point((alien.getNumOfColumn() - 1) * (alien.getCollisionRectangle().getWidth()
                    + gapAliens) + xStartPosition, location.getY()
                    - numOfRowDown * alien.getCollisionRectangle().getHeight());
            alien.getCollisionRectangle().setUpperLeftPoint(newUpperLeftPoint);
        }
        speed = originalSpeed;
        numOfRowDown = 0;
        finishMovingRight = false;
    }
}
