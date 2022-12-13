package flappyBird;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel{
	
	
	private static final long serialVersionUID = -8700931567754893103L;

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Flappy.flappybird.repaint(g);
	}
}
