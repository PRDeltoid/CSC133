package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

//We must implement Runnable to make this media loopable (ie. run by a repeating timer)
public class BGSound implements Runnable {
	Media sound;
	

	public BGSound(String soundfile) {
		try {
			InputStream stream = Display.getInstance().getResourceAsStream(getClass(),  "/"+soundfile);
			sound = MediaManager.createMedia(stream, "audio/wav", this);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isPlaying() {
		return sound.isPlaying();
	}
	public void start() {
		sound.play();
		
	}

	public void stop() {
		sound.pause();
	}
	
	public void run() {
		sound.setTime(0);
		start();
	}
}
