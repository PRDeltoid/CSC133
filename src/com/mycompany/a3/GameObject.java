package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Shape;

abstract public class GameObject extends Object implements IDrawable {
	Point location = new Point();
	int size;
	int color;
	
	private void setSize(int size) {
		this.size = size;
	}

	public GameObject(float x, float y, int size, int color) {
		this.setLocation(x,y);
		this.setSize(size);
		this.setColor(color);
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

}
