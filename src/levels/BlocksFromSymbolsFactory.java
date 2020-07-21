package levels;

import java.util.Map;
import java.util.TreeMap;

import sprites.Block;

/**
 * Blocks From Symbols Factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new TreeMap<>();
    private Map<String, BlockCreator> blockCreators = new TreeMap<>();

    /**
     * add a spacer symbol.
     *
     * @param s     symbol
     * @param width width of spacer
     */
    public void addSpaceSymbol(String s, int width) {
        this.spacerWidths.put(s, width);
    }

    /**
     * add a block symbol.
     *
     * @param s     symbol
     * @param block a block
     */
    public void addBlockSymbol(String s, BlockCreator block) {
        this.blockCreators.put(s, block);
    }

    /**
     * @param s symbol
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * @param s symbol
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * @param s    symbol
     * @param xPos x position
     * @param yPos y position
     * @return the block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int xPos, int yPos) {
        return this.blockCreators.get(s).create(xPos, yPos);
    }

    /**
     * @param s symbol
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
