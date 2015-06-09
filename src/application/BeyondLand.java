package application;

import javax.swing.JFrame;

import gui.SpacePanel;
import surfaces.Spiral;
import surfaces.Torus;

public class BeyondLand {
	public static void main(String[] args) {
		SpaceModel sM = new SpaceModel();
		SpacePanel sP = new SpacePanel();
		sP.setSize(1024, 1024);
		SpaceController sC = new SpaceController(sP, sM);
		JFrame fr = new JFrame();
		fr.setSize(1024, 1024);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		fr.add(sP);
		fr.setFocusable(false);
		sP.requestFocus();
		sM.addSurface(new Torus(0.0, 2 * Math.PI, 0.0, 2 * Math.PI, 30, 30, 10, 10));
		//sM.addSurface(new Spiral(1.0, 2.0, 0.0, 1.0, 30, 30));
	}
}
