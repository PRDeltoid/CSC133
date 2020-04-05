package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;

abstract public class Cyborg extends MovableGameObject implements ISteerable {
	
	protected int maximumSpeed;
	private int energyLevel;
	private int startEnergyLevel;
	private int energyConsumptionRate;
	private int damageLevel;
	private int maxDamageLevel;
	protected int lastBaseReached;
	protected int steeringDirection;
	
	public Cyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int maxDamageLevel) {
		super(x, y, size, color, heading, speed);
		this.maximumSpeed = maxSpeed;
		this.energyLevel = energyLevel;
		this.startEnergyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.maxDamageLevel = maxDamageLevel;
		
		//Defaults
		this.damageLevel = 0;
		this.lastBaseReached = 1;
	}
	
	//Default ctor
	public Cyborg() {
		super(0, 0, 10, ColorUtil.BLUE, 0, 0);
		this.maximumSpeed = 10;
		this.energyLevel = 100;
		this.energyConsumptionRate = 5;
		this.maxDamageLevel = 10;
		
		//Defaults
		this.damageLevel = 0;
		this.lastBaseReached = 1;
		
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
	protected void updateHeading() {
		//Make sure we our heading always falls within 0-359
		setHeading((getHeading() + this.steeringDirection) % 360);
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
	
	public void resetEnergyLevel() {
		this.energyLevel = startEnergyLevel;
	}
	
	private void addEnergy(int energy) {
		this.energyLevel += energy;
		
	}
	
	public int getDamageLevel() {
		return this.damageLevel;
	}

	public void resetDamageLevel() {
		//reset our damage level
		this.damageLevel = 0;
	}

	public void updateEnergyLevel() {
		this.energyLevel -= this.energyConsumptionRate;
	}

	public String toString() {
		return "Cyborg: loc=" + this.location.getX() + "," + this.location.getY() +
		" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]" +
		" heading=" + this.getHeading() +
		" speed=" + this.getSpeed() +
		" size=" + this.getSize() +
		" maxSpeed=" + this.maximumSpeed +
		" steeringDirection=" + this.steeringDirection +
		" energyLevel=" + this.getEnergyLevel() +
		" damage=" + this.getDamageLevel();
	}
	
	//Decrease the cyborg's speed by a fixed amount
	public void brake(int speedMod) {
		//Make sure speed doesn't go below 0
		int newSpeed = Math.max(0,this.getSpeed()-speedMod);
		this.setSpeed(newSpeed);
	}
	
	public void brake() {
		this.brake(5);
	}
	
	//Increase the cyborg's speed by a factor related to it's current damage level
	//The higher the damage, the lower the additional speed
	public void accelerate(int speedMod) {
		//Increase speed by speedMod
		int newSpeed = this.getSpeed()+speedMod;
		this.setSpeed(newSpeed);
		//Update speed to make sure we are within our adjusted max speed range (see updateSpeed method)
		updateSpeed();
	}
	
	public void accelerate() {
		this.accelerate(5);
	}
	
	private void updateSpeed() {
		//Find our max speed based upon our damage
		int adjustedMaxSpeed = maximumSpeed-(maximumSpeed*(damageLevel/maxDamageLevel));
		//Update our speed to be either our max adjusted speed, or our current speed (whichever is lower)
		int newSpeed = Math.min(adjustedMaxSpeed, this.getSpeed());
		this.setSpeed(newSpeed);
	}
	
	//Determine what happens when the cyborg has collided with any given GameObject
	//This may need to be moved into GameObject so that everything calculates its own collision, but we'll place it all here for now
	public void collide(GameObject collider) {
		if(collider instanceof Drone) {
			//Hardcoded 1 damage 
			this.damageLevel += 1;
			this.fadeColor();
		} else if(collider instanceof Cyborg) {
			this.damageLevel += 1;
			this.fadeColor();
		} else if(collider instanceof Base) {
			//Update the cyborg's last touched base IF THEY ARE IN SEQUENCE (ie. 1 more than previously touched base)
			setLastBase(((Base) collider).getSequenceNumber());
		} else if(collider instanceof EnergyStation) {
			//Give the cyborg all of the EnergyStation's energy
			this.addEnergy(((EnergyStation) collider).getCapacity());
			((EnergyStation) collider).emptyCapacity();
		} else {
			//unknown collider, show an error
			System.out.print("Error: An unknown collision has occurred!");
		}
		
		//recalculate our speed after collision (if we took damage, our speed will decrease)
		updateSpeed();
		
	}
	
	public void update() {
		//per the documentation, the cyborg moves THEN updates heading THEN updates its energy level
		move(20);
		updateHeading();
		updateEnergyLevel();
		updateSpeed();
	}
	
	//Checks for death or out-of-energy state
	public boolean isDead() {
		if(energyLevel == 0 || (damageLevel >= maxDamageLevel)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void fadeColor() {
		this.color =- ColorUtil.red(25);
	}
	
	public String getClassName() {
		return "Cyborg";
	}
	
}
