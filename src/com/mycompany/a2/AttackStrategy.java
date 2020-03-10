package com.mycompany.a2;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;

public class AttackStrategy implements IStrategy {
	String name= "Attack";
	NonPlayerCyborg target;
	PlayerCyborg player;
	
	public AttackStrategy(NonPlayerCyborg cyborg) {
		target = cyborg;
		player = PlayerCyborg.getPlayer();
	}

	public void apply() {
		Point goal = player.getLocation();
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
