package mouseRace;

import javax.swing.*;

import com.google.firebase.FirebaseApp;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the main class of the mouse game.
 * The class initialize all gui components and manage the game.
 * The class handles the start game, victory, and game loss.
 *
 */
public class MouseRace extends JFrame {
    
	private List<Element> elements = new ArrayList<>();
    private JPanel gamePanel, controlPanel, mainPanel, timePanel;
    private JLabel timerLabel;
    private static int timeScore = 0;
    private static Timer gameTimer;
    private JButton startButton;
    private static leaderBoardDB leaderBoard ;
    
        
    public MouseRace() {
        setTitle("Mouse Race Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGui();

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	startGame();
            	startButton.setEnabled(false);
            }
        });        
        add(mainPanel);
        setVisible(true);
    }
        
	/**
	 *  Initialize GUI components
	 */
    private void initGui() {
        mainPanel = new JPanel();
        timePanel = new JPanel();
        controlPanel = new JPanel();
        gamePanel = new GamePanel();

        mainPanel.setLayout(new BorderLayout());
        timePanel.setLayout(new FlowLayout());
        controlPanel.setLayout(new FlowLayout());

        gamePanel.setPreferredSize(new Dimension(600, 400)); 
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        
        timerLabel = new JLabel("Time: 0");
        startButton = new JButton("Start");
        
        timePanel.add(timerLabel);
        mainPanel.add(timePanel, BorderLayout.NORTH);
        controlPanel.add(startButton);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
	}

    /**
     * This method is triggered when clicking start button.
     * The method initialize new database for the game and it calls initializeElements to build the elements on screen.
     */
    private void startGame() {
    
    	leaderBoard = new leaderBoardDB();
    	initializeElements();
    	
    	gameTimer = new Timer(1000, new ActionListener() {
    		
    		int timer = 0;
            public void actionPerformed(ActionEvent e) {
                
            	timer++;
                timeScore = timer;
            	timerLabel.setText("Time: " + timer);   
                
                for(Element element : elements) {
                	if(element.getClass() == (ChangeElement.class)) {
            			element.move();
            		}
                	if(timer % 2 == 0 && element.getClass() == (CollectElement.class)) {
            			element.move();
                	}
                	if(timer % 3 == 0 && element.getClass() == (AvoidElement.class)) {
            			element.move();
                	}
                }   
        		gamePanel.repaint();
            }
        });
    	gameTimer.start();
    }

    /**
     * Building the game elements randomly, setting the amount of the elements is provided and can vary according
     * To the developer.
     * This method handles the mouse clicked event for game interactions.
     */
    private void initializeElements() {
    	Random x = new Random();
        Random size = new Random();
        Random type = new Random();
        int amount = 8; // choose the amount of elements in the game
        
        Helper.addElements(x,size,type,amount, elements);
    	
		gamePanel.repaint();
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Element toRemove = null;
                for (Element element : elements) {
                    if (Helper.isClicked(element, e.getX(), e.getY())) {
                        element.onClicked();
                        if(element.isCollectable()) {
                        	toRemove = element;
                        }
                    }
                }
                elements.remove(toRemove);
                gamePanel.repaint();
                
                if(Helper.getCollectscore() == (CollectElement.getNumOfInst() + ChangeElement.getNumOfInst())) {
                	victory();
                }
            }
        });
    }
    
    /**
     * When the player collects all elements, this method will stop the timer and ask for the player name.
     * After submitting the name it submits the player name and score to our DB.
     */
    private void victory() {
    	if(gameTimer != null) {
    		gameTimer.stop();
            Helper.setPlayerName(JOptionPane.showInputDialog("Enter your name:"));
            leaderBoard.submitScore(Helper.getPlayerName(), timeScore);
    	}		
	}
    
    public static void gameOver() {
		if(gameTimer != null) {
			gameTimer.stop();
			int dialogResponse = JOptionPane.showOptionDialog(null,
	                "Game Over!",
	                "\u26A0",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.INFORMATION_MESSAGE,
	                null,
	                new Object[]{"Restart", "Exit"},
	                "Restart");

	        if (dialogResponse == 0) {
	            // Restart the game
	        	CollectElement.setNumOfInst(0);
	        	ChangeElement.setNumOfInst(0);
	        	Helper.setCollectscore(0);
	            new MouseRace().startGame();
	        } else {
	            // Exit the game
	        	FirebaseApp.getInstance().delete();
	            System.exit(0);
	        }
		}
	}
                    
    /**
     * Custom GamePanel class for rendering elements which extends JPanel class.
     */
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Element element : elements) {
                element.render(g); 
            }
        }
    }
    
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MouseRace();
            }
        });
    }
}
