package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;

public class MoveToNextBaseStrategy implements IStrategy {
	String name= "MoveToNextBase";
	NonPlayerCyborg target;
	GameWorld world;
	
	public MoveToNextBaseStrategy(NonPlayerCyborg cyborg, GameWorld world) {
		target = cyborg;
		this.world = world;
	}

	public void apply() {
		Point goal = world.getBase(target.getLastBase()).getLocation();
		Point myLoc = target.getLocation();
		double idealHeading = Math.toDegrees(MathUtil.atan2(myLoc.getX()-goal.getX(), myLoc.getY()-goal.getY()));
		System.out.println(idealHeading);

		//Naive steering (just keep turning if we arn't aligned with the target)
		if(idealHeading < target.getHeading()) {
			target.steerLeft();
		} else if(idealHeading > target.getHeading()) {
			target.steerRight();
		}
	}

	public String getStrategyName() {
		return name;
	}

}
