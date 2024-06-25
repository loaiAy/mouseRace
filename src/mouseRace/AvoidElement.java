package mouseRace;

import java.awt.Color;
import java.awt.Graphics;


/**
 * This is the subclass for avoid element. clicking this element results a game loss.
 * Moves left and right every 3 seconds 
 *
 */
public class AvoidElement extends Element {

	private int delta = 45;
	
	public AvoidElement(int x, int y, int size) {
		super(x, y, size, Color.RED);
		this.setCollectable(false);
	}

	/**
	 * Moves the element left and right by delta to change its position
	 */
	@Override
	public void move() {
		delta = delta*(-1);
		this.setPosition(getX() + delta, getY());
	}

	/**
	 * Render as a red circle
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(getColor());
        g.fillOval(this.getX(), this.getY(), this.getSize(), this.getSize());  
	}
	
	/**
	 * Clicking this element triggers his onClicked method
	 * Triggers a game over
	 */
	@Override
	public void onClicked() {
		MouseRace.gameOver();
	}
}
