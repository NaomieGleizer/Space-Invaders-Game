package scores;

/**
 * Score info, name and score of the player.
 */
public class ScoreInfo implements java.io.Serializable {
    private String playerName;
    private int playerScore;

    /**
     * Constructor.
     *
     * @param name  name of player
     * @param score score
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;

    }

    /**
     * @return the name
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return this.playerScore;
    }
}
