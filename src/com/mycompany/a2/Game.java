package com.mycompany.a2;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import java.lang.String;

public class Game extends Form {
	
	private myGUI gui;
	
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
		this.show();
	}
	
	public Game() {
		//Create the world model
		//TODO Make height/width equal to the MapView object in myGui
		world = new GameWorld(1000,1000);
		
		//Setup the gui, attach it to the model
		gui = new myGUI(world);
		
		//Setup the world
		world.init();
		
		//Play the game
		play();
	}
	
	private class myGUI {
		Container mainContainer;
		Container centerContainer;
		Container eastContainer;
		Container westContainer;
		Container southContainer;
		Container northContainer;
		Toolbar toolbar;
		
		Button brakeBtn;
		Button rightBtn;
		Button accelerateBtn;
		Button leftBtn;
		Button changeStratBtn;
		Button collideNPCBtn;
		Button collideBaseBtn;
		Button collideEnergyStationBtn;
		Button collideDroneBtn;
		Button tickBtn;

		private void setupButtons(GameWorld world) {
			//TODO
			BrakeCommand brakeCommand = new BrakeCommand();
			RightCommand rightCommand = new RightCommand();
			AccelerateCommand accelerateCommand = new AccelerateCommand();
			LeftCommand leftCommand = new LeftCommand();
			TickCommand tickCommand = new TickCommand();
			ChangeStratCommand changeStratCommand = new ChangeStratCommand();
			CollideNPCCommand collideNPCCommand = new CollideNPCCommand(world);
			CollideBaseCommand collideBaseCommand = new CollideBaseCommand(world);
			CollideEnergyStationCommand collideEnergyStationCommand = new CollideEnergyStationCommand(world);
			CollideDroneCommand collideDroneCommand = new CollideDroneCommand(world);
			
			 brakeBtn = new MyButton("Break");
			 brakeBtn.setCommand(brakeCommand);
			 addKeyListener('b', brakeCommand);

			 rightBtn = new MyButton("Right");
			 rightBtn.setCommand(rightCommand);
			 addKeyListener('r', rightCommand);

			 accelerateBtn = new MyButton("Accelerate");
			 accelerateBtn.setCommand(accelerateCommand);
			 addKeyListener('a', accelerateCommand);
			 toolbar.addCommandToSideMenu(accelerateCommand);

			 leftBtn = new MyButton("Left");
			 leftBtn.setCommand(leftCommand);
			 addKeyListener('l', leftCommand);
			 
			 changeStratBtn = new MyButton("Change Strat");
			 changeStratBtn.setCommand(changeStratCommand);
			 
			 collideNPCBtn = new MyButton("Collide NPC");
			 collideNPCBtn.setCommand(collideNPCCommand);

			 collideBaseBtn = new MyButton("Collide Base");
			 collideBaseBtn.setCommand(collideBaseCommand);

			 collideEnergyStationBtn = new MyButton("Collide EnergyStation");
			 collideEnergyStationBtn.setCommand(collideEnergyStationCommand);
			 addKeyListener('e', collideEnergyStationCommand);

			 collideDroneBtn = new MyButton("Collide Drone");
			 collideDroneBtn.setCommand(collideDroneCommand);
			 addKeyListener('g', collideDroneCommand);

			 tickBtn = new MyButton("Tick");
			 tickBtn.setCommand(tickCommand);
			 addKeyListener('t', tickCommand);
			
		}
		
		public myGUI(GameWorld world) {
			

			//Setup toolbar
			toolbar = new Toolbar();
			Game.this.setToolbar(toolbar);
			toolbar.setTitleComponent(new Label("Sili-Challenge Game"));

			//Setup soundbox
			CheckBox soundCheckbox = new CheckBox("Sound");
			//TODO move this to MyCheckbox private class (see MyButton below)
			soundCheckbox.setCommand(new SoundCommand());
			soundCheckbox.getAllStyles().setBgTransparency(255);
			soundCheckbox.getAllStyles().setBgColor(ColorUtil.LTGRAY);
			
			//Setup toolbar commands
			toolbar.addComponentToLeftSideMenu(soundCheckbox);
			toolbar.addCommandToSideMenu(new ExitCommand());
			toolbar.addCommandToSideMenu(new AboutCommand());
			toolbar.addCommandToRightBar(new HelpCommand());
			
			//Setup buttons
			setupButtons(world);

			//Setup the GUI
			mainContainer = new Container();
			mainContainer.setLayout(new BorderLayout());

			//TODO Fix this
			System.out.println(Display.getInstance().getDisplayWidth());
			System.out.println(Display.getInstance().getDisplayHeight());
			mainContainer.setWidth(Display.getInstance().getDisplayWidth());
			mainContainer.setHeight(Display.getInstance().getDisplayHeight());

			eastContainer = new Container();
			eastContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
			eastContainer.add(brakeBtn);
			eastContainer.add(rightBtn);

			westContainer = new Container();
			westContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
			westContainer.add(accelerateBtn);
			westContainer.add(leftBtn);
			westContainer.add(changeStratBtn);

			northContainer = new ScoreView(world);
			
			southContainer = new Container();
			southContainer.setLayout(new FlowLayout(Component.CENTER));
			southContainer.add(collideNPCBtn);
			southContainer.add(collideBaseBtn);
			southContainer.add(collideEnergyStationBtn);
			southContainer.add(collideDroneBtn);
			southContainer.add(tickBtn);

			centerContainer = new MapView(world);
			
			mainContainer.add(BorderLayout.CENTER, centerContainer);
			mainContainer.add(BorderLayout.NORTH, northContainer);
			mainContainer.add(BorderLayout.SOUTH, southContainer);
			mainContainer.add(BorderLayout.EAST, eastContainer);
			mainContainer.add(BorderLayout.WEST, westContainer);
			
			Game.this.addComponent(mainContainer);
		
				
		}
		
	}
	
	private class BrakeCommand extends Command {
		public BrakeCommand() {
			super("Brake");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Brake command invoked");
			PlayerCyborg.getPlayer().brake();
		}
		
	}
	
	private class RightCommand extends Command {
		public RightCommand() {
			super("Right");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Right command invoked");
			PlayerCyborg.getPlayer().steerRight();
		}
		
	}

	private class AccelerateCommand extends Command {
		public AccelerateCommand() {
			super("Accelerate");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Accelerate command invoked");
			PlayerCyborg.getPlayer().accelerate();
		}
		
	}

	private class LeftCommand extends Command {
		public LeftCommand() {
			super("Left");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Left command invoked");
			PlayerCyborg.getPlayer().steerLeft();
		}
		
	}

	private class TickCommand extends Command {
		public TickCommand() {
			super("Tick");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Tick command invoked");
			tick();
		}
		
	}
	
	private class ChangeStratCommand extends Command {
		GameWorld target;
		
		public ChangeStratCommand() {
			super("ChangeStrat");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Change Strat command invoked");
			//TODO
		}
		
	}
	
	private class CollideNPCCommand extends Command {
		GameWorld target;
		
		public CollideNPCCommand(GameWorld target) {
			super("CollideNPC");
			this.target = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Collide NPC command invoked");
			PlayerCyborg.getPlayer().collide(target.debugGetRandomDrone());
		}
		
	}

	private class CollideBaseCommand extends Command {
		GameWorld target;
		
		public CollideBaseCommand(GameWorld target) {
			super("CollideBase");
			this.target = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO
			System.out.println("Collide Base command invoked");
		}
		
	}

	private class CollideEnergyStationCommand extends Command {
		GameWorld target;
		
		public CollideEnergyStationCommand(GameWorld target) {
			super("CollideEnergyStation");
			this.target = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO
			System.out.println("Collide Energy Station command invoked");
			PlayerCyborg.getPlayer().collide(world.debugGetRandomEnergyStation());
		}
		
	}

	private class CollideDroneCommand extends Command {
		GameWorld target;
		
		public CollideDroneCommand(GameWorld target) {
			super("CollideDrone");
			this.target = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO
			System.out.println("Collide Drone command invoked");
			PlayerCyborg.getPlayer().collide(world.debugGetRandomDrone());
		}
		
	}

	private class ExitCommand extends Command {
		
		public ExitCommand() {
			super("Exit");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Exit command invoked");
			if(Dialog.show("Exit?", null, "Yes", "No")) {
				Display.getInstance().exitApplication();
			}
		}
		
	}

	private class HelpCommand extends Command {
		
		public HelpCommand() {
			super("Help");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Help command invoked");
			//TODO fix this
			Dialog.show("Help", "TODO", "OK", null);
		}
		
	}

	private class AboutCommand extends Command {
		
		public AboutCommand() {
			super("About");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("About command invoked");
			Dialog.show("About", "Taylor Britton - CSC 137 \n Assignment 2 \n Spring 2020", "OK", null);
		}
		
	}

	private class SoundCommand extends Command {
		
		public SoundCommand() {
			super("Sound");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Sound command invoked");
			world.toggleSound();
		}
		
	}
	//Button styling
	private class MyButton extends Button {
	
		public MyButton(String label) {
			super(label);
			this.getAllStyles().setPadding(Component.TOP, 10);
			this.getAllStyles().setPadding(Component.BOTTOM, 10);
			this.getAllStyles().setFgColor(ColorUtil.WHITE);
			this.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
			this.getAllStyles().setBgTransparency(255);

			this.getUnselectedStyle().setBgColor(ColorUtil.GREEN);
			
			this.getPressedStyle().setBgColor(ColorUtil.BLUE);
		}

	}
}
