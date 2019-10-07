package gent.timdemey.bsp;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;

import gent.timdemey.bsp.hud.HudPane;
import gent.timdemey.bsp.rendering.RenderPane;

public class Main implements Runnable
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Main());
	}

	@Override
	public void run()
	{
		WebLookAndFeel.install();
		
		// Transparent 16 x 16 pixel cursor image.
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

				// Create a new blank cursor.
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				    cursorImg, new Point(0, 0), "blank cursor");
		
		EventBus eventBus = new EventBus();
		CommandCenter commandCenter = new CommandCenter(eventBus);
		HudPane hudPane = new HudPane(commandCenter, eventBus);

		RenderPane renderPane = new RenderPane();
		eventBus.AddRenderer(renderPane);
		eventBus.AddRenderer(hudPane);
		
	//	renderPane.setCursor(blankCursor);
	//	hudPane.setCursor(blankCursor);

		JFrame frame = new JFrame("BSP");
		frame.setContentPane(renderPane);
		frame.setGlassPane(hudPane);
		hudPane.setVisible(true);
		hudPane.setOpaque(false);
		
		hudPane.Initialize();
		
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		
		frame.setVisible(true);
	}
}
