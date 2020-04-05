package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	
	Label timeLabel;
	Label livesLabel;
	Label baseReachedLabel;
	Label playerEnergyLabel;
	Label playerDamageLabel;
	Label soundLabel;
	
	public ScoreView(Observable gameWorld) {
		this.setLayout(new FlowLayout(Component.CENTER));
		
		//add our starting labels
		//TODO Add padding so labels don't shift as values change
		timeLabel  	= new Label("Time: 0");
		livesLabel 	= new Label("Lives Left: ");
		baseReachedLabel = new Label("Player Last Base Reached: ");
		playerEnergyLabel = new Label("Player Energy Level: ");
		playerDamageLabel = new Label("Player Damage Level: ");
		soundLabel = new Label("Sound: ");
		this.add(timeLabel);
		this.add(livesLabel);
		this.add(baseReachedLabel);
		this.add(playerEnergyLabel);
		this.add(playerDamageLabel);
		this.add(soundLabel);

		//Add our observer to the model
		gameWorld.addObserver(this);
	}
	
	public void update(Observable gameWorld, Object arg) {
		timeLabel.setText("Time: " + ((GameWorld) gameWorld).getElapsedTicks());
		livesLabel.setText("Lives Left: " + ((GameWorld) gameWorld).getRemainingLives());
		baseReachedLabel.setText("Player Last Base Reached: " + PlayerCyborg.getPlayer().getLastBase());
		playerEnergyLabel.setText("Player Energy Level: " + PlayerCyborg.getPlayer().getEnergyLevel());
		playerDamageLabel.setText("Player Damage Level: " + PlayerCyborg.getPlayer().getDamageLevel());
		soundLabel.setText("Sound: " + (((GameWorld) gameWorld).getSound() ? "ON" : "OFF"));
		repaint();
	}

}
