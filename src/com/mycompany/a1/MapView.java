package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container {
	
	/*@Override
	protected Dimension calcPreferredSize() {
		return new Dimension(1000,1000);
	}*/

	public MapView() {
		this.setLayout(new FlowLayout());
		this.add(new Label("MapView"));
		
		//Empty for now, except for a red border
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.MAGENTA));
	}

}
