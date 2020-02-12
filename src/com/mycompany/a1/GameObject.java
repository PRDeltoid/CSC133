package com.mycompany.a1;

import com.codename1.charts.models.Point;

abstract public class GameObject {
	Point location = new Point();
	int size;
	int color;
	
	public GameObject(float x, float y, int size, int color) {
		this.setLocation(x,y);
		this.setSize(size);
		this.setColor(color);
	}
	
	public int getSize() {
		return size;
	};
	
	private void setSize(int size) {
		this.size = size;
	}

	public int getColor() {
		return color;
	}
	public void setColor(int newColor) {
		this.color = newColor;
	}
	
	public void setLocation(float x, float y) {
		this.location.setX(x);
		this.location.setY(y);
	}
	
	abstract void printInfo();

}
