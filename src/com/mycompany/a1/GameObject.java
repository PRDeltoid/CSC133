package com.mycompany.a1;

import com.codename1.ui.geom.Point;

abstract public class GameObject {
	Point location;
	int size;
	int color;
	
	public int getSize() {
		return size;
	};
	public int getColor() {
		return color;
	}
	public void setColor(int newColor) {
		this.color = newColor;
	}

}
