package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Cyborg extends MovableGameObject implements ISteerable {
	
	private int maximumSpeed;
	private int energyLevel;
	private int energyConsumptionRate;
	private int damageLevel;
	private int maxDamageLevel;
	private int lastBaseReached;
	private int steeringDirection;
	
	private int lives;
	
	public Cyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int lives, int maxDamageLevel) {
		super(x, y, size, color, heading, speed);
		this.maximumSpeed = maxSpeed;
		this.energyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.lives = lives;
		this.maxDamageLevel = maxDamageLevel;
		
		//Defaults
		this.damageLevel = 0;
		this.lastBaseReached = 0;
	}

	public void steerRight() {
		//Move steering wheel 5 degrees to the right, make sure we are within our range
		this.steeringDirection = Math.max(-40, this.steeringDirection - 5);
	}
	
	public void steerLeft() {
		//Move steering wheel 5 degrees to the left, make sure we are within our range
		this.steeringDirection = Math.min(40, this.steeringDirection + 5);
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

	//Updates the cyborg's last touched base ONLY IF it has touched the prior base in the sequence
	public void setLastBase(int lastBaseSequenceNumber) {
		//Only update the base number if its sequenceNumber is exactly one greater than the previously visited base
		if(lastBaseSequenceNumber == getLastBase()+1) {
			this.lastBaseReached = lastBaseSequenceNumber;
		}
	}
	
	public int getEnergyLevel() {
		return this.energyLevel;
	}
	
	public int getDamageLevel() {
		return this.damageLevel;
	}
	
	public void updateEnergyLevel() {
		this.energyLevel -= this.energyConsumptionRate;
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
	
	public void brake() {
		this.setSpeed(Math.max(0,this.getSpeed()-5));
	}
	
	public void accelerate() {
		//TODO Double-check this
		int newSpeed = Math.min(maximumSpeed, this.getSpeed()-(this.getSpeed())*(damageLevel/maxDamageLevel));
		this.setSpeed(newSpeed);
	}
}
