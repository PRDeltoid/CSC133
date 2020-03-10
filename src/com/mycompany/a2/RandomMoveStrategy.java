package com.mycompany.a2;

import java.util.Random;

import com.codename1.charts.models.Point;

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
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
