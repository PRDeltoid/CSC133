package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class BoundingBox {
	private GameObject target;
	
	public BoundingBox(GameObject target) {
		this.target = target;
	}
	
	public boolean checkCollision(BoundingBox otherBox) {
		//Check for overlap
		if((this.getRight() < otherBox.getLeft()) || (this.getLeft()> otherBox.getRight())) {
			return false;
		} else if((otherBox.getTop() < this.getBottom()) || (this.getTop() < otherBox.getBottom())) {
			return false;
		} else {
			return true;
		}

	}
	
	public int getLeft() {
		return (int) (target.getLocation().getX()-target.getSize()/2);
	}

	public int getRight() {
		return (int) (target.getLocation().getX()+target.getSize()/2);
	}
	
	public int getTop() {
		return (int) (target.getLocation().getY()-target.getSize()/2);
		
	}

	public int getBottom() {
		return (int) (target.getLocation().getY()+target.getSize()/2);
		
	}
	
	public void draw(Graphics g, Point p) {
		Point drawAt = new Point();
		//Get the upper-left corner relative to our center
		drawAt.setX(target.getLocation().getX() - target.getSize()/2);
		drawAt.setY(target.getLocation().getY() - target.getSize()/2);
		

		//Draw our shape
		g.setColor(ColorUtil.MAGENTA);
		//TODO: Make circle
		g.drawRect((int)(p.getX()+drawAt.getX()), (int)(p.getY()+drawAt.getY()), target.getSize(), target.getSize(), 2);
	}	

	

}
