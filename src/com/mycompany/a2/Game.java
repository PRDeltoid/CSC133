package com.mycompany.a2;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
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
	
	@Override
	protected Dimension calcPreferredSize() {
		return new Dimension(Display.getInstance().getDisplayHeight(), Display.getInstance().getDisplayWidth());
	}
	
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
		gameOver = world.isGameOver();
	}
	
	private void play() {
		//show() must be called before the MapView will have a size to provide to the GameWorld
		this.show();

		//Setup the world
		world.init(gui.centerContainer.getHeight(), gui.centerContainer.getWidth());
	}
	
	public Game() {
		//Create the world model
		//TODO Make height/width equal to the MapView object in myGui
		world = new GameWorld();
		
		//Setup the gui, attach it to the model
		gui = new myGUI(world);
		
		//Play the game
		play();
	}
	
	private class myGUI {
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
			BrakeCommand brakeCommand = new BrakeCommand();
			RightCommand rightCommand = new RightCommand();
			AccelerateCommand accelerateCommand = new AccelerateCommand();
			LeftCommand leftCommand = new LeftCommand();
			TickCommand tickCommand = new TickCommand();
			ChangeStratCommand changeStratCommand = new ChangeStratCommand(world);
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

			//Setup sound checkbox
			MyCheckBox soundCheckbox = new MyCheckBox("Sound");
			soundCheckbox.setCommand(new SoundCommand());
			
			//Setup toolbar commands
			toolbar.addComponentToLeftSideMenu(soundCheckbox);
			toolbar.addCommandToSideMenu(new ExitCommand());
			toolbar.addCommandToSideMenu(new AboutCommand());
			toolbar.addCommandToRightBar(new HelpCommand());
			
			//Setup buttons
			setupButtons(world);

			Game.this.setLayout(new BorderLayout());
			
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
			
			Game.this.add(BorderLayout.CENTER, centerContainer);
			Game.this.add(BorderLayout.NORTH, northContainer);
			Game.this.add(BorderLayout.SOUTH, southContainer);
			Game.this.add(BorderLayout.EAST, eastContainer);
			Game.this.add(BorderLayout.WEST, westContainer);
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
		
		public ChangeStratCommand(GameWorld target) {
			super("ChangeStrat");
			this.target = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Change Strat command invoked");
			target.switchAllNPCStrategy();
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
			//Get player and a random NPC
			NonPlayerCyborg npc = target.debugGetRandomNPC();
			PlayerCyborg player = PlayerCyborg.getPlayer();
			//They each hit each other
			player.collide(npc);
			npc.collide(player);

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
			System.out.println("Collide Base command invoked");
			Command cOk = new Command("Ok");
			Command cCancel = new Command("Cancel");
			Command[] cmds = new Command[]{cOk, cCancel};
			TextField baseNumber = new TextField();
			Command c = Dialog.show("Enter base number:", baseNumber, cmds);
			//[if you only want to display the okay option, you do not need to
			//create cmds, just use Dialog.show("Enter the title:", myTF, cOk);]
			if (c == cOk) {
				try {
					int baseInt = Integer.parseInt(baseNumber.getText());
					System.out.println("Base " + Integer.parseInt(baseNumber.getText()));
					PlayerCyborg.getPlayer().setLastBase(baseInt);
				} catch(NumberFormatException exp) {
					System.out.println("Invalid number entered");
				}
			}
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
			Dialog.show("Help", "a - accelerate\nb - brake\nl - turn left\nr - turn right\ne - collide with energy station\ng - collide with a drone\nt - tick", "OK", null);
		}
		
	}

	private class AboutCommand extends Command {
		
		public AboutCommand() {
			super("About");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("About command invoked");
			Dialog.show("About", "Taylor Britton - CSC 133 \n Assignment 2 \n Spring 2020", "OK", null);
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

	private class MyCheckBox extends CheckBox {
	
		public MyCheckBox(String label) {
			super(label);
			this.getAllStyles().setBgTransparency(255);
			this.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		}

	}
}
