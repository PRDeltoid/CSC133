package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface IDrawable {
	abstract void draw(Graphics g, Point pCmpRelPrnt);
	abstract void drawShape(Graphics g, Point p);
}
