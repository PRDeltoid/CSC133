package com.mycompany.a2;

import com.codename1.charts.models.Point;

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
		// TODO Auto-generated method stub
	}

	public String getStrategyName() {
		return name;
	}

}
