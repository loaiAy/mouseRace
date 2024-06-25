package mouseRace;


import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * This class is responsible for the interactions with the fire base DB.
 * The class handles displaying the leader board and updating it.
 */
public class leaderBoardDB {
	
	private static FirebaseDatabase database;
	private final String path = "/C:/Users/luiso/Downloads/mouseracegame-firebase-adminsdk-fouqx-a3ad5e4b9d.json"; // the json file includes api key
																											 	   // and documentation
	/**
	 * Initializes the fire base DB.
	 */
	public leaderBoardDB() {
    	if(FirebaseApp.getApps().isEmpty()) {
    		try {
            	FileInputStream refreshToken = new FileInputStream(path);
            	FirebaseOptions options = FirebaseOptions.builder()
            	    .setCredentials(GoogleCredentials.fromStream(refreshToken))
            	    .setDatabaseUrl("https://mouseracegame-default-rtdb.firebaseio.com/") //The DB url Address
            	    .build();
            	FirebaseApp.initializeApp(options);
                database = FirebaseDatabase.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	else {
    		database = FirebaseDatabase.getInstance();
    	}
    }
    
	/**
	 * Submits the player name and score to the DB.
	 */
    public void submitScore(String playerName, int score) {
        DatabaseReference leaderboardRef = database.getReference("leaderboard");
        DatabaseReference newScoreRef = leaderboardRef.push();
        newScoreRef.setValueAsync(new PlayerScore(playerName, score));
        displayTopScores();
    }
    
    /**
     * Getting the top 3 score from the DB and displaying them to the player.
     */
    public void displayTopScores() {
        DatabaseReference leaderboardRef = database.getReference("leaderboard");
        Query topScoresQuery = leaderboardRef.orderByChild("score").limitToFirst(3);

        topScoresQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder topScores = new StringBuilder("\nTop Scores:\n");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlayerScore score = snapshot.getValue(PlayerScore.class);
                    topScores.append(score.getPlayerName()).append(": ").append(score.getScore()).append("\n");
                }
                JOptionPane.showMessageDialog(null, topScores.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error retrieving top scores: " + databaseError.getMessage());
            }
        });
    }
}
