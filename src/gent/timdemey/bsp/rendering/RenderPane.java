package gent.timdemey.bsp.rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gent.timdemey.bsp.EventBus;

public class RenderPane extends JPanel implements IRenderer
{
	private List<RenderEvent> renderEvents = new ArrayList<>();
	
	public void Render(final RenderEvent renderEvent)
	{
		SwingUtilities.invokeLater(() ->
		{
			renderEvents.add(renderEvent);
			repaint();
		});
	}
	
	protected void paintComponent(Graphics gg) 
	{
		RenderEvent event = renderEvents.size() != 0 ? renderEvents.get(renderEvents.size() - 1) : null;
		renderEvents.clear();
		
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON); 
        	    
	    g.setFont(Font.decode("Tahoma 36 bold"));
	    
	    if (event == null || event.renderingState == RenderingState.Stopped)
	    {
	    	g.setColor(Color.black);
	    	g.fillRect(0, 0, getWidth(), getHeight());
	    	
	    	g.setColor(Color.white);
		    RenderUtils.DrawCenteredString(g, "No rendering active", getBounds());
	    	
	    	return;
	    }
	    
	    if (event.announcement != null)
	    {
	    	g.setColor(new Color(event.red, 0, 0));
	    	g.drawString(event.announcement, 150, 150);
	    }
	    if (event.mouseLocation != null)
	    {
	    	g.setColor(new Color(event.red, 255-event.red, 0));
	    	g.fillOval(event.mouseLocation.x - 5, event.mouseLocation.y - 5, 10, 10);
	    }
	}
}
