package gent.timdemey.bsp.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.BiFunction;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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
		
		setFocusable(true);
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
	
	private void RegisterKey(int keyEvent)
	{
		KeyStroke keyStrokePressed = KeyStroke.getKeyStroke(keyEvent, 0, false);
		KeyStroke keyStrokeReleased = KeyStroke.getKeyStroke(keyEvent, 0, true);
		String actionKeyPressed = "pressed-" + keyEvent;
		String actionKeyReleased = "released-" + keyEvent;
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokePressed, actionKeyPressed);
		getActionMap().put(actionKeyPressed, new AbstractAction("KeyPressed-" + keyStrokePressed.getKeyChar())
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				keyPressed(keyStrokePressed);
			}
		});
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeReleased, actionKeyReleased);
		getActionMap().put(actionKeyReleased, new AbstractAction("KeyReleased-" + keyStrokeReleased.getKeyChar())
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				keyReleased(keyStrokeReleased);
			}
		});
	}
	
	private void AddListeners()
	{
		addMouseMotionListener(this);
		RegisterKey(KeyEvent.VK_W);
		RegisterKey(KeyEvent.VK_S);
		RegisterKey(KeyEvent.VK_A);
		RegisterKey(KeyEvent.VK_D);
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

	private void keyPressed(KeyStroke e)
	{
		// don't send events if there is no rendering
		if (renderingState == null || renderingState == RenderingState.Stopped)
		{
			return;
		}
		
		HudEvent hudEvent = new HudEvent();
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			hudEvent.KeyUp = KeyChange.Pressed;
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			hudEvent.KeyDown = KeyChange.Pressed;
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			hudEvent.KeyLeft = KeyChange.Pressed;
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			hudEvent.KeyRight = KeyChange.Pressed;
		}
		eventBus.AddHudEvent(hudEvent);
	}

	private void keyReleased(KeyStroke e)
	{	
		// don't send events if there is no rendering
		if (renderingState == null || renderingState == RenderingState.Stopped)
		{
			return;
		}
		
		HudEvent hudEvent = new HudEvent();
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			hudEvent.KeyUp = KeyChange.Released;
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			hudEvent.KeyDown = KeyChange.Released;
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			hudEvent.KeyLeft = KeyChange.Released;
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			hudEvent.KeyRight = KeyChange.Released;
		}
		
		eventBus.AddHudEvent(hudEvent);
	}
}
