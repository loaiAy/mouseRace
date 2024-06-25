package mouseRace;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The CollectElement class represents the elements that can be collected and increase the player score.
 * This element moves up and down every two seconds and its color is set to be green.
 *
 */
public class CollectElement extends Element {

	private int delta = 40;
	private static int numOfInst = 0;
	
	public CollectElement(int x, int y, int size) {
		super(x, y, size, Color.GREEN);
		numOfInst++;
		this.setCollectable(true);
	}

	/**
	 * This method triggered every two seconds to move the element up and down.
	 */
	@Override
	public void move() {
		delta = delta*(-1);
		this.setPosition(getX(), getY()+delta);
	}

	/**
	 * Rendering an green rectangle.
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(getColor());
        g.fillRect(this.getX(), this.getY(), this.getSize(), this.getSize()/2); 
		
	}

	/**
	 * Clicking these elements triggers this method which leads to score increment.
	 */
	@Override
	public void onClicked() {
		Helper.setCollectscore(Helper.getCollectscore() + 1);
	}

	/**
	 * Getter and setter for the number of instances of collect element. 
	 */
	public static int getNumOfInst() {
		return numOfInst;
	}

	public static void setNumOfInst(int numOfInst) {
		CollectElement.numOfInst = numOfInst;
	}

}
