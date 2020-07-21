package geometry;

/**
 * The rectangle interface.
 */
public interface RectanInterface {

    /**
     * @param line a line
     * @return a list of the intersection points between the line and the rectangle
     */
    java.util.List intersectionPoints(Line line);

    /**
     * @return the width of the rectangle
     */
    double getWidth();

    /**
     * @return the heigh of the rectangle
     */
    double getHeight();

    /**
     * @return the upper-left point of the rectangle
     */
    Point getUpperLeft();
}
