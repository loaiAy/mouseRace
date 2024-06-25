package mouseRace;

import java.awt.Color;
import java.awt.Graphics;

/**
 *  abstract class to define the basic properties and behaviors for the various set of elements
 *
 */
public abstract class Element {
	
    private int x, y;       // Position
    private Color color;    // Color
    private int size;       // Size
    private boolean collectable;

    public Element(int x, int y, int size, Color color) {
        setX(x);
        setY(y);
        setSize(size);
        setColor(color);
    }

    /**
     *  Abstract methods which need to be implemented by subclasses
     */
	public abstract void move();  // Method to define movement behavior

    public abstract void render(Graphics g);  // Method to render the element

    public abstract void onClicked();  // Method to define onClick behavior
    
    
    /**
     *  If the element is collectElement return true otherwise return false
     * 
     */
    public boolean isCollectable() {
    	return collectable;
    }
    
    public void setCollectable(boolean collectable) {
    	this.collectable = collectable;
    }

    /**
     *  Getters and Setters for position, size, color
     */
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private void setY(int y) {
    	this.y = y;
    }
    
    private void setX(int x) {
    	this.x = x;
    }
    
    public int getSize() {
        return this.size;
    }
    
    private void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return this.color;
    }
    
    protected void setColor(Color color) {
        this.color = color;
    }

    protected void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
}
