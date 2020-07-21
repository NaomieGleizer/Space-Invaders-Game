package levels;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Reader of the block definition file.
 */
public class BlocksDefinitionReader {

    /**
     * @param reader block definition file
     * @return block-symbol
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        String st, symbol = "", state = "";
        SetBlock block, defaultBlock = null;
        boolean defaultFlag = false;
        BlocksFromSymbolsFactory blocksSymbol = new BlocksFromSymbolsFactory();
        int width = 0;
        try {
            while ((st = br.readLine()) != null) { // read all the file
                if (st.isEmpty() || st.charAt(0) == '#') { // skip empty lines and comments lines
                    if (st.equals("# default values for blocks")) {
                        defaultBlock = new SetBlock();
                        defaultFlag = true;
                    }
                    continue;
                }
                if (defaultFlag) {
                    block = new SetBlock(defaultBlock);
                } else {
                    block = new SetBlock();
                }
                String[] properties = st.split(" ");
                for (String s : properties) { // go over each property
                    if (s.equals("default") || s.equals("bdef") || s.equals("sdef")) {
                        state = s;
                        continue;
                    }
                    String[] keyAndValue = s.split(":");
                    if (keyAndValue[0].equals("symbol")) {
                        symbol = keyAndValue[1];
                        continue;
                    }
                    if (state.equals("default")) {
                        BlocksDefinitionReader.addProperty(defaultBlock, keyAndValue, state);
                    } else {
                        width = BlocksDefinitionReader.addProperty(block, keyAndValue, state);
                    }
                }
                if (state.equals("bdef")) { // add block to the list
                    blocksSymbol.addBlockSymbol(symbol, block);
                } else if (state.equals("sdef")) { // add spacer to the list
                    blocksSymbol.addSpaceSymbol(symbol, width);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed reading file");
            System.exit(0);
        }
        return blocksSymbol;
    }

    /**
     * Add property for the block.
     *
     * @param block       the block
     * @param keyAndValue key and value from the file
     * @param state       sdef/bdef/default
     * @return the width
     */
    public static int addProperty(SetBlock block, String[] keyAndValue, String state) {
        int width = 0;
        switch (keyAndValue[0]) { // set property
            case "height":
                block.setHeight(Integer.valueOf(keyAndValue[1]));
                break;
            case "width":
                width = Integer.valueOf(keyAndValue[1]);
                if (!state.equals("sdef")) {
                    block.setWidth(width);
                }
                break;
            case "hit_points":
                block.setHitPoints(Integer.valueOf(keyAndValue[1]));
                break;
            case "fill":
                String data = keyAndValue[1].substring(6, keyAndValue[1].length());
                if (keyAndValue[1].substring(0, 5).equals("color")) {
                    block.setBackground(new ColorBackground(findColor(data)));
                } else { // image
                    block.setBackground(new ImageBackground(findImage(data)));
                }
                break;
            case "stroke":
                block.setStroke(findColor(keyAndValue[1].substring(6, keyAndValue[1].length())));
                break;
            default: // fill-k or wrong field
                String[] fillK = keyAndValue[0].split("-");
                if (keyAndValue[1].substring(0, 5).equals("color")) {
                    block.addBackground(Integer.valueOf(fillK[1]), new ColorBackground(
                            findColor(keyAndValue[1].substring(6, keyAndValue[1].length()))));
                } else { // image
                    block.addBackground(Integer.valueOf(fillK[1]), new ImageBackground(
                            findImage(keyAndValue[1].substring(6, keyAndValue[1].length()))));
                }
                break;
        }
        return width;
    }

    /**
     * Find the color in the string.
     *
     * @param s string
     * @return the color
     */
    public static Color findColor(String s) {
        ColorsParser colorsParser = new ColorsParser();
        // RGB(x,y,z) or (color
        if (s.substring(0, 3).equals("RGB")) {
            // (x,y,z)-> x,y,z
            String rgbComma = s.substring(4, s.length() - 2);
            String[] colorsRGB = rgbComma.split(",");
            return new Color(Integer.valueOf(colorsRGB[0]), Integer.valueOf(colorsRGB[1])
                    , Integer.valueOf(colorsRGB[2]));

        } else {
            return colorsParser.colorFromString(s.substring(0, s.length() - 1));
        }
    }

    /**
     * Find the image in the string.
     *
     * @param s string
     * @return image
     */
    public static Image findImage(String s) {
        BufferedImage image = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(s.substring(0, s.length()));
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.exit(0);
        }
        return image;
    }
}
