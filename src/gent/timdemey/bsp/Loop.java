package gent.timdemey.bsp;

import java.util.List;

import gent.timdemey.bsp.gamedata.GameData;
import gent.timdemey.bsp.hud.HudEvent;
import gent.timdemey.bsp.rendering.RenderEvent;
import gent.timdemey.bsp.rendering.RenderingState;

public class Loop implements Runnable
{
	private final EventBus eventBus;
	private final GameData gameData;
	private final long startTime;
	
	public Loop (EventBus eventBus)
	{
		this.eventBus = eventBus;
		this.gameData = new GameData();
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public void run()
	{		
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Active;
		}});
		while(!Thread.interrupted())
		{
			HandleHudInput();
			Update();
			Render();
			try
			{
				Thread.sleep(1);
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Stopped; 
			announcement = null;
		}});
	}
	
	private void HandleHudInput()
	{
		// handle user input
		
		List<HudEvent> events = eventBus.GetHudEvents();
		
		if (events != null && events.size() > 0)
		{
			for (HudEvent event : events)
			{
				if (event.TestButtonClicked)
				{
					gameData.hudAnnouncement.Text = "TEST!";
				}
				if (event.MouseLocation != null)
				{
					gameData.mouseData.location = event.MouseLocation;
				}
			}
		}
	}
	
	private void Update()
	{

		long currTime = System.currentTimeMillis();
		long diffTimeTotal = currTime - startTime;
		
		long centered = Math.abs(diffTimeTotal % 1000 - 500);
		long red = centered * 255 / 500;
		
		gameData.red = (int) red;
		
	}
	
	private void Render()
	{
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Active;
			announcement = gameData.hudAnnouncement.Text;	
			mouseLocation = gameData.mouseData.location;
			red = gameData.red;
		}});
	}
}
