package sprites;

import geometry.Rectangle;
import geometry.Point;
import levels.BlocksDefinitionReader;
import levels.ImageBackground;

/**
 * Alien.
 */
public class Alien extends Block {
    private int numOfColumn;
    private int numOfRow;

    /**
     * Constructor.
     *
     * @param r           rectangle
     * @param hitPoints   num of hit points
     * @param numOfColumn number of column
     * @param numOfRow    number of row
     */
    public Alien(Rectangle r, int hitPoints, int numOfColumn, int numOfRow) {
        super(r, hitPoints);
        this.numOfColumn = numOfColumn;
        this.numOfRow = numOfRow;
        setImageBackground();
    }

    /**
     * Set the background of the alien.
     */
    public void setImageBackground() {
        this.setDefaultBackground(new ImageBackground(BlocksDefinitionReader.findImage(
                "block_images/alien" + String.valueOf(numOfRow) + ".png")));

    }

    /**
     * move the alien to the next line.
     */
    public void moveLine() {
        Point location = this.getCollisionRectangle().getUpperLeft();
        double newY = location.getY() + this.getCollisionRectangle().getHeight();

        Point newUpperLeftPoint = new Point(location.getX(), newY);
        this.getCollisionRectangle().setUpperLeftPoint(newUpperLeftPoint);
    }

    /**
     * @param change changing the x coordinate by this number.
     */
    public void moveRightOrLeft(double change) {
        Point location = this.getCollisionRectangle().getUpperLeft();
        Point newUpperLeftPoint = new Point(location.getX() + change, location.getY());
        this.getCollisionRectangle().setUpperLeftPoint(newUpperLeftPoint);
    }

    /**
     * Add the alien to AlienMover.
     *
     * @param alienMover alien mover
     */
    public void addToAlienMover(AlienMover alienMover) {
        alienMover.addAlien(this);
    }

    /**
     * get the column of the alien.
     *
     * @return number of column
     */
    public int getNumOfColumn() {
        return this.numOfColumn;
    }

    /**
     * get the row of the alien.
     *
     * @return number of row
     */
    public int getNumOfRow() {
        return this.numOfRow;
    }
}

