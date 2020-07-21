package scores;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * High score table.
 */
public class HighScoresTable implements java.io.Serializable {
    private List<ScoreInfo> highScores = new ArrayList<>();
    private int size;

    /**
     * @param size size of the table
     */
    public HighScoresTable(int size) {
        this.size = size;
    }

    /**
     * Add a high-score.
     *
     * @param score score
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (this.highScores.size() == this.size) {
            this.highScores.remove(this.size - 1);
        }
        this.highScores.add(rank - 1, score);
    }

    /**
     * @return table size.
     */
    public int size() {
        return this.size;

    }

    /**
     * @return the current high scores. The list is sorted such that the highest scores come first.
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * the rank of the current score.
     *
     * @param score score
     * @return rank
     */
    public int getRank(int score) {
        for (ScoreInfo currentScore : this.highScores) {
            if (score >= currentScore.getScore()) {
                return this.highScores.indexOf(currentScore) + 1;
            }
        }
        if (this.highScores.isEmpty()) {
            return 1;
        }
        return this.highScores.size() + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file. Current table data is cleared.
     *
     * @param filename file of high scores
     * @throws IOException if an error is occurred during the reading of the file
     */
    public void load(File filename) throws IOException {
        highScores.clear();
        ScoreInfo scoreInfo;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            while ((scoreInfo = (ScoreInfo) objectInputStream.readObject()) != null) {
                this.highScores.add(scoreInfo);
            }
        } catch (EOFException e) {
            return;
        } catch (FileNotFoundException e) { // Can't find file to open
            throw new FileNotFoundException();
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in the file");
        } catch (IOException e) { // Some other problem
            throw new IOException("Failed reading object");
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                throw new IOException("Failed closing file");
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename file
     * @throws IOException if an error is occurred during the reading of the file
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            for (ScoreInfo scoreInfo : this.highScores) {
                objectOutputStream.writeObject(scoreInfo);
            }
        } catch (IOException e) {
            throw new IOException("Failed saving object");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                throw new IOException("Failed closing file");
            }
        }
    }

    /**
     * Read a table from file and return it.
     *
     * @param filename score file
     * @return the table of scores
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScores = new HighScoresTable(4);
        try {
            highScores.load(filename);
        } catch (Exception e) {
            return new HighScoresTable(0);
        }
        return highScores;
    }
}
