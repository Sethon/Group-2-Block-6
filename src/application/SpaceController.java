package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import gui.GUIPoint;
import gui.SpacePanel;

public class SpaceController {
	private SpacePanel sP;
	private SpaceModel sM;
	
	public SpaceController(SpacePanel sP, SpaceModel sM) {
		this.sP = sP;
		this.sM = sM;
		sP.addMouseWheelListener(new ScrollListener());
		sP.addKeyListener(new KeyboardListener());
		sP.addMouseListener(new ClickListener());
	}
	
	private class ScrollListener implements MouseWheelListener {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			sM.updateRes(e.getWheelRotation());
			sP.update(sM.getDrawables());
		}
		
	}
	
	private class KeyboardListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				sM.rotateX();
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sM.rotateY();
			}
			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				sM.rotateZ();
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_UP) {
				sM.updateOrY(1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				sM.updateOrY(-1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				sM.updateOrX(-1);
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				sM.updateOrX(1);
			}
			sP.update(sM.getDrawables());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
	}
	
	private class ClickListener extends MouseAdapter {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				String s = sM.getSurfaceInfo(new GUIPoint(e.getX(), e.getY(), 0));
				if (s != null) {
					JOptionPane.showMessageDialog(sP, s, "Surface Info", JOptionPane.PLAIN_MESSAGE);
				} else {
					
				}
			} else {
				
			}
		}
	}
}
