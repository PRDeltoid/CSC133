package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	
	/*@Override
	protected Dimension calcPreferredSize() {
		return new Dimension(1000,1000);
	}*/

	public MapView(Observable gameWorld) {
		this.setLayout(new FlowLayout());
		this.add(new Label("MapView"));
		
		//Add our observer to the model
		gameWorld.addObserver(this);

		//Empty for now, except for a red border
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.MAGENTA));
	}
	
	public void update(Observable gameWorld, Object arg) {
		//does nothing (for now)
	}

}
