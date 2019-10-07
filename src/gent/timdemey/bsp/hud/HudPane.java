package gent.timdemey.bsp.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.BiFunction;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import gent.timdemey.bsp.CommandCenter;
import gent.timdemey.bsp.EventBus;
import gent.timdemey.bsp.rendering.IRenderer;
import gent.timdemey.bsp.rendering.RenderEvent;
import gent.timdemey.bsp.rendering.RenderingState;
import net.miginfocom.swing.MigLayout;

public class HudPane extends JPanel implements MouseMotionListener, IRenderer
{
	private final CommandCenter commandCenter;	
	private final EventBus eventBus;
	
	private RenderingState renderingState;
	
	public HudPane(CommandCenter commandCenter, EventBus eventBus)
	{
		this.commandCenter = commandCenter;
		this.eventBus = eventBus;		
	}
	
	private interface IApply
	{
		void Apply();
	}
	
	public void Initialize()
	{
		setLayout(new MigLayout());
		
		AddButtons();
		AddListeners();
	}
	
	private void AddButtons()
	{
		BiFunction<String, IApply, JButton> create = (str, appl) -> 
		{
			Action action = new AbstractAction(str) {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					appl.Apply();
				}
			};
			return new JButton(action);
		};
		
		// start render
		{
			JButton button = create.apply("Start Render", commandCenter::StartRender);
			add(button, "wrap");
		}
		
		// stop render
		{
			JButton button = create.apply("Stop Render", commandCenter::StopRender);
			add(button, "wrap");
		}
	}
	
	private void AddListeners()
	{
		addMouseMotionListener(this);
	}
	
	protected void paintComponent(Graphics g) {
   
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.fillOval(50,50,100,100);
	    
	}
	
	@Override
	public void Render(RenderEvent renderEvent)
	{
		renderingState = renderEvent.renderingState;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		// don't send events if there is no rendering
		if (renderingState == null || renderingState == RenderingState.Stopped)
		{
			return;
		}
		eventBus.AddHudEvent(new HudEvent()
		{{
			MouseLocation = arg0.getPoint();
		}});
	}
}
