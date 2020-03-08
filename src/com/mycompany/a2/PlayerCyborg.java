package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public class PlayerCyborg extends Cyborg {
	static PlayerCyborg player;
	
	public PlayerCyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int maxDamageLevel) {
		super(x, y, size, color, heading, speed, maxSpeed, energyLevel, energyConsumptionRate, maxDamageLevel);
	}

	//Singleton 
	public static PlayerCyborg getPlayer() {
		if (player == null) {
			player = new PlayerCyborg(0,0,20,ColorUtil.MAGENTA,0,10,50,100,5,10);
		}
		return player;
	}

}
