package com.mycompany.a2;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class GameWorld extends Observable {

	private int height; //currently "X"
	private int width;	//currently "Y"
	private GameObjectCollection objects = new GameObjectCollection(); //ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private int elapsedTicks = 0;
	private boolean gameOver = false;
	private int startLives = 3;
	private int remainingLives = startLives;
	private boolean sound = false;
	
	//helper function to make sure a given value falls within a range
	//this is very useful if we need to make sure a given X or Y value is within our map
	//if the X or Y value is outside of our clamp, we make it the maximum or minimum value (depending on if it is under or over our clamp values respectively)
	static private float clamp(float value, float max, float min) {
		//Take the maximum of our largest possible value (the minimum of the value and max) and our minimum value
		return Math.max(min, Math.min(value, max));
		
	}
	
	//Checks if a given unit is within a given boundary
	//Commonly used for bounds checking
	private boolean isInsideBoundary(GameObject object) {
		return ((0 <= object.getLocation().getX()) &&
				(0 <= object.getLocation().getY()) && 
				(height >= object.getLocation().getX()) && 
				(width >= object.getLocation().getY()));
	}
	
	//Checks if a given object is outside of the game boundaries
	private boolean isOutOfBounds(GameObject object) {
		return !isInsideBoundary(object);
	}

	//Nudges an object back into the game world
	private void nudgeInsideBoundary(GameObject object) {
		object.setLocation(clamp(object.getLocation().getX(),0,height), clamp(object.getLocation().getY(),0,width));
	}

	public int getElapsedTicks() {
		return elapsedTicks;
	}
	
	public int getRemainingLives() {
		return remainingLives;
	}
	
	public boolean getSound() {
		return sound;
	}

	public boolean isGameover() {
		return (remainingLives==0);
	}

	//Ctor
	public GameWorld(int height, int width) {
		this.height = height;
		this.width = width;
	}
	
	public void init() {
		//Initialize map variables
		Random rand = new Random();
		
		//If we're resetting, delete all current non-player game objects and recreate them
		objects.clear();
		objects.add(PlayerCyborg.getPlayer());
		//Move player back to "start". Currently hardcoded at 0,0 (with Base 1, seen below)
		PlayerCyborg.getPlayer().setLocation(0, 0);

		//Add our bases
		//4 bases, random location, blue color, sequenced in order
		objects.add(new Base(0,0,10,ColorUtil.BLUE,1));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),10,ColorUtil.BLUE,2));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),10,ColorUtil.BLUE,3));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),10,ColorUtil.BLUE,4));

		//EnergyStation(float x, float y, int size, int color) {
		//2 energy stations with random location and size (1-50) (and thus, random capacity)
		objects.add(new EnergyStation(rand.nextInt(height), rand.nextInt(width), rand.nextInt(49)+1, ColorUtil.GREEN));
		objects.add(new EnergyStation(rand.nextInt(height), rand.nextInt(width), rand.nextInt(49)+1, ColorUtil.GREEN));
		
		//Drone(float x, float y, int size, int color, int heading, int speed) {
		//2 drones with random location, fixed size, and random speed (5-10) and heading (0-359)
		objects.add(new Drone(rand.nextInt(height), rand.nextInt(width), 10, ColorUtil.YELLOW, rand.nextInt(360), rand.nextInt(5) + 5));
		objects.add(new Drone(rand.nextInt(height), rand.nextInt(width), 10, ColorUtil.YELLOW, rand.nextInt(360), rand.nextInt(5) + 5));
		
		setChanged();
		notifyObservers();
	}
	
	public void printMapInfo() {
		IIterator iter = objects.getIterator();
		while(iter.hasNext()) {
			GameObject object = (GameObject) iter.next();
			System.out.println(object.toString());
		}
	}
	
	//Update function is called whenever we want to update the map/game objects' state (ie. every game tick)
	public void update() {
		this.elapsedTicks += 1;

		//This iterator will be replaced in Assignment 2 with a custom-built iterator
		//For now, we're just going to use the Java built-in iterator
		IIterator objects = this.objects.getIterator();
		
		//Iterate through every game object and apply relevant updates to it
		while(objects.hasNext()) {
			GameObject object = (GameObject) objects.next();
			object.update();
			//Kind of hacky bounds checking. I can't figure out how to get boundary information inside a movable object (and thus, accessible to the move() method)
			//If an object is movable and is not inside the boundary, move it back in
			if(isOutOfBounds(object)) {
				System.out.println(object.getClassName() + " is out of bounds, nudging back in");
				nudgeInsideBoundary(object);
			}
		}

		//check if the player has died/can't move
		if(PlayerCyborg.getPlayer().isDead() == true) {
			//Lose a life
			System.out.println("The Cyborg has failed. You lose one life. Try again!");
			remainingLives -= 1;
			//Check for game over (no lives left)
			if(isGameover()) {
				System.out.println("Game Over! You have run out of lives");
				//set the game over flag to disable further play
				gameOver = true;
			// otherwise reset the player
			} else {
				init();
				PlayerCyborg.getPlayer().resetDamageLevel();
				PlayerCyborg.getPlayer().resetEnergyLevel();
			}
		//Hardcoded victory check for our 4 hardcoded bases
		} else if(PlayerCyborg.getPlayer().getLastBase() == 4) {
			System.out.println("Game over, you win! Total time: #"+elapsedTicks);
			gameOver = true;
			
		}
		
		setChanged();
		notifyObservers();
	}
	
	//Debug command to read player's cyborg state
	public void printPlayerInfo() {
		System.out.println("Cyborg Lives: " + remainingLives);
		System.out.println("Elapsed Time: " + elapsedTicks);
		System.out.println("Highest Base: " + PlayerCyborg.getPlayer().getLastBase());
		System.out.println("Energy Level: " + PlayerCyborg.getPlayer().getEnergyLevel());
		System.out.println("Damage Level: " + PlayerCyborg.getPlayer().getDamageLevel());
	}

	//helper function to get a random drone from the gameObject list
	//THIS FUNCTION CAN CAUSE INFINITE LOOP IF NO DRONE EXISTS. BE CAREFUL!!!
	public Drone debugGetRandomDrone() {
		//Randomly pick objects until we pick a drone
		Random rand = new Random();
		GameObject object = objects.get(rand.nextInt(objects.size()-1));
		while(!(object instanceof Drone)) {
			object = objects.get(rand.nextInt(objects.size()-1));
		}
		//Return our random drone
		return (Drone) object;
		
	}
	
	//THIS FUNCTION CAN CAUSE INFINITE LOOP IF NO ENERGYSTATIONS EXISTS. BE CAREFUL!!!
	public EnergyStation debugGetRandomEnergyStation() {
		//Randomly pick objects until we pick a energyStation
		Random rand = new Random();
		GameObject object = objects.get(rand.nextInt(objects.size()-1));
		while(!(object instanceof EnergyStation)) {
			object = objects.get(rand.nextInt(objects.size())-1);
		}
		//Return our random energyStation 
		return (EnergyStation) object;
		
	}
}