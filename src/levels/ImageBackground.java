package levels;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Image;

/**
 * Draw an image in a rectangle.
 */
public class ImageBackground implements Background {
    private Image image;

    /**
     * Constructor.
     *
     * @param i image
     */
    public ImageBackground(Image i) {
        this.image = i;
    }

    /**
     * Draw the image in rectangle on surface.
     *
     * @param d   drawSurface
     * @param rec rectangle
     */
    public void drawBackground(DrawSurface d, Rectangle rec) {
        d.drawImage((int) rec.getUpperLeft().getX(), (int) rec.getUpperLeft().getY(), image);
    }
}
