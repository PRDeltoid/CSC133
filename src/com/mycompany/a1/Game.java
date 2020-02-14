package com.mycompany.a1;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Form;
import java.lang.String;

public class Game extends Form {
	
	private GameWorld world;
	private int elapsedTicks;
	private boolean gameOver = false;

	//Flag for managing if the player has indicated they want to exit the game
	//This makes the Y and N entries available for use
	private boolean wantsToExit = false;
	
	//Progress the game world 1 tick
	private void tick() {
		//Update the game world
		this.elapsedTicks += 1;
		this.world.update();

		//check if the player has died/can't move
		if(world.getPlayer().isDead() == true) {
			//Lose a life and reset the player
			System.out.println("The Cyborg has failed. You lose one life. Try again!");
			world.getPlayer().loseALife();
			//Check for game over (no lives left)
			if(world.getPlayer().isGameover()) {
				System.out.println("Game Over! You have run out of lives");
				//set the game over flag to disable further play
				gameOver = true;
			} else {
				this.world.init();
			}
		}
	}
	
	//Debug command to read player's cyborg state
	private void printPlayerInfo() {
		System.out.println("Cyborg Lives: " + world.getPlayer().getLives());
		System.out.println("Elapsed Time: " + this.elapsedTicks);
		System.out.println("Highest Base: " + world.getPlayer().getLastBase());
		System.out.println("Energy Level: " + world.getPlayer().getEnergyLevel());
		System.out.println("Damage Level: " + world.getPlayer().getDamageLevel());
	}

	//Discrete simulation input
	private void play() {
		Label myLabel=new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField=new TextField();
		this.addComponent(myTextField);
		this.show();
		myTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				System.out.println("-------------------");
				String sCommand=myTextField.getText().toString();
				myTextField.clear();
				if(sCommand.length() != 0)
					switch (sCommand.charAt(0)) {
						case 'a':
							//Accelerate cyborg (5 speed units)
							System.out.println("Accelerating");
							world.getPlayer().accelerate(5);
							break;
						case 'b':
							//Brake Cyborg (5 speed units)
							System.out.println("Braking");
							world.getPlayer().brake(5);
							break;
						case 'l':
							//Steer cyborg left 5 degrees
							System.out.println("Steering Left");
							world.getPlayer().steerLeft();
							break;
						case 'r':
							//Steer cyborg right 5 degrees
							System.out.println("Steering Right");
							world.getPlayer().steerRight();
							break;
						case 'c':
							//Pretend cyborg collided with another cyborg
							//We create an imaginary cyborg as none exist in our game world (yet)
							System.out.println("Cyborg Collision!");
							world.getPlayer().collide(new Cyborg());
							break;
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							//Pretend Cyborg collided with base station "n"
							int value = sCommand.charAt(0) - '0'; //ascii hack to find the integer value of the character given
							System.out.println("Cyborg -> Base Station" + value);
							world.getPlayer().setLastBase(value);
							break;
						case 'e':
							//Pretend Cyborg hit an energy station
							System.out.println("Cyborg -> EnergyStation");
							world.getPlayer().collide(world.debugGetRandomEnergyStation());
							break;
						case 'g':
							//Pretend Cyborg has collided with drone
							System.out.println("Cyborg -> Drone");
							world.getPlayer().collide(world.debugGetRandomDrone());
							break;
						case 't':
							//Tick game clock
							System.out.println("Tick");
							tick();
							break;
						case 'd':
							//Describe current game/player cyborg values
							printPlayerInfo();
							break;
						case 'm':
							//Describe the map
							world.printMapInfo();
							break;
						case 'x':
							//Exit
							System.out.println("Are you sure you want to quit? Y/n");
							wantsToExit = true;
							break;
						case 'y':
						case 'Y':
							//User confirms exit
							if(wantsToExit == true) {
								System.out.println("Quitting");
								System.exit(0);
							} else {
								System.out.println("This entry is only valid when attempting to quit");
							}
							break;
						case 'n':
						case 'N':
							//User changed their mind, don't exit
							if(wantsToExit == true) {
								System.out.println("Aborting quit");
								wantsToExit = false;
							} else {
								System.out.println("This entry is only valid when attempting to quit");
							}
							break;
					}
				}
			}
		);
	}
	
	public Game() {
		//Create the world
		world = new GameWorld(100,100);
		//Setup the world
		world.init();
		//Play the game
		play();
	}
	
	
}
