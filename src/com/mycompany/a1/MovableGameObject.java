package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public abstract class MovableGameObject extends GameObject {
	
	private int heading = 0;
	private int speed = 0;
	
	public void move() {
		double oldX = this.location.getX();
		double oldY = this.location.getY();
		double heading = Math.toRadians(90 - getHeading());
		double deltaX = Math.cos(heading)*getSpeed();
		double deltaY = Math.sin(heading)*getSpeed();
		this.location.setX((float) (oldX + deltaX));
		this.location.setY((float) (oldY + deltaY));
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getHeading() {
		return heading;
	}
	
	public void setHeading(int heading) {
		this.heading = heading;
	}
	
}
