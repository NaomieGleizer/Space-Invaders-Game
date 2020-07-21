package levels;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Color;

/**
 * Draw color for the background.
 */
public class ColorBackground implements Background {
    private Color color;

    /**
     * Constructor.
     *
     * @param c color
     */
    public ColorBackground(Color c) {
        this.color = c;
    }

    /**
     * Draw the color in rectangle on surface.
     *
     * @param d   drawSurface
     * @param rec rectangle
     */
    public void drawBackground(DrawSurface d, Rectangle rec) {
        d.setColor(color);
        d.fillRectangle((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY()
                , (int) rec.getWidth(), (int) rec.getHeight());
    }
}
