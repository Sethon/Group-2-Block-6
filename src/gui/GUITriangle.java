package gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUITriangle implements Drawable {
	private static final Color TRIANGLE_COLOR = Color.GRAY;
	
	private GUIPoint p1;
	private GUIPoint p2;
	private GUIPoint p3;
	
	public GUITriangle(GUIPoint p1, GUIPoint p2, GUIPoint p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	@Override
	public void draw(Graphics2D g2) {
		Color tmp = g2.getColor();
		g2.setColor(TRIANGLE_COLOR);
		p1.draw(g2);
		p2.draw(g2);
		p3.draw(g2);
		g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		g2.drawLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
		g2.drawLine(p3.getX(), p3.getY(), p1.getX(), p1.getY());
		g2.setColor(tmp);
	}	
}
