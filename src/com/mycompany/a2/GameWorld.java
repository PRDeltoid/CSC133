package com.mycompany.a2;

import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;

public class GameWorld extends Observable {

	private int height; //currently "X"
	private int width;	//currently "Y"
	private GameObjectCollection objects = new GameObjectCollection(); //ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private GameObjectCollection npcs = new GameObjectCollection();
	private int elapsedTicks = 0;
	private boolean gameOver = false;
	private int startLives = 3;
	private int remainingLives = startLives;
	private boolean sound = false;
	
	private int lastBase = 4; //The last base needed to win the game
	
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

	public boolean playerOutOfLivesGameover() {
		return (remainingLives==0);
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	//Ctor
	public GameWorld() {
	}
	
	//Overloaded version of init which also sets height and width. Used at game startup to initialize with MapView size
	public void init(int height, int width) {
		init();
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
		
		//public NonPlayerCyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int maxDamageLevel) {
		//3 NPCs with different strategies. Have unlimited energy and 150% the player's health. All other stats are identical to player.
		NonPlayerCyborg cyborg1 = new NonPlayerCyborg(20,0,20,ColorUtil.CYAN, 0, 10, 50, 100, 0, 15);
		NonPlayerCyborg cyborg2 = new NonPlayerCyborg(20,20,20,ColorUtil.CYAN, 0, 10, 50, 100, 0, 15);
		NonPlayerCyborg cyborg3 = new NonPlayerCyborg(0,20,20,ColorUtil.CYAN, 0, 10, 50, 100, 0, 15);

		cyborg1.setStrategy(new MoveToNextBaseStrategy(cyborg1));
		cyborg2.setStrategy(new AttackStrategy(cyborg2));
		cyborg3.setStrategy(new RandomMoveStrategy(cyborg3));

		objects.add(cyborg1);
		objects.add(cyborg2);
		objects.add(cyborg3);
		npcs.add(cyborg1);
		npcs.add(cyborg2);
		npcs.add(cyborg3);
		
		//Update views
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

		//Player death/out of energy check
		if(PlayerCyborg.getPlayer().isDead() == true) {
			//Lose a life
			System.out.println("Your Cyborg has failed. You lose one life. Try again!");
			remainingLives -= 1;
			//Check for game over (no lives left)
			if(playerOutOfLivesGameover()) {
				System.out.println("Game Over! You have run out of lives");
				//set the game over flag to disable further play
				gameOver = true;
			// otherwise reset the player and world
			} else {
				init();
				PlayerCyborg.getPlayer().resetDamageLevel();
				PlayerCyborg.getPlayer().resetEnergyLevel();
			}
		//Player victory check
		} else if(PlayerCyborg.getPlayer().getLastBase() == lastBase) {
			System.out.println("Game over, you win! Total time: "+elapsedTicks);
			gameOver = true;
			
		//NPC victory check
		} else {
			IIterator npcIt = npcs.getIterator();
			while(npcIt.hasNext()) {
				if(((NonPlayerCyborg) (npcIt.next())).getLastBase() == lastBase) {
					System.out.println("Game over, you lose! Another cyborg reached the end before you. Total time: "+elapsedTicks);
					gameOver = true;
					
				}
			}
		}

		
		setChanged();
		notifyObservers();
	}
	
	public void toggleSound() {
		sound = !sound;
		setChanged();
		notifyObservers();
	}
	
	public void switchAllNPCStrategy() {
		IIterator npcsIt = npcs.getIterator();
		while(npcsIt.hasNext()) {
			//TODO Should this be "setStrategy()" where GameWorld can have a "getRandomNPCStrat" function to feed it?
			((NonPlayerCyborg) (npcsIt.next())).switchStrategy(); 
		}
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

	//THIS FUNCTION CAN CAUSE INFINITE LOOP IF NO NPCs EXISTS. BE CAREFUL!!!
	public NonPlayerCyborg debugGetRandomNPC() {
		Random rand = new Random();
		GameObject object = npcs.get(rand.nextInt(npcs.size()-1));
		return (NonPlayerCyborg) object;
		
	}
}
