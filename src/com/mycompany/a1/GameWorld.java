package com.mycompany.a1;

import java.util.ArrayList;
import java.util.Iterator;

public class GameWorld {

	private int height;
	private int width;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Cyborg player;
	
	public void init() {
		//TODO
		height = 100;
		width = 100;
		player = new Cyborg(100, 100, 100, 100); //TODO: Fix this
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
}
