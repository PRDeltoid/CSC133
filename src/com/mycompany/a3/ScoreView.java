package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	
	Label timeLabel;
	Label timeText;
	Label livesLabel;
	Label baseReachedLabel;
	Label playerEnergyLabel;
	Label playerDamageLabel;
	Label soundLabel;
	Label livesText;
	Label baseReachedText;
	Label playerEnergyText;
	Label playerDamageText;
	Label soundText;

	public ScoreView(Observable gameWorld) {
		this.setLayout(new FlowLayout(Component.CENTER));
		
		//add our starting labels
		//TODO Add padding so labels don't shift as values change
		timeLabel  	= new Label("Time: ");
		timeText = new Label("0000");
		livesLabel 	= new Label("Lives Left: ");
		livesText = new Label("00");
		baseReachedLabel = new Label("Player Last Base Reached: ");
		baseReachedText = new Label("00");
		playerEnergyLabel = new Label("Player Energy Level: ");
		playerEnergyText = new Label("100");
		playerDamageLabel = new Label("Player Damage Level: ");
		playerDamageText = new Label("000");
		soundLabel = new Label("Sound: ");
		soundText = new Label(" ON");
		this.add(timeLabel);
		this.add(timeText);
		this.add(livesLabel);
		this.add(livesText);
		this.add(baseReachedLabel);
		this.add(baseReachedText);
		this.add(playerEnergyLabel);
		this.add(playerEnergyText);
		this.add(playerDamageLabel);
		this.add(playerDamageText);
		this.add(soundLabel);
		this.add(soundText);

		//Add our observer to the model
		gameWorld.addObserver(this);
	}
	
	public void update(Observable gameWorld, Object arg) {
		timeText.setText(Integer.toString(((GameWorld) gameWorld).getElapsedTicks()));
		livesText.setText(Integer.toString(((GameWorld) gameWorld).getRemainingLives()));
		baseReachedText.setText(Integer.toString(PlayerCyborg.getPlayer().getLastBase()));
		playerEnergyText.setText(Integer.toString(PlayerCyborg.getPlayer().getEnergyLevel()));
		playerDamageText.setText(Integer.toString(PlayerCyborg.getPlayer().getDamageLevel()));
		soundText.setText((((GameWorld) gameWorld).getSoundSetting() ? "ON" : "OFF"));
		repaint();
	}

}
