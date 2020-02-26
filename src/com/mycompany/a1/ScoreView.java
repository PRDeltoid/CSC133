package com.mycompany.a1;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container {
	
	public ScoreView() {
		this.setLayout(new FlowLayout(Component.CENTER));
		this.add(new Label("ScoreView"));
	}

}
