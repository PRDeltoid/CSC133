package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Base extends FixedGameObject {
	private int sequenceNumber;

	public void setColor() { }
	
	public void printInfo() {
		System.out.print("Base:");
		System.out.print(" loc=" + this.location.getX() + "," + this.location.getY());
		System.out.print(" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]");
		System.out.print(" size=" + this.size);
		System.out.print(" seqNum=" + this.sequenceNumber);
		System.out.print("\n");
	}

}
