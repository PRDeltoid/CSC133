package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Base extends FixedGameObject {
	private int sequenceNumber;
	
	public Base(float x, float y, int size, int color, int sequenceNumber) {
		super(x,y,size,color);
		this.sequenceNumber = sequenceNumber;
	}
	
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setColor() { }
	
	public String toString() {
		return "Base:" +
		" loc=" + this.location.getX() + "," + this.location.getY() +
		" color=[" + ColorUtil.red(this.color) + "," + ColorUtil.blue(this.color) + "," + ColorUtil.green(this.color) + "]" +
		" size=" + this.size +
		" seqNum=" + this.sequenceNumber;
	}

	public void update() {}

	public String getClassName() {
		return "Base " + this.getSequenceNumber();
	}
	
}
