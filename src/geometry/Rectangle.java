package geometry;

import java.util.ArrayList;

/**
 * A rectangle class.
 */
public class Rectangle implements RectanInterface {
    private Point upperLeftPoint;
    private double width;
    private double height;
    private java.awt.Color color = null;

    /**
     * Create a new rectangle with location ,width and height.
     *
     * @param upperLeft the upper-left point in the of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeftPoint = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeftPoint;
    }

    /**
     * @return the left line of the rectangle
     */
    public Line getLeftLine() {
        Point lowerLeft = new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.height);
        return new Line(this.getUpperLeft(), lowerLeft);
    }

    /**
     * @return the right line of the rectangle
     */
    public Line getRightLine() {
        Point upperRight = new Point(this.getUpperLeft().getX() + width, this.getUpperLeft().getY());
        Point lowerRight = new Point(upperRight.getX(), upperRight.getY() + this.height);
        return new Line(upperRight, lowerRight);
    }

    /**
     * @return the top line of the rectangle
     */
    public Line getTopLine() {
        Point upperRight = new Point(this.getUpperLeft().getX() + width, this.getUpperLeft().getY());
        return new Line(this.upperLeftPoint, upperRight);
    }

    /**
     * @return the bottom line of the rectangle
     */
    public Line getBottomLine() {
        Point lowerLeft = new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.height);
        Point lowerRight = new Point(lowerLeft.getX() + width, lowerLeft.getY());
        return new Line(lowerLeft, lowerRight);
    }

    /**
     * @return the color of the rectangle
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @param line a line
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List intersectionPoints(Line line) {
        java.util.List<Point> intersectP = new ArrayList<>();
        // check intersection points with left,right,top,bottom lines of the rectangle
        Point p1 = line.intersectionWith(this.getLeftLine());
        Point p2 = line.intersectionWith(this.getRightLine());
        Point p3 = line.intersectionWith(this.getTopLine());
        Point p4 = line.intersectionWith(this.getBottomLine());
        // add points to the list
        // add points to the list
        if (p1 != null) {
            intersectP.add(p1);
        }
        if (p2 != null) {
            intersectP.add(p2);
        }
        if (p3 != null) {
            intersectP.add(p3);
        }
        if (p4 != null) {
            intersectP.add(p4);
        }
        return intersectP;
    }

    /**
     * @param colorRectangle the color of the rectangle
     */
    public void setColor(java.awt.Color colorRectangle) {
        this.color = colorRectangle;
    }

    /**
     * @param point a new upper-left point
     */
    public void setUpperLeftPoint(Point point) {
        this.upperLeftPoint = point;
    }
}
