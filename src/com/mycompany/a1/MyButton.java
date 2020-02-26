package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.plaf.Border;

public class MyButton extends Button {
	
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
