package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface ISelectable {
	//Set the object as selected
	public void setSelected(boolean b);
	
	//Check if the object is selected
	public boolean isSelected();

	// a way to determine if a pointer is in an object
	// pPtrRelPrnt is pointer position relative to the parent origin
	// pCmpRelPrnt is the component position relative to the parent origin
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	
	void drawShape(Graphics g, Point p);
}
