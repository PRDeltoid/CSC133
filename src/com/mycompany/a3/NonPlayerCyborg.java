package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

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
	
	public void switchStrategy(GameWorld world) {
		//Increase lastBaseReached every time we set a strat
		lastBaseReached++;
		Random rand = new Random();
		switch(rand.nextInt(3)) {
		case(0):
			currentStrategy = new MoveToNextBaseStrategy(this, world);
			break;
		case(1):
			currentStrategy = new AttackStrategy(this);
			break;
		case(2):
			currentStrategy = new RandomMoveStrategy(this, world);
			break;
		}
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
	
	public void draw(Graphics g, Point p) {
		Point drawAt = new Point();
		//Get the upper-left corner relative to our center
		drawAt.setX(getLocation().getX() - getSize()/2);
		drawAt.setY(getLocation().getY() - getSize()/2);
		

		//Draw our shape
		g.setColor(getColor());
		//TODO: Make circle
		g.drawRect((int)(p.getX()+drawAt.getX()), (int)(p.getY()+drawAt.getY()), size, size, 5);
	}
}
