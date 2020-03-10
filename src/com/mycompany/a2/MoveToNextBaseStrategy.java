package com.mycompany.a2;

import com.codename1.charts.models.Point;

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
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
