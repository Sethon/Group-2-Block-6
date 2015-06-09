package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class GUITriangleColored extends GUITriangle {
	private Color colour;
	
	public GUITriangleColored(GUIPoint p1, GUIPoint p2, GUIPoint p3, Color colour) {
		super(p1, p2, p3);
		this.colour = colour;
	}

	@Override
	public void draw(Graphics2D g2) {
		Color tmp = g2.getColor();
		g2.setColor(TRIANGLE_COLOR);
		/*p1.draw(g2);
		p2.draw(g2);
		p3.draw(g2);*/
		Stroke s = g2.getStroke();
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		g2.drawLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
		g2.drawLine(p3.getX(), p3.getY(), p1.getX(), p1.getY());
		g2.setColor(colour);
		g2.fillPolygon(new int[] {p1.getX(), p2.getX(), p3.getX()},
				new int[] {p1.getY(), p2.getY(), p3.getY()}, 3);
		g2.setColor(tmp);
		g2.setStroke(s);
	}
}
