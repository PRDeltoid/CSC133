package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public class NonPlayerCyborg extends Cyborg {
	IStrategy currentStrategy;

	public NonPlayerCyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int maxDamageLevel) {
		super(x, y, size, color, heading, speed, maxSpeed, energyLevel, energyConsumptionRate, maxDamageLevel);
	}
	
	@Override
	public void update() {
		//TODO Check this
		invokeStrategy();
		super.update();
	}

	public void setStrategy(IStrategy strat) {
		currentStrategy = strat;
	}
	
	public void invokeStrategy() {
		//If a strategy has been set, invoke it!
		if(currentStrategy != null) {
			currentStrategy.apply();
		}
	}
	
	public void switchStrategy() {
		//Increase lastBaseReached every time we set a strat
		lastBaseReached++;
		//TODO
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
		" damage=" + this.getDamageLevel() +
		" strategy=" + this.currentStrategy.getStrategyName();
	}
}
