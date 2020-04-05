package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	Media sound;
	GameWorld world;

	public Sound(String soundfile, GameWorld world) {
		if (Display.getInstance().getCurrent() == null)
		{
			System.out.println("Error: Create sound objects after calling show()!");
			System.exit(0);
		}
		this.world = world;
		try {
			InputStream stream = Display.getInstance().getResourceAsStream(getClass(),  "/"+soundfile);
			sound = MediaManager.createMedia(stream, "audio/wav");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		//if sound is enabled, play the sound
		if(world.getSoundSetting() == true) {
			sound.setTime(0);
			sound.play();
		}
	}

}