package levels;

import java.util.Map;
import java.util.TreeMap;
import java.awt.Color;

/**
 * Color parser.
 */
public class ColorsParser {
    private Map<String, Color> map = new TreeMap<String, Color>();

    /**
     * add a map of colors.
     */
    public ColorsParser() {
        map.put("yellow", Color.yellow);
        map.put("red", Color.red);
        map.put("black", Color.black);
        map.put("blue", Color.blue);
        map.put("cyan", Color.cyan);
        map.put("gray", Color.gray);
        map.put("lightGray", Color.lightGray);
        map.put("green", Color.green);
        map.put("orange", Color.orange);
        map.put("pink", Color.pink);
        map.put("white", Color.white);
        map.put("lightViolet", Color.decode("#B266FF"));
        map.put("lightOrange", Color.decode("#FFB266"));
        map.put("lightYellow", Color.decode("#FFFF99"));
        map.put("lightBlue", Color.decode("#99CCFF"));
        map.put("violet", Color.decode("#9999FF"));

    }

    /**
     * parse color definition and return the specified color.
     *
     * @param s string
     * @return the color
     */
    public java.awt.Color colorFromString(String s) {
        return map.get(s);
    }
}
