package com.mycompany.a2;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;

public class RandomMoveStrategy implements IStrategy {
	String name= "RandomMove";
	NonPlayerCyborg target;
	int mapHeight;
	int mapWidth;
	
	public RandomMoveStrategy(NonPlayerCyborg cyborg, GameWorld world) {
		target = cyborg;
		mapHeight = world.getHeight();
		mapWidth = world.getWidth();
	}

	public void apply() {
		Random rand = new Random();
		Point goal = new Point(rand.nextInt(mapHeight), rand.nextInt(mapWidth));
		Point myLoc = target.getLocation();
		target.steerLeft();
		double idealHeading = Math.toDegrees(MathUtil.atan2(myLoc.getX()-goal.getX(), myLoc.getY()-goal.getY()));

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
