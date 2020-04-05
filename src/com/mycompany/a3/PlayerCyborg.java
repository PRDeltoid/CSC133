package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

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
	
	public void draw(Graphics g, Point p) {
		Point drawAt = new Point();
		drawAt.setX(getLocation().getX() - getSize()/2);
		drawAt.setY(getLocation().getY() - getSize()/2);
		

		g.setColor(getColor());
		//TODO: Make circle
		g.fillRect((int)(p.getX()+drawAt.getX()), (int)(p.getY()+drawAt.getY()), size, size);
	}
}
