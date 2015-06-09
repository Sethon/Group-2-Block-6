package gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUIAxis implements Drawable {
	private static final Color AXIS_COLOR = Color.BLUE;
	
	private GUIPoint a;
	private GUIPoint b;
	
	public GUIAxis(GUIPoint a, GUIPoint b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void draw(Graphics2D g2) {
		Color tmp = g2.getColor();
		g2.setColor(AXIS_COLOR);
		g2.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
		g2.setColor(tmp);
	}
}
