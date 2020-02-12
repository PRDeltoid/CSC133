package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Drone extends MovableGameObject {

	public void setColor() {}

	public void printInfo() {
		System.out.print("loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" heading=" + this.getHeading());
		System.out.print(" speed=" + this.getSpeed());
		System.out.print(" size=" + this.getSize());
		System.out.print("\n");
	}

}
