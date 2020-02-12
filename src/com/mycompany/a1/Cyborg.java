package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Cyborg extends MovableGameObject implements ISteerable {
	
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	private int damageLevel;
	private int lastBaseReached;
	private int steeringDirection;
	
	private int lives;
	
	public Cyborg(int maxSpeed, int energyLevel, int energyConsumptionRate, int lives) {
		this.maximumSpeed = maxSpeed;
		this.energyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.lives = lives;
		
		//Defaults
		this.damageLevel = 0;
		this.lastBaseReached = 0;
	}

	public void move() {
		double oldX = this.location.getX();
		double oldY = this.location.getY();
		double heading = Math.toRadians(90 - (getHeading()));
		double deltaX = Math.cos(heading)*getSpeed();
		double deltaY = Math.sin(heading)*getSpeed();
		this.location.setX((float) (oldX + deltaX));
		this.location.setY((float) (oldY + deltaY));
	}


	public void steerRight() {
		//Move heading 5 degrees to the right, make sure we are within 0-359 range
		this.steeringDirection = (this.steeringDirection - 5) % 360;
	}
	
	public void steerLeft() {
		//Move heading 5 degrees to the left, make sure we are within 0-359 range
		this.steeringDirection = (this.steeringDirection + 5) % 360;
	}
	
	//Updates the cyborg's heading based upon its current steering direction
	public void updateHeading() {
		setHeading(getHeading() + this.steeringDirection);
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	//Returns the last base a cyborg reached. 
	//This will be equal to the "highest base reached"
	public int getLastBase() {
		return this.lastBaseReached;
	}
	
	public int getEnergyLevel() {
		return this.energyLevel;
	}
	
	public int getDamageLevel() {
		return this.damageLevel;
	}

	public void printInfo() {
		System.out.print("loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" heading=" + this.getHeading());
		System.out.print(" speed=" + this.getSpeed());
		System.out.print(" size=" + this.getSize());
		System.out.print(" maxSpeed=" + this.maximumSpeed);
		System.out.print(" steeringDirection=" + this.steeringDirection);
		System.out.print(" energyLevel=" + this.getEnergyLevel());
		System.out.print(" damage=" + this.getDamageLevel());
		System.out.print("\n");
	}
}
