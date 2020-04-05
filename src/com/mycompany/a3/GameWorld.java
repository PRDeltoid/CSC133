package com.mycompany.a3;

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
	private Sound cyborgCrashSound;
	private Sound energyStationCollideSound;
	private Sound lifeLostSound;
	private BGSound backgroundSound;
	
	private int lastBase = 4; //The last base needed to win the game
	
	/*helper function to make sure a given value falls within a range
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
	}*/
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public int getElapsedTicks() {
		return elapsedTicks;
	}
	
	public int getRemainingLives() {
		return remainingLives;
	}
	
	public boolean getSoundSetting() {
		return sound;
	}
	
	public GameObjectCollection getObjects() {
		return objects;
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
		this.height = height;
		this.width = width;
		init();
	}

	public void init() {
		//Initialize map variables
		Random rand = new Random();
		
		//If we're resetting, delete all current non-player game objects and recreate them
		objects.clear();
		objects.add(PlayerCyborg.getPlayer());
		//Move player back to "start". Currently hardcoded at 0,0 (with Base 1, seen below)
		PlayerCyborg.getPlayer().setLocation(100, 100);
		//Hardcoded show bounding box for player
		PlayerCyborg.getPlayer().SetShowBoundingBox(true);

		//Add our bases
		//4 bases, random location, blue color, sequenced in order
		objects.add(new Base(0,0,10,ColorUtil.BLUE,1));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),30,ColorUtil.BLUE,2));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),30,ColorUtil.BLUE,3));
		objects.add(new Base(rand.nextInt(height), rand.nextInt(width),30,ColorUtil.BLUE,4));

		//EnergyStation(float x, float y, int size, int color) {
		//2 energy stations with random location and size (1-50) (and thus, random capacity)
		objects.add(new EnergyStation(rand.nextInt(height), rand.nextInt(width), rand.nextInt(69)+1, ColorUtil.GREEN));
		objects.add(new EnergyStation(rand.nextInt(height), rand.nextInt(width), rand.nextInt(69)+1, ColorUtil.GREEN));
		
		//Drone(float x, float y, int size, int color, int heading, int speed) {
		//2 drones with random location, fixed size, and random speed (5-10) and heading (0-359)
		objects.add(new Drone(rand.nextInt(height), rand.nextInt(width), 20, ColorUtil.YELLOW, rand.nextInt(360), rand.nextInt(4)+1));
		objects.add(new Drone(rand.nextInt(height), rand.nextInt(width), 20, ColorUtil.YELLOW, rand.nextInt(360), rand.nextInt(4)+1));
		
		//public NonPlayerCyborg(float x, float y, int size, int color, int heading, int speed, int maxSpeed, int energyLevel, int energyConsumptionRate, int maxDamageLevel) {
		//3 NPCs with different strategies. Have unlimited energy and 150% the player's health. All other stats are identical to player.
		NonPlayerCyborg cyborg1 = new NonPlayerCyborg(50,0,50,ColorUtil.CYAN, 0, 1, 50, 100, 0, 15);
		NonPlayerCyborg cyborg2 = new NonPlayerCyborg(50,50,50,ColorUtil.CYAN, 0, 1, 50, 100, 0, 15);
		NonPlayerCyborg cyborg3 = new NonPlayerCyborg(0,50,50,ColorUtil.CYAN, 0, 1, 50, 100, 0, 15);

		cyborg1.setStrategy(new MoveToNextBaseStrategy(cyborg1, this));
		cyborg2.setStrategy(new AttackStrategy(cyborg2));
		cyborg3.setStrategy(new RandomMoveStrategy(cyborg3, this));

		objects.add(cyborg1);
		objects.add(cyborg2);
		objects.add(cyborg3);
		npcs.add(cyborg1);
		npcs.add(cyborg2);
		npcs.add(cyborg3);
		
		//Load our sounds
		createSounds();
		//Start the background music
		backgroundSound.start();
		
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
	
	public void createSounds() {
		cyborgCrashSound = new Sound("cyborg-collide.mp3", this); //TODO: Change to wav
		energyStationCollideSound = new Sound("energystation-collide.wav", this);
		lifeLostSound = new Sound("life-loss.wav", this);
		backgroundSound = new BGSound("bg-sound.wav");
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
			//Passing in 20ms update time hardcoded right now
			//TODO: Make this dynamic based on timer
			object.update(20, this);
			checkCollision(object);
		}

		//Player death/out of energy check
		if(PlayerCyborg.getPlayer().isDead() == true) {
			//Lose a life
			System.out.println("Your Cyborg has failed. You lose one life. Try again!");
			remainingLives -= 1;
			//Play the life lost sound
			lifeLostSound.play();

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
	
	private void checkCollision(GameObject object) {
		IIterator objects = this.objects.getIterator();
		while(objects.hasNext()) {
			GameObject otherObject = (GameObject) objects.next();

			//Check all other objects for collision
			if(object.collidesWith(otherObject)) {
				if(object instanceof Cyborg) {
					if(otherObject instanceof Cyborg || otherObject instanceof Drone) { 
						cyborgCrashSound.play();
					} else if(otherObject instanceof EnergyStation) {
						energyStationCollideSound.play();
					}
				}
				object.handleCollision(otherObject);
				otherObject.handleCollision(object);
				//TODO: Add to an "already collided" list
			}
		}
		
	}

	public void toggleSound() {
		sound = !sound;
		
		//Toggle our background music too
		if(backgroundSound.isPlaying()) {
			backgroundSound.stop();
		} else {
			backgroundSound.start();
		}

		setChanged();
		notifyObservers();
	}
	
	public void switchAllNPCStrategy() {
		IIterator npcsIt = npcs.getIterator();
		while(npcsIt.hasNext()) {
			((NonPlayerCyborg) (npcsIt.next())).switchStrategy(this); 
		}
	}

	public Base getBase(int index) {
		IIterator it = objects.getIterator();
		while(it.hasNext()) {
			Object object = it.next();
			if(object instanceof Base && ((Base) object).getSequenceNumber() == index) {
				return ((Base) object);
			}
		}
		
		return null;
		
	}
}
