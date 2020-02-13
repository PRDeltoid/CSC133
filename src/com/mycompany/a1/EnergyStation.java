package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class EnergyStation extends FixedGameObject {
	
	private int capacity;
	final private double capcityMod = 0.4;
	
	public EnergyStation(float x, float y, int size, int color) {
		super(x,y,size,color);
		//capacity is determined by the size multiplied by a modifier (capcityMod)
		this.capacity = (int) Math.floor(size*this.capcityMod);
	}
	
	public void printInfo() {
		System.out.print("EnergyStation: ");
		System.out.print("loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" size=" + this.size);
		System.out.print(" capacity=" + this.capacity);
		System.out.print("\n");
	}
	
	public void update() {}
}
