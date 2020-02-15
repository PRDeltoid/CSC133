package com.mycompany.a1;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class Drone extends MovableGameObject {
	
	public Drone(float x, float y, int size, int color, int heading, int speed) {
		super(x,y,size,color,heading,speed);
	}

	//Drones cannot set color after creation
	public void setColor() {}

	public void printInfo() {
		System.out.print("Drone: loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" heading=" + this.getHeading());
		System.out.print(" speed=" + this.getSpeed());
		System.out.print(" size=" + this.getSize());
		System.out.print("\n");
	}

	protected void updateHeading() {
		Random rand = new Random();
		//Drones randomly update their heading to be 5 degrees in either direction
		//generate a random number between -5 and 5
		int headingAdjustment = rand.nextInt(10) - 5;
		
		//update drone heading
		this.setHeading(this.getHeading() + headingAdjustment);
	}

	public void update() {
		move();
		updateHeading();
	}

	public String getClassName() {
		return "Drone";
	}
	
}
