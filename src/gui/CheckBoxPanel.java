package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CheckBoxPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private GLFrame frame;
	
	public CheckBoxPanel(GLFrame frame) {
		this.frame = frame;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JCheckBox chB1 = new JCheckBox(Renderer.RENDER_LINES);
		chB1.setSelected(true);
		chB1.addActionListener(this);
		chB1.setFocusable(false);
		JCheckBox chB2 = new JCheckBox(Renderer.RENDER_POINTS);
		chB2.addActionListener(this);
		chB2.setFocusable(false);
		JCheckBox chB3 = new JCheckBox(Renderer.RENDER_TRIANGLES);
		chB3.setSelected(true);
		chB3.addActionListener(this);
		chB3.setFocusable(false);
		JCheckBox chB4 = new JCheckBox(Renderer.RENDER_AXES);
		chB4.setSelected(true);
		chB4.addActionListener(this);
		chB4.setFocusable(false);
		this.add(chB1);
		this.add(chB2);
		this.add(chB4);
		this.add(chB3);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox chB = (JCheckBox) e.getSource();
		frame.switchRendering(chB.getText(), chB.isSelected());
	}

}
