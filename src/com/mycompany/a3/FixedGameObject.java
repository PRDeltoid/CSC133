package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class FixedGameObject extends GameObject implements ISelectable {
	boolean selected;

	//FixedGameObject constructor is the same as GameObject, so this feels kind of useless...
	public FixedGameObject(float x, float y, int size, int color) {
		super(x, y, size, color);
	}
	
	//Set the object as selected
	public void setSelected(boolean b) {
		this.selected = b;
	}
	
	//Check if the object is selected
	public boolean isSelected() {
		return this.selected;
	}

	// a way to determine if a pointer is in an object
	// pPtrRelPrnt is pointer position relative to the parent origin
	// pCmpRelPrnt is the component position relative to the parent origin
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		Point drawAt = new Point();
		drawAt.setX(getLocation().getX() - getSize()/2);
		drawAt.setY(getLocation().getY() - getSize()/2);

		int px = (int) pPtrRelPrnt.getX(); // pointer location relative to
		int py = (int) pPtrRelPrnt.getY(); // parents origin

		int xLoc = (int) (pCmpRelPrnt.getX()+ drawAt.getX());// shape location relative
		int yLoc = (int) (pCmpRelPrnt.getY()+ drawAt.getY());// to parents origin

		if ( (px >= xLoc) && (px <= xLoc+size)
			&& (py >= yLoc) && (py <= yLoc+size) ) {
			return true;
		} else {
			return false;
		}
	}
}
