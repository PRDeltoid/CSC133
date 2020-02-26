package com.mycompany.a1;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;

public class MapView extends Container {
	
	@Override
	protected Dimension calcPreferredSize() {
		return new Dimension(1000,1000);
	}

	public MapView() {
		this.add(new Label("MapView"));
		this.setHeight(500);
		this.setWidth(500);
	}

}
