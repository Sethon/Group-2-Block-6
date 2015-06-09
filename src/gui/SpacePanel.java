package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SpacePanel extends JPanel{
	private static final Color BG_COLOR 	= Color.WHITE;
	
	private ArrayList<Drawable> drawables;
	
	public SpacePanel() {
		drawables = new ArrayList<>();
		this.setBackground(BG_COLOR);
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	    for (Drawable d : drawables) {
	    	d.draw(g2);
	    }
	}
	
	public void update(ArrayList<Drawable> drawables) {
		this.drawables = drawables;
		repaint();
	}
}
