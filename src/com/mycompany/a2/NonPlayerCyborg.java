package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public class NonPlayerCyborg extends Cyborg {
	
	IStrategy currentStrategy;
	public void setStrategy(IStrategy strat) {
		currentStrategy = strat;
	}
	
	public void invokeStrategy() {
		currentStrategy.apply();
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
}
