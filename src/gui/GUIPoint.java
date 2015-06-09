package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GUIPoint implements Drawable{
	private static final Color P_COLOR = Color.DARK_GRAY;
	
	
	private int x;
	private int y;
	private int r;
	
	
	public GUIPoint(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public boolean equals(Object o) {
		GUIPoint p = (GUIPoint) o;
		return (p.getX() == x) && (p.getY() == y);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.fill(new Ellipse2D.Double(x - r, y - r, 2*r, 2*r));
	}
}
