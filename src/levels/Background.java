package levels;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * Interface for drawing the background.
 */
public interface Background {
    /**
     * Draw the rectangle on surface.
     * @param d   drawSurface
     * @param rec rectangle
     */
    void drawBackground(DrawSurface d, Rectangle rec);
}
