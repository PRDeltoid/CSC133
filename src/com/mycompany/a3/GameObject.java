package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Shape;

abstract public class GameObject extends Object implements IDrawable, ICollider{
	Point location = new Point();
	int size;
	int color;
	BoundingBox boundingBox;
	boolean showBoundingBox = false;
	
	private void setSize(int size) {
		this.size = size;
	}

	public GameObject(float x, float y, int size, int color) {
		this.setLocation(x,y);
		this.setSize(size);
		this.setColor(color);
		boundingBox = new BoundingBox(this);
	}
	
	public int getSize() {
		return size;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int newColor) {
		this.color = newColor;
	}
	
	public Point getLocation() {
		return this.location;
	}

	public void setLocation(float x, float y) {
		this.location.setX(x);
		this.location.setY(y);
	}
	
	//Print the info of the object
	//This must be implemented in subclasses because each subclass has different stats to report
	@Override
	public abstract String toString();

	//Every object must have an update method, called whenever the game ticks
	abstract void update(int elapsedTime, GameWorld world);
	
	abstract String getClassName();

	public void SetShowBoundingBox(boolean show) {
		showBoundingBox = show;
	}
	
	public void draw(Graphics g, Point p) {
		Point drawAt = new Point();
		drawAt.setX(getLocation().getX() - getSize()/2);
		drawAt.setY(getLocation().getY() - getSize()/2);
		

		g.setColor(getColor());
		this.drawShape(g,  new Point((int)(p.getX()+drawAt.getX()), (int)(p.getY()+drawAt.getY())));

		//Draw the bounding box if enabled
		if(showBoundingBox) {
			this.boundingBox.draw(g,  p);
		}
	}
	
	public boolean collidesWith(GameObject otherObject) {
		return this.getBoundingBox().checkCollision(otherObject.getBoundingBox());
	}

	private BoundingBox getBoundingBox() {
		return this.boundingBox;
	}

}
