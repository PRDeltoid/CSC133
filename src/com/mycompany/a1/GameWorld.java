package com.mycompany.a1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class GameWorld {

	private int height;
	private int width;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Cyborg player;
	
	public GameWorld(int height, int width) {
		this.height = height;
		this.width = width;

		//Create our player
		//This is not done in init() because init() is used to reset the map (but not the player!) in case the player cyborg cannot move (is dead)
		//Cyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int lives, int maxDamageLevel, boolean isPlayer) {
		player = new Cyborg(0,0,40,ColorUtil.MAGENTA,0,10,50,100,5,3,10,true);
	}
	
	public void init() {
		//Initialize map variables
		Random rand = new Random();
		
		//If we're resetting, delete all current non-player game objects and recreate them
		objects.clear();
		objects.add(player);

		//Add our bases
		//4 bases, random location, blue color, sequenced in order
		objects.add(new Base(0,0,10,ColorUtil.BLUE,1));
		objects.add(new Base(rand.nextFloat() % height, rand.nextFloat() % width,10,ColorUtil.BLUE,2));
		objects.add(new Base(rand.nextFloat() % height, rand.nextFloat() % width,10,ColorUtil.BLUE,3));
		objects.add(new Base(rand.nextFloat() % height, rand.nextFloat() % width,10,ColorUtil.BLUE,4));

		//EnergyStation(float x, float y, int size, int color) {
		//2 energy stations with random location and size (0-49) (and thus, random capacity)
		objects.add(new EnergyStation(rand.nextFloat() % height, rand.nextFloat() % width, rand.nextInt() % 50, ColorUtil.GREEN));
		objects.add(new EnergyStation(rand.nextFloat() % height, rand.nextFloat() % width, rand.nextInt() % 50, ColorUtil.GREEN));
		
		//Drone(float x, float y, int size, int color, int heading, int speed) {
		//2 drones with random location, fixed size, and random speed (5-10) and heading (0-359)
		objects.add(new Drone(rand.nextFloat() % height, rand.nextFloat() % width, 10, ColorUtil.YELLOW, rand.nextInt() % 360, (rand.nextInt() % 5) + 5));
		objects.add(new Drone(rand.nextFloat() % height, rand.nextFloat() % width, 10, ColorUtil.YELLOW, rand.nextInt() % 360, (rand.nextInt() % 5) + 5));
	}
	
	public void printMapInfo() {
		Iterator<GameObject> iter = objects.iterator();
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
		Iterator<GameObject> objects = this.objects.iterator();
		
		//Iterate through every game object and apply relevant updates to it
		while(objects.hasNext()) {
			GameObject object = objects.next();
			object.update();
		}
	}
	
	//helper function to get a random drone from the gameobject list
	public Drone debugGetRandomDrone() {
		//Randomly pick objects until we pick a drone, basically
		Random rand = new Random();
		GameObject object = objects.get(rand.nextInt() % objects.size());
		while(!(object instanceof Drone)) {
			object = objects.get(rand.nextInt() % objects.size());
		}
		//Return our random drone
		return (Drone) object;
		
	}
	
	public EnergyStation debugGetRandomEnergyStation() {
		//Randomly pick objects until we pick a energyStation, basically
		Random rand = new Random();
		GameObject object = objects.get(rand.nextInt() % objects.size());
		while(!(object instanceof EnergyStation)) {
			object = objects.get(rand.nextInt() % objects.size());
		}
		//Return our random drone
		return (EnergyStation) object;
		
	}
}
