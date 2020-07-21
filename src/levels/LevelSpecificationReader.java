package levels;

import sprites.Block;
import sprites.Velocity;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * create the levels from a file.
 */
public class
LevelSpecificationReader {
    private List<LevelInformation> levels = new ArrayList<>();

    /**
     * @param reader levels specifications file
     * @return a list of levels
     */
    public List<LevelInformation> fromReader(Reader reader) {
        BufferedReader br = new BufferedReader(reader);
        LevelInformation level;
        String st, blockFile = null;
        String[] line;
        int fieldsCounter = 0;
        int blockStartX = 0, blockStartY = 0, rowHeight = 0;
        try {
            while ((st = br.readLine()) != null) {
                if (st.isEmpty() || st.charAt(0) == '#' || st.equals("START_LEVEL") || st.equals("END_LEVEL")) {
                    continue;
                }
                level = new CreateLevel(800, 600);
                while (!st.equals("START_BLOCKS")) {
                    line = st.split(":");
                    fieldsCounter++;
                    String key = line[0];
                    switch (key) {
                        case "level_name":
                            level.setLevelName(line[1]);
                            break;
                        case "ball_velocities":
                            List<Velocity> velocities = new ArrayList<>();
                            String[] angleAndSpeed = line[1].split(" ");
                            for (String s : angleAndSpeed) {
                                String[] vel = s.split(",");
                                velocities.add(Velocity.fromAngleAndSpeed(Integer.valueOf(vel[0])
                                        , Integer.valueOf(vel[1])));
                            }
                            level.setBallsVelocities(velocities);
                            break;
                        case "background":
                            // image(image.png) or color(color)
                            String data = line[1].substring(6, line[1].length());
                            if (line[1].substring(0, 5).equals("color")) {
                                level.setBackground(new ColorBackground(BlocksDefinitionReader.findColor(data)));
                            } else { //image
                                level.setBackground(new ImageBackground(BlocksDefinitionReader.findImage(data)));
                            }
                            break;
                        case "paddle_speed":
                            level.setPaddleSpeed(Integer.valueOf(line[1]));
                            break;
                        case "paddle_width":
                            level.setPaddleWidth(Integer.valueOf(line[1]));
                            break;
                        case "block_definitions":
                            blockFile = line[1];
                            break;
                        case "blocks_start_x":
                            blockStartX = Integer.valueOf(line[1]);
                            break;
                        case "blocks_start_y":
                            blockStartY = Integer.valueOf(line[1]);
                            break;
                        case "row_height":
                            rowHeight = Integer.valueOf(line[1]);
                            break;
                        case "num_blocks":
                            level.setNumOfBlocksToRemove(Integer.valueOf(line[1]));
                            break;
                        default:
                            fieldsCounter--;
                            break;
                    }
                    st = br.readLine();
                }
                if (fieldsCounter != 10) {
                    System.out.println("There are some missing fields for the level");
                    System.exit(0);
                }
                if (blockFile == null) {
                    System.out.println("There is no block Definition file");
                    System.exit(0);
                }
                fieldsCounter = 0;
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockFile);
                Reader blockReader = new InputStreamReader(is);
                BlocksFromSymbolsFactory blockSymbols = BlocksDefinitionReader.fromReader(blockReader);
                // create blocks
                level.setBlocks(getBlocks(br, blockSymbols, blockStartX, blockStartY, rowHeight));
                levels.add(level);
            }
        } catch (Exception e) {
            System.out.println("Failed reading file");
            System.exit(0);
        }
        return this.levels;
    }

    /**
     * get the blocks.
     *
     * @param br           bufferReader
     * @param blockSymbols symbol from the block factory
     * @param x            x location of the block
     * @param y            y location of the block
     * @param rowHeight    height of the rows
     * @return a list of blocks
     */
    public List<Block> getBlocks(BufferedReader br, BlocksFromSymbolsFactory blockSymbols, int x
            , int y, int rowHeight) {
        List<Block> blocks = new ArrayList<>();
        Block b;
        int positionX = x;
        try {
            String st = br.readLine();
            while (!st.equals("END_BLOCKS")) {
                for (int i = 0; i < st.length(); i++) {
                    String symbol = st.substring(i, i + 1);
                    if (blockSymbols.isBlockSymbol(symbol)) {
                        b = blockSymbols.getBlock(symbol, positionX, y);
                        blocks.add(b);
                        positionX += b.getCollisionRectangle().getWidth();
                    } else if (blockSymbols.isSpaceSymbol(symbol)) {
                        positionX += blockSymbols.getSpaceWidth(symbol);
                    }
                }
                positionX = x;
                y += rowHeight;
                st = br.readLine();
            }
        } catch (Exception e) {
            System.exit(0);
        }
        return blocks;
    }
}
