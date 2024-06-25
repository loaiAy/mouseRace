package mouseRace;

/**
 * This class represents the player score and his name.
 *
 */
public class PlayerScore {
	
	private int score;
    private String playerName;

    public PlayerScore() {
    	
    }
    
	public PlayerScore(String playerName, int score) {
		this.score = score;
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}	
}
