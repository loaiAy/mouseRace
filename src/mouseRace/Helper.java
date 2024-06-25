package mouseRace;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Helper class to manage different game states and utility methods.
 * 
 *
 */
public class Helper {

	private static int collectScore;
    private static String playerName;
    private final static int sizeRange = 40;
    private final static int buffer = 50;

    /**
     * Method return true if the mouse selected area coordinates is within the bounds of the element, false otherwise.
     * 
     */
	public static boolean isClicked(Element element, int mouseX, int mouseY) {
		int xCoord = element.getX();
		int yCoord = element.getY();
		int elementSize = element.getSize();
		
    	int rangeXend = xCoord + elementSize;
    	int rangeYend = yCoord + elementSize;

        return mouseX >= xCoord && mouseX <= rangeXend
            && mouseY >= yCoord && mouseY <= rangeYend;
	}

	/**
	 * Add a given amount of elements to the given list of elements.
	 * the type of the element determined randomly as well the element size, x and y.
	 */
	public static void addElements(Random x, Random size, Random type, int amount, List<Element> elements) {
		for(int i = 0; i < amount; i++) {
    		int xCoordinates = x.nextInt(550)+buffer;
    		int yCoordinates = x.nextInt(350)+buffer;
        	switch(type.nextInt(3)) {
        		case 0:
        			elements.add(new CollectElement(xCoordinates, yCoordinates, size.nextInt(sizeRange)+buffer));
        			break;
        		case 1:
        			elements.add(new AvoidElement(xCoordinates, yCoordinates, size.nextInt(sizeRange)+buffer));
        			break;
        		case 2:
        			elements.add(new ChangeElement(xCoordinates, yCoordinates, size.nextInt(sizeRange)+buffer));
        			break;
        	}
        }
	}

	/**
	 * This method returns the color of the element depending on his current state. 
	 */
	public static Color changeColor(String state) {
		return state.equals("CollectElement") ? Color.RED : Color.GREEN;
	}
	
	public static int getCollectscore() {
		return collectScore;
	}

	public static void setCollectscore(int collectScore) {
		Helper.collectScore = collectScore;
	}

	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String playerName) {
		Helper.playerName = playerName;
	}
}
