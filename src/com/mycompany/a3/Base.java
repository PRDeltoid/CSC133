package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Base extends FixedGameObject {
	private int sequenceNumber;
	
	public Base(float x, float y, int size, int color, int sequenceNumber) {
		super(x,y,size,color);
		this.sequenceNumber = sequenceNumber;
	}
	
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setColor() { }
	
	public String toString() {
		return "Base:" +
		" loc=" + this.location.getX() + "," + this.location.getY() +
		" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]" +
		" size=" + this.size +
		" seqNum=" + this.sequenceNumber;
	}

	public void update(int elapsedTime, GameWorld world) {}

	public String getClassName() {
		return "Base " + this.getSequenceNumber();
	}
	
	public void drawShape(Graphics g, Point p) {	
		//Get the absolute top-left coordinate of the entity
		int baseX = (int)p.getX();
		int baseY = (int)p.getY();
		//Get the points of our triangle
		int[] xPoints = {baseX, baseX+size, baseX+(size/2)};
		int[] yPoints = {baseY, baseY, baseY+size};
		int nPoints = 3;

		if(isSelected()) {
			g.drawPolygon(xPoints, yPoints, nPoints);
		} else {
			g.fillPolygon(xPoints, yPoints, nPoints);
		}
	}
	public void handleCollision(GameObject collider) {
	}
}
