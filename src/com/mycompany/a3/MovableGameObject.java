package com.mycompany.a3;

public abstract class MovableGameObject extends GameObject {
	
	private int heading = 0;
	private int speed = 0;
	
	public MovableGameObject(float x, float y, int size, int color, int heading, int speed) {
		super(x, y, size, color);
		this.heading = heading;
		this.speed = speed;
	}

	public void move(int elapsedTime, int maxX, int maxY) {
		float oldX = this.location.getX();
		float oldY = this.location.getY();
		double heading = Math.toRadians(90 - getHeading());
		double deltaX = Math.cos(heading)*getSpeed()*elapsedTime;
		double deltaY = Math.sin(heading)*getSpeed()*elapsedTime;
		//Currently flooring deltas to make sure our decimal numbers arn't too long. May end up rounding to the 10s or 100s decimal place later
		float newX = (float) (oldX+Math.floor(deltaX));
		float newY = (float) (oldY+Math.floor(deltaY));
		
		//Make sure our new coordinates don't move outside the map boundary
		newX = clamp(newX, (float)maxX, 0);
		newY = clamp(newY, (float)maxY, 0);
		
		this.location.setX((float) newX);
		this.location.setY((float) newY);
	}
	
	static private float clamp(float value, float max, float min) {
		//Take the maximum of our largest possible value (the minimum of the value and max) and our minimum value
		return Math.max(min, Math.min(value, max));
	}

	protected int getSpeed() {
		return speed;
	}
	
	protected void setSpeed(int speed) {
		this.speed = speed;
	}
	
	protected int getHeading() {
		return heading;
	}
	
	protected void setHeading(int heading) {
		this.heading = heading;
	}
	
	protected abstract void updateHeading();

	
}
