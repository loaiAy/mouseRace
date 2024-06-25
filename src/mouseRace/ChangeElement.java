package mouseRace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

/**
 * This element have two forms, the initial form is a Collectelement and it changes to Avoidelement every 5 seconds
 * With changing forms the element change its color and shape respectively.
 * 
 */
public class ChangeElement extends Element {

	private double rotate = 0;
	private String state;
	private Timer timer;
	private static int numOfInst = 0;
	private final double angle = 0.08;

	public ChangeElement(int x, int y, int size) {
		super(x, y, size, Color.green);
		this.setColor(Color.green);
		this.state = "CollectElement";
		numOfInst++;
		
		timer = new Timer(5000, (ActionListener) new ActionListener() {		// Timer set to make action every 5 
			public void actionPerformed(ActionEvent e) {					// Which result changing form
				setColor(Helper.changeColor(state));
				state = state.equals("AvoidElement") ? "CollectElement" : "AvoidElement";
            }
        });
		timer.start();
	}

	/**
	 * This method rotates the element clockwise continuously.
	 */
	@Override
	public void move() {
		rotate += angle; // Update angle (adjust the value for rotation speed)
        if (rotate >= (2 * Math.PI)) {
        	rotate = 0; // Reset angle to avoid overflow
        }
	}

	/**
	 * Renders the element clockwise as a square while changing its color every 5 seconds.
	 * 
	 */
	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.getColor());
        AffineTransform originalTransform = g2.getTransform();
        g2.rotate(rotate, this.getX() + this.getSize() / 2, this.getY() + this.getSize() / 2);
        g2.fillRect(this.getX(), this.getY(), this.getSize(), this.getSize()); 
        g2.setTransform(originalTransform);
	}

	/**
	 * When clicking this element this method triggered.
	 * If the element is collectable(form of collect element) it increase the overall score by 1.
	 * If the element is not collectable(form of avoid element) it triggers a game over. 
	 */
	@Override
	public void onClicked() {
		if(this.state.equals("CollectElement")) {
			Helper.setCollectscore(Helper.getCollectscore() + 1);
			this.setCollectable(true);
		}
		else {
			this.setCollectable(false);
			MouseRace.gameOver();
		}
	}
	
	/**
	 * Getter and setter for the number of instances of change element.
	 * 
	 */
	public static int getNumOfInst() {
		return numOfInst;
	}

	public static void setNumOfInst(int numOfInst) {
		ChangeElement.numOfInst = numOfInst;
	}
}
