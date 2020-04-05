package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	
	GameWorld world;
	
	
	/*@Override
	protected Dimension calcPreferredSize() {
		return new Dimension(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
	}*/

	public MapView(Observable gameWorld) {
		this.setLayout(new FlowLayout());
		this.add(new Label("MapView"));
		
		world = (GameWorld)gameWorld;
		
		//Add our observer to the model
		gameWorld.addObserver(this);

		//Empty for now, except for a red border
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.MAGENTA));
	}
	
	public void update(Observable gameWorld, Object arg) {
		((GameWorld) gameWorld).printMapInfo();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		//Get our objects
		IIterator objects = world.getObjects().getIterator();
		while(objects.hasNext()) {
			//Draw the object
			//We pass in this objects X/Y so the drawing object can use it as an offset 
			//(since our object's x/y are relative to the Map, but our draw takes screen coordinates)
			((GameObject)objects.next()).draw(g, new Point(this.getX(), this.getY()));;
		}
	}
}
