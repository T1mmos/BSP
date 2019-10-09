package gent.timdemey.bsp.rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
        
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.concatenate(AffineTransform.getTranslateInstance(0, -300));
        g.setTransform(tx);
        
	    if (event != null && event.image != null)
	    {
		    g.drawImage(event.image, 0, 0, null);
	    }
	}
}
