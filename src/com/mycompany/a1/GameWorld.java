package com.mycompany.a1;

import java.util.ArrayList;
import java.util.Iterator;

import com.codename1.charts.util.ColorUtil;

public class GameWorld {

	private int height;
	private int width;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Cyborg player;
	
	public void init() {
		//TODO
		height = 100;
		width = 100;
		player = new Cyborg(0,0,40,0,0,ColorUtil.MAGENTA,50,100,5,3,10);
		objects.add(player);
	}
	
	public void printMapInfo() {
		Iterator iter = objects.iterator();
		while(iter.hasNext()) {
			GameObject object = (GameObject) iter.next();
			object.printInfo();
		}
	}
	
	public Cyborg getPlayer() {
		return this.player;
	}
	
	//Update function is called whenever we want to update the map/game objects' state (ie. every game tick)
	public void update() {
		
	}
}
