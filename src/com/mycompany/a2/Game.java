package com.mycompany.a2;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import java.lang.String;

public class Game extends Form {
	
	private GameWorld world;
	//Flag to prevent further execution after game over 
	private boolean gameOver = false;

	//Flag for managing if the player has indicated they want to exit the game
	//This makes the Y and N entries available for use
	private boolean wantsToExit = false;
	
	//Progress the game world 1 tick
	private void tick() {
		//Update the game world
		this.world.update();
		gameOver = world.isGameover();
	}
	
	//Discrete simulation input
	private void play() {
		
		Container mainContainer = new Container();
		mainContainer.setLayout(new BorderLayout());
		//TODO Fix this
		//this.setWidth(Display.getInstance().getDisplayWidth());
		//this.setHeight(Display.getInstance().getDisplayHeight());

		Container eastContainer = new Container();
		eastContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.add(new MyButton("Break"));
		eastContainer.add(new MyButton("Right"));

		Container westContainer = new Container();
		westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.add(new MyButton("Accelerate"));
		westContainer.add(new MyButton("Left"));
		westContainer.add(new MyButton("Change Strat"));

		Container northContainer = new ScoreView();
		
		Container southContainer = new Container();
		southContainer.setLayout(new FlowLayout(Component.CENTER));
		southContainer.add(new Label("South"));

		Container centerContainer = new MapView();
		
		mainContainer.add(BorderLayout.CENTER, centerContainer);
		mainContainer.add(BorderLayout.NORTH, northContainer);
		mainContainer.add(BorderLayout.SOUTH, southContainer);
		mainContainer.add(BorderLayout.EAST, eastContainer);
		mainContainer.add(BorderLayout.WEST, westContainer);
		
		this.addComponent(mainContainer);
	
		Toolbar myToolbar = new Toolbar();
		this.setToolbar(myToolbar);
		
		myToolbar.setTitleComponent(new Label("Sili-Challenge Game"));
		myToolbar.addCommandToSideMenu(new Command("Sidemenu Command"));
		myToolbar.addCommandToRightBar(new Command("Help"));
		
		this.show();

		/*
		Label myLabel=new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField=new TextField();
		this.addComponent(myTextField);
		myTextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				System.out.println("-------------------");
				String sCommand=myTextField.getText().toString();
				myTextField.clear();
				//Only do something if the game isn't over
				if(sCommand.length() != 0 && gameOver != true)
					switch (sCommand.charAt(0)) {
						case 'a':
							//Accelerate cyborg (5 speed units)
							System.out.println("Accelerating");
							PlayerCyborg.getPlayer().accelerate(5);
							break;
						case 'b':
							//Brake Cyborg (5 speed units)
							System.out.println("Braking");
							PlayerCyborg.getPlayer().brake(5);
							break;
						case 'l':
							//Steer cyborg left 5 degrees
							System.out.println("Steering Left");
							PlayerCyborg.getPlayer().steerLeft();
							break;
						case 'r':
							//Steer cyborg right 5 degrees
							System.out.println("Steering Right");
							PlayerCyborg.getPlayer().steerRight();
							break;
						case 'c':
							//Pretend cyborg collided with another cyborg
							//We create an imaginary cyborg as none exist in our game world (yet)
							System.out.println("Cyborg Collision!");
							PlayerCyborg.getPlayer().collide(new NonPlayerCyborg());
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
							PlayerCyborg.getPlayer().setLastBase(value);
							break;
						case 'e':
							//Pretend Cyborg hit an energy station
							System.out.println("Cyborg -> EnergyStation");
							PlayerCyborg.getPlayer().collide(world.debugGetRandomEnergyStation());
							break;
						case 'g':
							//Pretend Cyborg has collided with drone
							System.out.println("Cyborg -> Drone");
							PlayerCyborg.getPlayer().collide(world.debugGetRandomDrone());
							break;
						case 't':
							//Tick game clock
							System.out.println("Tick");
							tick();
							break;
						case 'd':
							//Describe current game/player cyborg values
							world.printPlayerInfo();
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
		);*/
	}
	
	public Game() {
		//Create the world
		world = new GameWorld(1000,1000);
		//Setup the world
		world.init();
		//Play the game
		play();
	}
	
	
}
