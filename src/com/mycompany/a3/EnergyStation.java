package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class EnergyStation extends FixedGameObject {
	
	private int capacity;
	final private double capacityMod = 0.4;
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public EnergyStation(float x, float y, int size, int color) {
		super(x,y,size,color);
		//capacity is determined by the size multiplied by a modifier (capacityMod)
		this.capacity = (int) Math.floor(size*this.capacityMod);
	}
	
	public String toString() {
		return "EnergyStation: " +
		"loc=" + this.location.getX() + "," + this.location.getY() +
		" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]" +
		" size=" + this.size +
		" capacity=" + this.capacity;
	}
	
	public void update(int elapsedTime, GameWorld world) {}
	
	//Drain the energy capacity of the EnergyStation
	public void emptyCapacity() {
		this.capacity = 0;
	}

	public String getClassName() {
		return "EnergyStation";
	}
	
	public void drawShape(Graphics g, Point p) {
		if(isSelected()) {
			g.drawArc((int)p.getX(), (int)p.getY(), size/2, size/2, 0, 360);	
		} else {
			g.fillArc((int)p.getX(), (int)p.getY(), size/2, size/2, 0, 360);	
		}
	}
	
	public void handleCollision(GameObject collider) {
		if(collider instanceof Cyborg) {
			((EnergyStation) collider).emptyCapacity();
		} else {
			//unknown collider, show an error
			System.out.print("Error: An unknown collision has occurred!");
		}
	}
}
