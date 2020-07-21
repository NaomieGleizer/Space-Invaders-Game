package geometry;

/**
 * A Line class.
 *
 * @author Naomie Gleizer
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Construct a line-segment given start and end points.
     *
     * @param start the first point
     * @param end   the second point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Construct a line-segment given two x and y coordinates.
     *
     * @param x1 coordinate x of the start point
     * @param y1 coordinate y of the start point
     * @param x2 coordinate x of the end point
     * @param y2 coordinate y of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        double xMid = (this.start.getX() + this.end.getX()) / 2;
        double yMid = (this.start.getY() + this.end.getY()) / 2;
        return new Point(xMid, yMid);
    }

    /**
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }


    /**
     * @param other a line to compare with
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        // if their slop are identical, the lines don't intersect
        Point intersectPoint = this.intersectionPointOfEquations(other);
        if (intersectPoint == null) { // parallel lines
            return false;
        }
        // check if the point is on the lines
        double roundX = (double) Math.round(intersectPoint.getX() * 100000d) / 100000d;
        double roundY = (double) Math.round(intersectPoint.getY() * 100000d) / 100000d;
        return this.isPointOnLine(new Point(roundX, roundY)) && other.isPointOnLine(new Point(roundX, roundY));
    }


    /**
     * @param point a point
     * @return true if the point is on the line and false otherwise
     */
    public boolean isPointOnLine(Point point) {
        double startX = (double) Math.round(this.start.getX() * 100000d) / 100000d;
        double startY = (double) Math.round(this.start.getY() * 100000d) / 100000d;
        double endX = (double) Math.round(this.end.getX() * 100000d) / 100000d;
        double endY = (double) Math.round(this.end.getY() * 100000d) / 100000d;

        return ((point.getX() <= Math.max(startX, endX)
                && (point.getX() >= Math.min(startX, endX)))
                && (point.getY() <= Math.max(startY, endY)
                && (point.getY() >= Math.min(startY, endY))));
    }

    /**
     * @param other the second line.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!this.isIntersecting(other)) {
            return null;
        } else {
            return this.intersectionPointOfEquations(other);
        }
    }

    /**
     * Find the intersection geometry.Point of the two lines' equations. Returns null if the lines are parallel
     * or if they have the same equation. If the line is a point, it would be the intersection point.
     *
     * @param other the second line
     * @return the intersection geometry.Point of the lines.
     */
    public Point intersectionPointOfEquations(Line other) {
        double intersectX, intersectY, slop1, slop2;
        boolean l1ParalToY = false, l2ParalToY = false;
        // case one line is parallel to the Y axis
        if (this.start.getX() == this.end.getX()) {
            slop1 = 0;
            l1ParalToY = true;
        } else { // find slop
            slop1 = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        }
        if (other.start().getX() == other.end().getX()) {
            slop2 = 0;
            l2ParalToY = true;
        } else { // find slop
            slop2 = (other.start().getY() - other.end().getY()) / (other.start().getX() - other.end().getX());
        }
        // y=ax+b, b is the free value
        double freeVal1 = slop1 * (-this.start.getX()) + this.start.getY();
        double freeVal2 = slop2 * (-other.start().getX()) + other.start().getY();
        // if the lines are parallel or have the same equation, return null
        if (slop1 != 0 && slop1 == slop2) {
            return null;
        } else if (l1ParalToY && l2ParalToY) { // parallel lines
            return null;
        } else if (l1ParalToY) { // case one line is parallel to the Y axis
            intersectX = this.start.getX();
            // place the x of the constant function in the other
            intersectY = slop2 * intersectX + freeVal2;
        } else if (l2ParalToY) {
            intersectX = other.start().getX();
            // place the x of the constant function in the other
            intersectY = slop1 * intersectX + freeVal1;
        } else { // none of the line is parallel to Y axis or one to another
            // find x of the intersection point
            intersectX = (freeVal1 - freeVal2) / (slop2 - slop1);
            // place x in one of the equations to find y
            intersectY = slop2 * intersectX + freeVal2;
        }
        return new Point(intersectX, intersectY);
    }

    /**
     * @param other a line to compare with
     * @return true is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        // compare their start and end points
        return (this.start.equals(other.start()) && this.end.equals(other.end()));
    }

    /**
     * @param rect rectangle
     * @return null if this line does not intersect with the rectangle and otherwise, return the closest
     * intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List intersPoints = rect.intersectionPoints(this);
        if (intersPoints.size() == 0) {
            return null;
        }
        Point p = (Point) intersPoints.get(0);
        double minDistance = p.distance(this.start);
        int indexClosestPoint = 0;
        for (int i = 1; i < intersPoints.size(); i++) {
            p = (Point) intersPoints.get(i);
            if (p.distance(this.start) < minDistance) {
                minDistance = p.distance(this.start);
                indexClosestPoint = i;
            }
        }
        return (Point) intersPoints.get(indexClosestPoint);
    }
}