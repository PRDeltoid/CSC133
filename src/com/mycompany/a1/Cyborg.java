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
	private boolean isPlayer;
	
	private int lives;
	
	public Cyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int lives, int maxDamageLevel, boolean isPlayer) {
		super(x, y, size, color, heading, speed);
		this.maximumSpeed = maxSpeed;
		this.energyLevel = energyLevel;
		this.energyConsumptionRate = energyConsumptionRate;
		this.lives = lives;
		this.maxDamageLevel = maxDamageLevel;
		this.isPlayer = isPlayer;
		
		//Defaults
		this.damageLevel = 0;
		this.lastBaseReached = 1;
	}
	
	//Default ctor
	public Cyborg() {
		super(0, 0, 10, ColorUtil.BLUE, 0, 0);
		this.maximumSpeed = 50;
		this.energyLevel = 100;
		this.energyConsumptionRate = 5;
		this.lives = 3;
		this.maxDamageLevel = 10;
		this.isPlayer = false;
		
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
	public void updateHeading() {
		//Make sure we our heading always falls within 0-359
		setHeading((getHeading() + this.steeringDirection) % 360);
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	//this will eventually become an interface (spec. from lecture)
	public boolean isPlayer() {
		return this.isPlayer;
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
	
	private void addEnergy(int energy) {
		this.energyLevel += energy;
		
	}
	
	public int getDamageLevel() {
		return this.damageLevel;
	}
	
	public void updateEnergyLevel() {
		this.energyLevel -= this.energyConsumptionRate;
	}

	public void printInfo() {
		System.out.print("Cyborg: loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" heading=" + this.getHeading());
		System.out.print(" speed=" + this.getSpeed());
		System.out.print(" size=" + this.getSize());
		System.out.print(" maxSpeed=" + this.maximumSpeed);
		System.out.print(" steeringDirection=" + this.steeringDirection);
		System.out.print(" energyLevel=" + this.getEnergyLevel());
		System.out.print(" damage=" + this.getDamageLevel());
		System.out.print(" isPlayer=" + this.isPlayer());
		System.out.print("\n");
	}
	
	//Decrease the cyborg's speed by a fixed amount
	public void brake(int speedMod) {
		int newSpeed = Math.max(0,this.getSpeed()-speedMod);
		this.setSpeed(newSpeed);
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
		move();
		updateHeading();
		updateEnergyLevel();
		updateSpeed();
	}
	
	//Checks for death and updates the cyborg if death occurs (they lose one life)
	public boolean isDead() {
		if(energyLevel == 0 || (damageLevel >= maxDamageLevel)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isGameover() {
		if(this.lives == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void loseALife() {
		this.lives -= 1;
		//reset our damage level
		this.damageLevel = 0;
	}
	
	private void fadeColor() {
		this.color =- ColorUtil.red(25);
	}
	
}
