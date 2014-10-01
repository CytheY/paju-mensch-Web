package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


	public class PixelComponent extends JComponent
	{
	    private Color color;
	    private int w,h,x,y;
	    public PixelComponent(Color color,int x, int y, int w, int h)
	    {
	        super();
	        this.color = color;
	        this.w = w;
	        this.h = h;
	    }

	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);

	        g.setColor(color);
	        g.fillRect(x, y, w, h);
	    }
	
}
