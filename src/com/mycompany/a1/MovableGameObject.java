package com.mycompany.a1;

public abstract class MovableGameObject extends GameObject {
	
	private int heading = 0;
	private int speed = 0;
	
	public MovableGameObject(float x, float y, int size, int color, int heading, int speed) {
		super(x, y, size, color);
		this.heading = heading;
		this.speed = speed;
	}

	//TODO: Fix this?
	public void move() {
		double oldX = this.location.getX();
		double oldY = this.location.getY();
		double heading = Math.toRadians(90 - getHeading());
		double deltaX = Math.cos(heading)*getSpeed();
		double deltaY = Math.sin(heading)*getSpeed();
		//Debug output for testing the method
		System.out.println("Inside move() method: Old [X,Y]=[" + oldX + "," + oldY + "], heading="+heading+", New [X,Y]=["+oldX+Math.floor(deltaX)+","+oldY+Math.floor(deltaY)+"]");
		this.location.setX((float) (oldX + Math.floor(deltaX)));
		this.location.setY((float) (oldY + Math.floor(deltaY)));
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
